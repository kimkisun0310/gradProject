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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    //게시물 등록
    @PostMapping("/posts")
    public CreatePostResponse create(@RequestBody @Valid PostFormVO postFormVO){
        Member member = memberService.findOne(postFormVO.getAuthorId());
        Post post = postService.create(member, postFormVO.getTitle(), postFormVO.getContents(), postFormVO.getPrice(),
                postFormVO.getLatitude(), postFormVO.getLongitude());
        Long id = postService.post(post);
        return new CreatePostResponse(id);
    }

    //게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable("postId") Long postId){
        postService.delete(postId);
    }

    //게시물 리스트
    @GetMapping("/posts")
    public Result list(){
        List<Post> posts = postService.findAll();
        List<PostDto> collect = new ArrayList<>();
        for(Post post : posts){
            if(post.getStatus()==PostStatus.DELETE)continue;
            PostDto postDto = new PostDto(post.getTitle(), post.getContents(), post.getTime().toString(),
                    post.getAuthor().getUserName(), post.getAuthor().getPicURL(),
                    post.getView(), post.getPrice(), post.getId(), post.getLatitude(), post.getLongitude());
            collect.add(postDto);
        }
        return new Result(collect);
    }

    //게시물 확인하기
    @GetMapping("/posts/{postId}")
    public Result clickPost(@PathVariable("postId") Long postId){
        Post post = postService.findOne(postId);
        if(post.getStatus()==PostStatus.DELETE)return new Result(-1);
        PostDto postDto = new PostDto(post.getTitle(), post.getContents(),
                post.getTime().toString(),
                post.getAuthor().getUserName(), post.getAuthor().getPicURL(),
                post.getView(), post.getPrice(), post.getId(), post.getLatitude(), post.getLongitude());
        post.upView();
        return new Result(postDto);
    }


    //내 게시물
    @GetMapping("/posts/who/{userId}")
    public Result postList(@PathVariable("userId") Long userId){
        List<Post> posts = postService.findByAuthor(userId);
        List<PostDto> collect = new ArrayList<>();
        for(Post post : posts){
            if(post.getStatus()==PostStatus.DELETE)continue;
            PostDto postDto = new PostDto(post.getTitle(), post.getContents(), post.getTime().toString(),
                    post.getAuthor().getUserName(), post.getAuthor().getPicURL(),
                    post.getView(), post.getPrice(), post.getId(), post.getLatitude(), post.getLongitude());
            collect.add(postDto);
        }
        return new Result(collect);
    }


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
        private String localDateTime;
        private String authorName;
        private String authorPic;
        private int view;
        private int price;
        private Long postId;
        private double latitude;
        private double longitude;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
