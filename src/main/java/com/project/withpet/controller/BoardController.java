package com.project.withpet.controller;

import com.project.withpet.domain.Board;
import com.project.withpet.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/community")
    public String community() {
        return "board/community";
    }

    @GetMapping("/community/newpost")
    public String createPost(HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")==null){
            return "login";
        } else {
            return "board/community_newpost";
        }

    }

    @PostMapping("/community/newpost")
    public String create(BoardForm form, HttpServletRequest req){
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        HttpSession session = req.getSession();
        Object userId = session.getAttribute("userid");
        board.setWriter(userId.toString());
        Long boardCode = boardService.newPost(board);
        return "redirect:/community";
    }

}
