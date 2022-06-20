package graduProject.server.repository;

import graduProject.server.domain.Chat;
import graduProject.server.domain.ChatStatus;
import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Chat chat){
        em.persist(chat);
        return chat.getId();
    }

    public List<Chat> findByPost(Post post){
        return post.getChats();
    }

    public List<Chat> findByMember(Member member){
        return member.getChats();
    }

    public List<Chat> findByAuthor(Member member){
        Long memId = member.getId();
        return em.createQuery("select c from Chat c where c.post.author.id = :memId", Chat.class)
                .setParameter("memId", memId)
                .getResultList();
    }
}
