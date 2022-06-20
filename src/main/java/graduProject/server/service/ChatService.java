package graduProject.server.service;

import graduProject.server.domain.Chat;
import graduProject.server.domain.ChatStatus;
import graduProject.server.domain.Member;
import graduProject.server.domain.Post;
import graduProject.server.repository.ChatRepository;
import graduProject.server.repository.MemberRepository;
import graduProject.server.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final ChatRepository chatRepository;

    public List<Chat> findByPost(Post post){
        return chatRepository.findByPost(post);
    }

    public List<Chat> findByMember(Member member){
        return chatRepository.findByMember(member);
    }

    public List<Chat> findByAuthor(Member member){
        return chatRepository.findByAuthor(member);
    }
}
