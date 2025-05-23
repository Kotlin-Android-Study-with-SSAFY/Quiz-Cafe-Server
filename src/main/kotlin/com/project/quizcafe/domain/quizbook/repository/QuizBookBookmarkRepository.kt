package com.project.quizcafe.domain.quizbook.repository

import com.project.quizcafe.domain.quizbook.entity.QuizBookBookmark
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizBookBookmarkRepository : JpaRepository<QuizBookBookmark, Long> {
    fun findByUserIdAndQuizBookId(userId: Long, quizBookId: Long): QuizBookBookmark?
    fun findAllByUserId(userId: Long): List<QuizBookBookmark>
    fun deleteByUserIdAndQuizBookId(userId: Long, quizBookId: Long)
}
