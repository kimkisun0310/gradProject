package graduProject.server.repository;

import graduProject.server.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void MemberRepositoryTest() throws Exception{
        //given
        Member member = new Member();
        member.setUserName("memberA");
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
    }

    @Test
    public void 여러명찾기() throws Exception{
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setUserName("test1");
        member2.setUserName("test2");

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> test = new ArrayList<>();
        test.add(member1);
        test.add(member2);
        List<Member> members = memberRepository.findAll();

        //then
        Assertions.assertThat(members.containsAll(test));
    }

    @Test
    public void 이름으로찾기() throws Exception{
        //given
        Member member = new Member();
        Member member2 = new Member();
        member2.setUserName("member1");
        member.setUserName("member1");

        //when
        memberRepository.save(member);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findByName("member1");

        //then
        for(Member member1 : members){
            System.out.println("결과 : " + member1.getId() + " " + member1.getUserName());
        }
    }

}