package graduProject.server.repository;

import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import graduProject.server.domain.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    @PersistenceContext
    private final EntityManager em;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    public List<Post> findAll(){
        List<Post> tmp = em.createQuery("select p from Post p", Post.class)
                .getResultList();
//        List<Post> ret = new ArrayList<>();
//        for(Post post : tmp){
//            if(post.getStatus() == PostStatus.DELETE)continue;
//            ret.add(post);
//        }
        return tmp;
    }

    public List<Post> findByTitle(String title){
        List<Post> tmp =  em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
        List<Post> ret = new ArrayList<>();
        for(Post post : tmp){
            if(post.getStatus()== PostStatus.DELETE)continue;
            ret.add(post);
        }
        return ret;
    }

    public List<Post> findByUser(Long userId){
        List<Post> tmp = em.createQuery("select p from Post p where p.author.id = :userId", Post.class)
                .setParameter("userId", userId)
                .getResultList();
        List<Post> ret = new ArrayList<>();
        for(Post post : tmp){
            if(post.getStatus() == PostStatus.DELETE)continue;
            ret.add(post);
        }
        return ret;
    }

    public Post createPost(Member author, String title, String contents, int price, double latitude, double longitude){
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        post.setPrice(price);
        post.setView(0);
        post.setTime(LocalDateTime.now());
        post.setStatus(PostStatus.CONTINUE);
        post.setAuthor(author);
        post.setLatitude(latitude);
        post.setLongitude(longitude);
        author.addPost(post);
        return post;
    }

}
