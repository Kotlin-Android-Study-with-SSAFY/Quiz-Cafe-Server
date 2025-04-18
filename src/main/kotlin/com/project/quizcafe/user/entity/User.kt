package com.project.quizcafe.user.entity

import com.project.quizcafe.common.model.Role
import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_user_login_email", columnNames = ["loginEmail"])]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "login_email", nullable = false, unique = true)
    val loginEmail: String,
    @Column(nullable = false)
    val password: String,
    @Column(name = "nick_name", nullable = false)
    val nickName: String,
    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER
)