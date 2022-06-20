package graduProject.server.service;

import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import graduProject.server.repository.PostRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional()
public class PostServiceTest {
    @Autowired PostService postService;

    @Test
    public void create() throws Exception{
        //given
        Member member = new Member();
        member.setUserName("user1");

        //when
        Post post = postService.create(member, "title", "content", 333);
        postService.post(post);
        List<Post> test = postService.findAll();

        //then
        for(Post post1 : test){
            System.out.println("post3894894123123 : " + post1.getTitle() + " " + post1.getContents() + " " + post1.getPrice());
        }
    }

    @Test
    public void delete() throws Exception{
        //given
        Member member = new Member();
        member.setUserName("user1");

        //when
        Post post = postService.create(member, "title", "content", 333);
        postService.post(post);
        List<Post> test = postService.findAll();

        //then
        for(Post post1 : test){
            System.out.println("post3894894123123 : " + post1.getTitle() + " " + post1.getContents() + " " + post1.getPrice() + " " + post1.getStatus());
        }

        //when
        postService.delete(post.getId());
        test = postService.findAll();

        //then
        assertEquals("삭제되어서 list가 empty여야한다.", 0, test.size());
    }


}