package com.project.withpet.controller;

import com.google.gson.JsonObject;
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
        for(int i=0; i<posts.getSize(); i++) {
            BoardForm boardForm = new BoardForm();
            boardForm.setBoardcode(posts.getContent().get(i).getBoardcode());
            System.out.println("boardcode="+posts.getContent().get(i).getBoardcode());
            boardForm.setDate(posts.getContent().get(i).getDate());
            boardForm.setTitle(posts.getContent().get(i).getTitle());
            Long boardcode = posts.getContent().get(i).getBoardcode();
            Optional<Boardimg> thumbnail = boardimgService.findOne(boardcode);
            if(!thumbnail.isEmpty()) {
                String path = thumbnail.get().getPath();
                path = path.replace("C:\\summernote_image\\", "https://withpetimage.s3.ap-northeast-2.amazonaws.com/images/");
                System.out.println("path="+path);
                boardForm.setPath(path);
            } else {
                String path = "https://withpetimage.s3.ap-northeast-2.amazonaws.com/images/15fe7978-b10a-45c8-9364-44a9e035304692ed0fc7-1005-4a30-a088-05ed3dfffb2f.jfif";
                boardForm.setPath(path);
            }
            postList.add(boardForm);
        }

        model.addAttribute("posts", postList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", posts.hasNext());
        model.addAttribute("hasPrev", posts.hasPrevious());
        model.addAttribute("totalPage", posts.getTotalPages());
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
            return "board/community_newpost";
        }
    }

    @PostMapping("/community/newpost")
    public String create(BoardForm form, HttpServletRequest req
    ) throws IOException {
        String content = form.getContent();
        content = content.replace("/summernoteImage/", "https://withpetimage.s3.ap-northeast-2.amazonaws.com/images/");

        System.out.println(content+"\n");

        HttpSession session = req.getSession();

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(content);
        Object userId = session.getAttribute("userid");
        board.setWriter(userId.toString());
        Long boardCode = boardService.newPost(board);

        String[] pathContent = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            s3Uploader.upload(file, "images");
            pathContent[i]= file.getPath();
            Boardimg boardimg = new Boardimg(boardCode, file.getName(), "생략", pathContent[i]);
            boardimgService.save(boardimg);
        }

        files.clear();

//        new Boardimg(boardCode,form.getTitle(),);

        //        @RequestParam(value = "file") MultipartFile files
//        String path = s3Uploader.uploadFiles(files, "withpetimage");
//
//        HttpSession session = req.getSession();
//
//        Long boardCode = (Long) session.getAttribute("boardCodestr");
//        Boardimg boardimg = new Boardimg();
//
//        boardimg.setBoardcode(boardCode);
//        boardimg.setName(files.getName());
//        boardimg.setOrigname(UUID.randomUUID().toString());
//        boardimg.setPath(path);
//        boardimgService.save(boardimg);
        return "redirect:/community";
    }

    @GetMapping("/community/modifypost")
    public String modifyPost(Model model, @RequestParam("boardcode") Long boardcode){
        Optional<Board> post = boardService.findById(boardcode);
        model.addAttribute("post", post.get());
        return "board/community_modifypost";
    }

    ArrayList<File> files = new ArrayList<>();

    @PostMapping("/community/uploadimg")
    @ResponseBody
    public String uploadImg(HttpServletRequest req,
                            @RequestParam("file") MultipartFile multipartFile) throws IOException {
//        String URL = s3Uploader.uploadFiles(multipartFile, "images");
//        System.out.println(" 이미지 URL !!! :" + URL);


        System.out.println(multipartFile);
        JsonObject jsonObject = new JsonObject();
//        String contextRoot = new HttpServletRequestWrapper(req).getRealPath("/");
//        String fileRoot = contextRoot + "resources/fileupload/";
        String fileRoot = "C:\\summernote_image\\";
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID() + extension;
//            System.out.println("fileRoot="+fileRoot);
        System.out.println("originalFileName=" + originalFileName);
        System.out.println("extentsion=" + extension);
        System.out.println("savedFileName=" + savedFileName);
        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImage/" + savedFileName);
//            jsonObject.addProperty("url", "/summernoteimage/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");
            System.out.println("try=" + jsonObject.toString());
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
            System.out.println("catch=" + jsonObject.toString());
        }
        String a = jsonObject.toString();
        files.add(targetFile);

        return a;
    }

    @GetMapping("/community/delete")
    public String deletePost(@RequestParam("boardcode") Long boardcode){
        boardService.deletePost(boardcode);
        return "redirect:/community";
    }

    @PostMapping("/community/newreply")
    @ResponseBody
    public Object newreply(HttpServletRequest req, @RequestParam("writer") String writer,
                           @RequestParam("content") String content,
                           @RequestParam("boardcode") Long boardcode) {

        Reply reply = new Reply();
        reply.setWriter(writer);
        reply.setContent(content);
        reply.setBoardcode(boardcode);
        replyService.newReply(reply);
        List<Reply> replies = replyService.findList(boardcode);
        return replies;
    }



}
