package com.project.withpet.dto;

import com.project.withpet.domain.Ordertable;

import java.util.ArrayList;

public class OrderListDTO {
    ArrayList<ProdandCount> prodandCounts;

    Ordertable ordertable;


    public ArrayList<ProdandCount> getProdandCounts() {
        return prodandCounts;
    }

    public void setProdandCounts(ArrayList<ProdandCount> prodandCounts) {
        this.prodandCounts = prodandCounts;
    }

    public Ordertable getOrdertable() {
        return ordertable;
    }

    public void setOrdertable(Ordertable ordertable) {
        this.ordertable = ordertable;
    }

    public OrderListDTO(ArrayList<ProdandCount> prodandCounts, Ordertable ordertable) {
        this.prodandCounts = prodandCounts;
        this.ordertable = ordertable;
    }
}
