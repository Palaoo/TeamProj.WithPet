package com.project.withpet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(FeatlistId.class)
public class Featlist {

    @Id
    private Long shopid;
    @Id
    private Long featid;

}
