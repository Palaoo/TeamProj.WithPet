package com.project.withpet.controller;

import com.google.gson.JsonObject;
import com.project.withpet.dto.BoardForm;
import com.project.withpet.domain.Board;
import com.project.withpet.domain.Boardimg;
import com.project.withpet.domain.Reply;
import com.project.withpet.service.BoardService;
import com.project.withpet.service.BoardimgService;
import com.project.withpet.service.ReplyService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final BoardimgService boardimgService;
    private final S3Uploader s3Uploader;

    private final ReplyService replyService;

    @Autowired
    public BoardController(BoardService boardService, BoardimgService boardimgService, S3Uploader s3Uploader, ReplyService replyService) {
        this.boardService = boardService;
        this.boardimgService = boardimgService;
        this.s3Uploader = s3Uploader;
        this.replyService = replyService;
    }

    @GetMapping("/community")
    public String posts(Model model,
                        @PageableDefault(sort = "date", direction = Sort.Direction.DESC, size = 6)
                        Pageable pageable, HttpServletRequest req) {
        Page<Board> posts = boardService.postList(pageable);

        List<BoardForm> postList = new ArrayList<>();
        for(int i=0; i<posts.getNumberOfElements(); i++) {
            BoardForm boardForm = new BoardForm();
            boardForm.setBoardcode(posts.getContent().get(i).getBoardcode());
            boardForm.setDate(posts.getContent().get(i).getDate());
            boardForm.setTitle(posts.getContent().get(i).getTitle());
            Long boardcode = posts.getContent().get(i).getBoardcode();
            Optional<Boardimg> thumbnail = boardimgService.findOne(boardcode);
            if(!thumbnail.isEmpty()) {
                String path = thumbnail.get().getPath();
                path = path.replace("C:\\summernote_image\\",
                        "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/");
                boardForm.setPath(path);
            } else {
                String path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/15fe7978-b10a-45c8-9364-44a9e035304692ed0fc7-1005-4a30-a088-05ed3dfffb2f.jfif";
                boardForm.setPath(path);
            }
            postList.add(boardForm);
        }

        int pageN = pageable.getPageNumber();
        int startPage = ((int) Math.floor(pageN / 5)) * 5+1;
        int totalPage = posts.getTotalPages();
        int endpage = 0;
        if(totalPage < startPage + 4) {
            endpage = totalPage;
        } else {
            endpage = startPage + 4;
        }

        model.addAttribute("posts", postList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", posts.hasNext());
        model.addAttribute("hasPrev", posts.hasPrevious());
        model.addAttribute("totalPage", posts.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endpage);

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "board/community";
    }

    @GetMapping("/community/postdetail")
    public String postdetail(Model model, @RequestParam("boardcode") Long boardcode,
                             HttpServletRequest req) {
        Optional<Board> post = boardService.findById(boardcode);
        model.addAttribute("title", post.get().getTitle());
        model.addAttribute("writer", post.get().getWriter());
        model.addAttribute("date", post.get().getDate());
        model.addAttribute("boardcode", post.get().getBoardcode());
        model.addAttribute("content", post.get().getContent());

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "board/community_postdetail";
    }

    @PostMapping("/community/postdetail")
    @ResponseBody
    public Object replies(HttpServletRequest req,
                          @RequestParam("boardcode") Long boardcode) {

        List<Reply> replies = replyService.findList(boardcode);
        return replies;
    }



    @GetMapping("/community/newpost")
    public String createPost(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userid", session.getAttribute("userid"));
            return "board/community_newpost";
        }
    }

    @PostMapping("/community/newpost")
    public String create(BoardForm form, HttpServletRequest req) {

        HttpSession session = req.getSession();

        Board board = new Board();
        board.setTitle(form.getTitle());

        String content = form.getContent();
        content = content.replace("/summernoteImage/",
                "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/");
        board.setContent(content);

        String userId = (String) session.getAttribute("userid");
        board.setWriter(userId);
        Long boardCode = boardService.newPost(board);

        String[] pathContent = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            s3Uploader.upload(file, "images",0);
            pathContent[i]= file.getPath().replace("C:\\summernote_image\\",
                    "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/");
            Boardimg boardimg = new Boardimg(boardCode, file.getName(), "생략", pathContent[i]);
            boardimgService.save(boardimg);
        }

        files.clear();
        return "redirect:/community";
    }

    ArrayList<File> files = new ArrayList<>();

    @PostMapping("/community/uploadimg")
    @ResponseBody
    public String uploadImg(HttpServletRequest req,
                            @RequestParam("file") MultipartFile multipartFile) {
        String fileRoot = "C:\\summernote_image\\";
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID() + extension;
        JsonObject jsonObject = new JsonObject();
        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImage/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }
        String a = jsonObject.toString();
        files.add(targetFile);

        return a;
    }

    @GetMapping("/community/modifypost")
    public String modifyPost(Model model, @RequestParam("boardcode") Long boardcode){

        Optional<Board> post = boardService.findById(boardcode);
        model.addAttribute("post", post.get());
        return "board/community_modifypost";
    }

    @PostMapping("/community/modifypost")
    public String modifySubmit(@RequestParam("title") String title, @RequestParam("content") String content,
                               @RequestParam("boardcode") Long boardcode, HttpServletRequest req){

        HttpSession session = req.getSession();


        Optional<Board> boardOptional = boardService.findById(boardcode);
        Board board = boardOptional.get();
        board.setTitle(title);
        content = content.replace("/summernoteImage/",
                "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/");
        board.setContent(content);
        boardService.newPost(board);

        String[] pathContent = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            s3Uploader.upload(file, "images",0);
            pathContent[i]= file.getPath().replace("C:\\summernote_image\\",
                    "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/");
            Boardimg boardimg = new Boardimg(boardcode, file.getName(), "생략", pathContent[i]);
            boardimgService.save(boardimg);
        }

        files.clear();

        return "redirect:/community";
    }

    @GetMapping("/community/delete")
    public String deletePost(@RequestParam("boardcode") Long boardcode){
        boardService.deletePost(boardcode);
        boardimgService.deleteImg(boardcode);
        return "redirect:/community";
    }

    @PostMapping("/community/newreply")
    @ResponseBody
    public Reply newreply(HttpServletRequest req, @RequestParam("writer") String writer,
                           @RequestParam("content") String content,
                           @RequestParam("boardcode") Long boardcode) {

        Reply reply = new Reply();
        reply.setWriter(writer);
        reply.setContent(content);
        reply.setBoardcode(boardcode);
        Reply reply1 = replyService.newReply(reply);
        return reply1;
    }

    @GetMapping("/community/deletereply")
    @ResponseBody
    public Long deleteReply(@RequestParam Long rid){
        replyService.deleteReply(rid);
        return rid;
    }

}
