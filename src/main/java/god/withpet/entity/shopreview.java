package god.withpet.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class shopreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @ManyToOne
    @JoinColumn(name = "shopid")
    private cafe cafe;

    @Column
    private String content;

    @Column
    @CreatedDate
    private String date;

    @Column
    private Long score;

    @ManyToOne
    @JoinColumn(name = "userid")
    private member member;

//    @ManyToMany
//    @JoinColumn(name = "userid")
//    private member member;
}
