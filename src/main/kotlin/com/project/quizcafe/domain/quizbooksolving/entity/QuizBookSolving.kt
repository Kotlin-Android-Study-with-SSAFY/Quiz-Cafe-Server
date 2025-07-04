package com.project.quizcafe.domain.quizbooksolving.entity

import com.project.quizcafe.domain.user.entity.User
import com.project.quizcafe.domain.quizbook.entity.QuizBook
import com.project.quizcafe.domain.quizsolving.entity.QuizSolving
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "quiz_book_solving")
class QuizBookSolving(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_book_id", nullable = false)
    var quizBook: QuizBook,

    @Column(nullable = false)
    var version: Long,

    @Column(nullable = false)
    var totalQuizzes: Int,

    @Column(nullable = false)
    var correctCount: Int,

    @Column(name = "solving_time_seconds", nullable = true)
    var solvingTimeSeconds: Long? = null,

    @Column(name = "completed_at", nullable = false)
    var completedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "quizBookSolving", cascade = [CascadeType.ALL], orphanRemoval = true)
    val quizSolvings: MutableList<QuizSolving> = mutableListOf()
)
