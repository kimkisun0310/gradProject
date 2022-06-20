package graduProject.server.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PostForm {
    @NotEmpty(message = "제목 입력은 필수입니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    private int price;
}
