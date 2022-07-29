package god.withpet.entity;

import god.withpet.entity.cafe;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class feat {

    @Id
    @Column(name = "featid")
    private Long featid;

    @Column
    private String featname;

    @ManyToMany(mappedBy = "shopFeats")
    Set<cafe> feats;

}
