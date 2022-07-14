package com.example.WithPet.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Prod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prod_id;

}
