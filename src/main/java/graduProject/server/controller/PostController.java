package graduProject.server.controller;

import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import graduProject.server.domain.PostStatus;
import graduProject.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/new")
    public String createForm(Model model){
        model.addAttribute("postForm", new PostForm());
        return "posts/createPostForm";
    }
    @PostMapping("/posts/new")
    public String create(@Valid PostForm form, BindingResult result){
        if(result.hasErrors()){
            return "posts/createPostForm";
        }
        Member member = new Member();
        Post post = postService.create(member, form.getTitle(), form.getContents(), form.getPrice());
        postService.post(post);
        return "redirect:/";
    }

    @GetMapping("/posts")
    public String list(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "posts/postList";
    }

    @PostMapping("/posts/{postId}/del")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.delete(postId);
        return "redirect:/posts";
    }

}
