package com.project.withpet.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Orderprod {
    @Id
    @JoinColumn(name = "ordertable_orderid")
    private Long orderid;

    private String prodlist;
    private String count;

    public Orderprod(Long orderid, String prodlist, String count) {
        this.orderid = orderid;
        this.prodlist = prodlist;
        this.count = count;
    }

    public Orderprod() {
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getProdlist() {
        return prodlist;
    }

    public void setProdlist(String prodlist) {
        this.prodlist = prodlist;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
