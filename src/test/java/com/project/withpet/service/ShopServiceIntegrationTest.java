package com.project.withpet.service;

import com.project.withpet.domain.ShopForm;
import com.project.withpet.domain.Shop;
import com.project.withpet.repository.ShopQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class ShopServiceIntegrationTest {

    @Autowired
    ShopQueryRepository shopQueryRepository;

    @Test
    public void 호텔수() throws  Exception{
        List<ShopForm> shops = shopQueryRepository.findShops();
        assertEquals(shops.size(), 10);
    }

//    @Test
//    public void 호텔외래키() throws Exception{
//        List<ShopForm> allShops = shopQueryRepository.findAllShops();
//
//    }

    @Test
    public void 호텔조회() throws Exception{

        Shop shop = shopQueryRepository.findByName("합정 라드");
        assertEquals(shop.getShopid(), 2);

//        Board board = new Board();
//        board.setTitle("안녕하세요");
//        board.setWriter("김민지");
//        board.setContent("날씨 좋네요");
//        Long code = boardService.newPost(board);
//        Optional<Board> post = boardRepository.findById(code);
//        assertEquals(post.get().getTitle(), board.getTitle());
//        assertEquals(post.get().getContent(), board.getContent());
//        assertEquals(post.get().getWriter(), board.getWriter());
    }
}
