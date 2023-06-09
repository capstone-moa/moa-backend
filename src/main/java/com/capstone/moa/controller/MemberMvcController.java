package com.capstone.moa.controller;

import com.capstone.moa.dto.*;
import com.capstone.moa.entity.Interest;
import com.capstone.moa.service.GroupService;
import com.capstone.moa.service.InvitationService;
import com.capstone.moa.service.MemberService;
import com.capstone.moa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberMvcController {

    private final PostService postService;
    private final GroupService groupService;
    private final InvitationService invitationService;
    private final MemberService memberService;

    @GetMapping("/mypage/{memberId}/activity")
    public String findMemberActivities(@PathVariable("memberId") Long memberId, Model model) {
        FindPostsResponse postList = postService.findPostsByMember(memberId);
        FindMemberByIdResponse member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("posts", postList.posts());
        return "member/mypage_activity";
    }

    @GetMapping("/mypage/{memberId}/group")
    public String findMemberGroups(@PathVariable("memberId") Long memberId, Model model) {
        List<FindGroupByMemberIdResponse> groups = groupService.findGroupsByMemberId(memberId);
        List<FindInvitationResponse> invitations = invitationService.findInvitationsByMember(memberId);
        FindMemberByIdResponse member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("groups", groups);
        model.addAttribute("invitations", invitations);

        model.addAttribute("createGroupRequest", new CreateGroupRequest());
        return "member/mypage_group";
    }

    @ModelAttribute("interests")
    private Interest[] putInterest() {
        return Interest.values();
    }
}
