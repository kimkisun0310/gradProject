package graduProject.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Chat {
    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member1_id")
    private Member member1;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member2_id")
    private Member member2;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private ChatStatus chatStatus;

    //== 비즈니스 로직 ==/

    /**
     * chat 생성
     */
    public static Chat createChat(Member member, Post post){
        Chat chat = new Chat();
        chat.setPost(post);
        chat.setMember2(member);
        chat.setMember1(post.getAuthor());
        chat.setChatStatus(ChatStatus.CONTINUE);
        return chat;
    }

    /**
     * 종료
     */
    public void exit(){
        this.setChatStatus(ChatStatus.DONE);
    }
}
