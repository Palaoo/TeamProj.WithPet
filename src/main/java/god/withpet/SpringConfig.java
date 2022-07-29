package god.withpet;

import god.withpet.repository.ShopLike.JpaShopLikeRepository;
import god.withpet.repository.ShopLike.ShopLikeRepository;
import god.withpet.service.ShopLikeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public ShopLikeService shopLikeService(){
        return new ShopLikeService(shopLikeRepository());
    }

    @Bean
    public ShopLikeRepository shopLikeRepository(){
        return new JpaShopLikeRepository(em);

    }
}
