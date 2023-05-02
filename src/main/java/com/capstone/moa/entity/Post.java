package com.capstone.moa.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    public Post(Member member, String title, String content, String interest) {
        this(member, title, content, Interest.find(interest));
    }

    private Post(Member member, String title, String content, Interest interest) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.interest = interest;
    }
}