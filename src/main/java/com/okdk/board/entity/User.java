package com.okdk.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "user_password", nullable = false, length = 50)
    private String userPassword;

    @Column(name = "join_dttm", nullable = false, length = 17)
    private String joinDttm;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boards;

    @Column(name = "birthday", nullable = true, length = 8)
    private String birthday;

    @Column(name = "phone_no", nullable = true, length = 11)
    private String phoneNo;

    @Column(name = "authority_type", nullable = false, length = 10)
    private String authorityType;

}
