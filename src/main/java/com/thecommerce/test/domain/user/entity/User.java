package com.thecommerce.test.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thecommerce.test.domain.user.dto.request.JoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    @Comment("사용자 테이블 PK")
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(length = 15, nullable = false, unique = true)
    private String phoneNumber;

    @Column(length = 100, nullable = false, unique = true)
    private String email;
}
