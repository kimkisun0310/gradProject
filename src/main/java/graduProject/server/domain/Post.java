package graduProject.server.domain;

import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Post implements Comparator<Post>{
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String contents;
    private int price;
    private int wishNum;
    private int view;
    LocalDateTime time;
    @Embedded
    Address address;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @OneToMany(mappedBy = "post")
    private List<Chat> chats = new ArrayList<>();

    @ManyToMany(mappedBy = "posts")
    private List<Category> categories = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private static HashMap<String, Boolean> upMap;

    /**
     * 비즈니스로직
     */
    public void addChat(Chat chat){
        this.chats.add(chat);
    }

    /**
     * 게시글 수정
     */
    public void edit(String title, String contents, int price){
        this.setTitle(title);
        this.setContents(contents);
        this.setTime(LocalDateTime.now());
        this.setPrice(price);
    }

    /**
     * 게시글 삭제
     */
    public void delete(){
        this.setStatus(PostStatus.DELETE);
        for(Chat chat : this.getChats()){
            chat.setChatStatus(ChatStatus.DONE);
        }
    }

    //오름차순 정렬
    @Override
    public int compare(Post p1, Post p2) {
        String pp1 = p1.getTime().toString();
        String pp2 = p2.getTime().toString();
        LocalDateTime dateTime1 = LocalDateTime.parse(pp1);
        LocalDateTime dateTime2 = LocalDateTime.parse(pp2);
        if(dateTime1.isBefore(dateTime2)){
            return -1;
        }
        else if(dateTime1.isAfter(dateTime2)){
            return 1;
        }
        else return 0;
    }
}
