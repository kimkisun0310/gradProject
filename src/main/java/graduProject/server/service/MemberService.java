package graduProject.server.service;

import graduProject.server.domain.Member;
import graduProject.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원가입
     */
    @Transactional(readOnly = false)
    public Long join(Member member){
        validateDuplicateMember(member.getEmail()); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(String memberEmail) {
        //EXCEPTION
        Member member = memberRepository.findByEmail(memberEmail);
        if(member!=null){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Long validateEmailPassword(String email, String password){
        Member member = findByEmail(email);
        if(member.getPassword()==password){
            return member.getId();
        }
        else return -1L;
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public Member findByEmail(String email){return memberRepository.findByEmail(email);}

    public List<Member> findByName(String name){ return memberRepository.findByName(name); }

}
