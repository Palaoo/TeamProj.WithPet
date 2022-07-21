//package com.project.withpet.domain;
//
//import javax.persistence.Embeddable;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import java.io.Serializable;
//
//@Embeddable
//public class Featlist  implements Serializable {
//
//    @JoinColumn(name = "shop_shopid")
//    private Long shopid;
//
//    @ManyToOne
//    @JoinColumn(name = "featid")
//    private Feat feat;
//
//    public Long getShopid() {
//        return shopid;
//    }
//
//    public void setShopid(Long shopid) {
//        this.shopid = shopid;
//    }
//
//    public Feat getFeat() {
//        return feat;
//    }
//
//    public void setFeat(Feat feat) {
//        this.feat = feat;
//    }
//}
