package com.capstone.moa.service;

import com.capstone.moa.dto.CreateGroupRequest;
import com.capstone.moa.dto.ModifyGroupRequest;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Member;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createGroup(CreateGroupRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (groupRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Duplicate group name");
        }

        Group group = new Group(request.getName(), request.getInterest(), request.getIntroduce());
        GroupMember groupMember = new GroupMember(group, member, "LEADER");
        group.putGroupMember(groupMember);

        groupRepository.save(group);
    }

    @Transactional
    public void modifyGroup(Long groupId, ModifyGroupRequest request) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        GroupMember groupMember = groupMemberRepository.findGroupMemberByGroupAndMember(group, member)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));

        if (!groupMember.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the leader of this group");
        }

        group.modify(request.getGroupName(), request.getInterest(), request.getIntroduce());
    }
}