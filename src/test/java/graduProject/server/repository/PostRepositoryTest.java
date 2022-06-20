package graduProject.server.repository;

import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    @Test
    public void PostRepositoryTest() throws Exception{
        //given
        Member member = new Member();
        Post post = new Post();
        member.setUserName("user1");
        post.setTitle("post1");
        //when
        memberRepository.save(member);
        postRepository.save(post);
        member.addPost(post);
        post.setAuthor(member);
        List<Post> test = postRepository.findByAuthor(member.getUserName());

        //then
        for(Post post1 : test){
            System.out.println("결과 : " + post1.getTitle() + " " + post1.getAuthor().getUserName());
        }
    }

    @Test
    public void findall() throws Exception{
        //given
        Member member = new Member();
        Post post = new Post();
        member.setUserName("1233");
        post.setTitle("post1");

        //when
        postRepository.save(post);
        List<Post> test = postRepository.findAll();

        //then
        for(Post post1 : test){
            System.out.println("결과 : " + post1.getTitle() );
        }
    }
    
    @Test
    public void create() throws Exception{
        //given
        Member member = new Member();
        member.setUserName("name");
        Post post = new Post();
        post.setTitle("poststjejtj");
        postRepository.save(post);
        //when
        List<Post> test = postRepository.findAll();

        //then
        for(Post post1 : test){
            System.out.println("결과 : " + post1.getTitle() );
        }
    }
}