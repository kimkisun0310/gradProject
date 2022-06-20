package graduProject.server.service;

import graduProject.server.domain.Member;
import graduProject.server.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setUserName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberService.findOne(saveId));
    }
    
    @Test
    public void 중복회원제거() throws Exception{
        //given

        Member member1 = new Member();
        member1.setUserName("kim");
        Member member2 = new Member();
        member2.setUserName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야 함.");
    }
}