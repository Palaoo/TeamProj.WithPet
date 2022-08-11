package com.project.withpet.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FeatlistId implements Serializable {
    private Long shopid;
    private Long featid;

}
