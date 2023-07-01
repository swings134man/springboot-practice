package boot.bootprac.cms.member.controller;

import boot.bootprac.cms.member.domain.Member;
import boot.bootprac.cms.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService; // Spring Container에 등록.

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    // List
    @GetMapping("/members")
    public String List(Model model) {
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);

        return "members/memberList";
    }

    //단건 조회
    @GetMapping("/members/findone")
    public String findOne(@RequestParam Long id, Model model) {

       // Optional<Member> member = memberService.findOne(id);
        //Member member1 = member.get();

        Member member1 = memberService.findOne(id);


        model.addAttribute("member", member1);

        return "members/findMember";
    }
}
