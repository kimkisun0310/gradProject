package graduProject.server.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import graduProject.server.domain.PostStatus;
import graduProject.server.form.PostFormVO;
import graduProject.server.service.MemberService;
import graduProject.server.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    //등록
    @PostMapping("/posts/new")
    public CreatePostResponse create(@RequestBody @Valid PostFormVO postFormVO){
        Member member = memberService.findOne(postFormVO.getAuthorId());
        Post post = postService.create(member, postFormVO.getTitle(), postFormVO.getContents(), postFormVO.getPrice());
        Long id = postService.post(post);
        return new CreatePostResponse(id);
    }

    //시간순정렬
    @GetMapping("/posts")
    public List<Post> list(){
        List<Post> posts = postService.findAll();
        Collections.sort(posts, Collections.reverseOrder());
        JsonArray ja = new JsonArray();
        for(Post post : posts){
            JsonObject obj = new JsonObject();
            obj.addProperty("postId", post.getId());
            obj.addProperty("title", post.getTitle());
            obj.addProperty("author", post.getAuthor().getUserName());
            obj.addProperty("price", post.getPrice());
            obj.addProperty("time", post.getTime().toString());
            ja.add(obj);
        }
        JsonObject jo = new JsonObject();
        jo.add("Posts", ja);
        return posts;
    }
    //삭제
    @PostMapping("/posts/{postId}/del")
    public void deletePost(@PathVariable("postId") Long postId){
        postService.delete(postId);
    }

    @GetMapping("/posts/{postId}")
    public Result clickPost(@PathVariable("postId") Long postId){
        Post post = postService.findOne(postId);
        PostDto postDto = new PostDto(post.getTitle(), post.getContents(), post.getAuthor().getUserName(), post.getTime(), post.getPrice(), post.getUp(), post.getWishNum());
        return new Result(postDto);
    }


    //게시글 제목으로 찾기
    /**url 주소 때문에 일단 스킵*/

    //게시글 작성자로 찾기
    @Data
    static class CreatePostResponse{
        private Long id;

        public CreatePostResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    @AllArgsConstructor
    static class PostDto{
        private String title;
        private String contents;
        private String author;
        private LocalDateTime localDateTime;
        private int price;
        private int up;
        private int wishNum;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}
