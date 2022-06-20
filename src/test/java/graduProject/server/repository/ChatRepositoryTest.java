package graduProject.server.repository;

import graduProject.server.domain.Chat;
import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ChatRepositoryTest {
    @Autowired ChatRepository chatRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    @Test
    public void ChatRepositoryTest() throws Exception{
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setUserName("member1");
        member2.setUserName("member2");
        Chat chat = new Chat();
        chat.setMember1(member1);
        chat.setMember2(member2);
        member1.addChat(chat);
        member2.addChat(chat);
        Post post = new Post();
        post.setTitle("1233");
        chat.setPost(post);
        post.addChat(chat);
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        chatRepository.save(chat);
        postRepository.save(post);

        //then
        for(Chat chat1 : chatRepository.findByPost(post)){
            System.out.println("findByPost : " + chat1.getPost().getTitle());
        }
        for(Chat chat1 : chatRepository.findByAuthor(member2)){
            System.out.println("findByAuthor : " + chat1.getPost().getTitle());
        }

        for(Chat chat1 : chatRepository.findByMember(member1)){
            System.out.println("findByMember1 : " + chat1.getPost().getTitle());
        }

        for(Chat chat1 : chatRepository.findByMember(member2)){
            System.out.println("findByMember2 : " + chat1.getPost().getTitle());
        }
    }
}