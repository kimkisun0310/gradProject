package graduProject.server.service;

import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import graduProject.server.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = false)
    public Long post(Post post) { return postRepository.save(post); }

    public Post findOne(Long id){ return postRepository.findOne(id); }

    public List<Post> findAll(){return postRepository.findAll(); }
    public List<Post> findByTitle(String title){ return postRepository.findByTitle(title); }
    public List<Post> findByAuthor(Long authorId){ return postRepository.findByUser(authorId); }

    @Transactional(readOnly = false)
    public void delete(Long postId){
        Post post = postRepository.findOne(postId);
        post.delete();
    }

    @Transactional(readOnly = false)
    public Post create(Member author, String title, String contents, int price, double latitude, double longitude){
        Post post = postRepository.createPost(author, title, contents, price, latitude, longitude);
        return post;
    }
}