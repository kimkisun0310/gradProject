package graduProject.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String picURL;
    private int score;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member1")
    private List<Chat> Chats = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();


    /*
          post 추가 Method
     */
    public void addPost(Post post){ this.posts.add(post); }

    public void addChat(Chat chat){
        this.Chats.add(chat);
    }


}
