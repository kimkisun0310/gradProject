package graduProject.server.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostFormVO {
    private String title;
    private String contents;
    private int price;
    private Long authorId;
    private double latitude;
    private double longitude;
}
