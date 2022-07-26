package com.project.withpet.controller;

import com.project.withpet.domain.Board;
import com.project.withpet.domain.Reply;
import com.project.withpet.service.BoardService;
import com.project.withpet.service.BoardimgService;
import com.project.withpet.service.ReplyService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final BoardimgService boardimgService;
//    private final S3Uploader s3Uploader;

    private final ReplyService replyService;

    @Autowired
    public BoardController(BoardService boardService, BoardimgService boardimgService, ReplyService replyService) {
        this.boardService = boardService;
        this.boardimgService = boardimgService;
//        this.s3Uploader = s3Uploader;
        this.replyService = replyService;
    }

    @GetMapping("/community")
    public String posts(Model model,
                        @PageableDefault(sort = "date", direction = Sort.Direction.DESC, size = 6)
                        Pageable pageable, HttpServletRequest req) {

        Page<Board> posts = boardService.postList(pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", posts.hasNext());
        model.addAttribute("hasPrev", posts.hasPrevious());
        model.addAttribute("totalPage", posts.getTotalPages());
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "board/community";
    }

    @GetMapping("/community/postdetail")
    public String postdetail(Model model, @RequestParam("boardcode") Long boardcode,
                             HttpServletRequest req){
        Optional<Board> post = boardService.findById(boardcode);
        model.addAttribute("title", post.get().getTitle());
        model.addAttribute("writer", post.get().getWriter());
        model.addAttribute("date", post.get().getDate());
        model.addAttribute("boardcode", post.get().getBoardcode());
        model.addAttribute("content", post.get().getContent());

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "board/community_postdetail";
    }

    @PostMapping("/community/postdetail")
    @ResponseBody
    public Object replies(HttpServletRequest req,
                           @RequestParam("boardcode") Long boardcode){

        List<Reply> replies = replyService.findList(boardcode);
        return replies;

    }


    @GetMapping("/community/newpost")
    public String createPost(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")==null){
            return "redirect:/login";
        } else {
            return "board/community_newpost";
        }
    }

    @PostMapping("/community/newpost")
    public String create(BoardForm form, HttpServletRequest req
                        ) throws IOException {

        HttpSession session = req.getSession();

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        Object userId = session.getAttribute("userid");
        board.setWriter(userId.toString());
        boardService.newPost(board);
        return "redirect:/community";

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
