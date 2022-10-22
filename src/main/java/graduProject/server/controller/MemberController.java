package graduProject.server.controller;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import graduProject.server.domain.Address;
import graduProject.server.domain.Member;
import graduProject.server.form.MemberFormVO;
import graduProject.server.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("/members")
    public CreateMemberResponse create(@RequestBody MemberFormVO memberFormVO){
        Member member = new Member();
        member.setUserName(memberFormVO.getName());
        member.setEmail(memberFormVO.getEmail());
        member.setPassword(memberFormVO.getPassword());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 멤버 리스트 출력
    @GetMapping("/members")
    public Result list(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = new ArrayList<>();
        for(Member member : members){
            MemberDto memberDto = new MemberDto(member.getUserName(), member.getPicURL(), member.getScore());
            collect.add(memberDto);
        }
        return new Result(collect);
    }

    //개인정보 출력
    @GetMapping("/members/{userId}")
    public Result findById(@PathVariable("userId") Long userId){
        Member member = memberService.findOne(userId);
        MemberDto memberDto = new MemberDto(member.getUserName(), member.getPicURL(), member.getScore());
        return new Result(memberDto);
    }


    @Data
    static class CreateMemberResponse{
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
        private String picURL;
        private int score;
    }
}
