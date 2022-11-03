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
        try{
            validateDuplicateMember(member.getEmail()); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        }catch(Exception e){
            return -1L;
        }
    }

    private void validateDuplicateMember(String memberEmail) {
        //EXCEPTION
        List<Member> members = memberRepository.findByEmail(memberEmail);
        if(!members.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Long validateEmailPassword(String email, String password){
        List<Member> members = findByEmail(email);
        if(members.isEmpty())return -1L;
        else{
            if(password.equals(members.get(0).getPassword()))return members.get(0).getId();
            else return -1L;
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public List<Member> findByEmail(String email){return memberRepository.findByEmail(email);}

    public List<Member> findByName(String name){ return memberRepository.findByName(name); }

}
