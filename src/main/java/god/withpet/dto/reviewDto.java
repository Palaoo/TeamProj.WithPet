package god.withpet.dto;

import god.withpet.entity.cafe;
import god.withpet.entity.member;
import god.withpet.entity.shopreview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class reviewDto {
    private Long rid;
    private String content;
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private Long score;
    private member member;
    private cafe cafe;

    public shopreview toEntity() {
        shopreview shopreview =

    }


}
