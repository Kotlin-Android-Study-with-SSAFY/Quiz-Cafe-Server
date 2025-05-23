package com.project.quizcafe.domain.quizbook.service

import com.project.quizcafe.domain.quizbook.dto.request.CreateQuizBookRequest
import com.project.quizcafe.domain.quizbook.dto.request.UpdateQuizBookRequest
import com.project.quizcafe.domain.quizbook.dto.response.GetQuizBookAndQuizSummaryResponse
import com.project.quizcafe.domain.quizbook.dto.response.GetQuizBookResponse
import com.project.quizcafe.domain.quizbook.entity.QuizBook
import com.project.quizcafe.domain.user.entity.User
import java.util.*

interface QuizBookService {
    fun createQuizBook(request: CreateQuizBookRequest): QuizBook
    fun getQuizBook(id: Long): Optional<QuizBook>

    fun getQuizBookById(id: Long, user: User): GetQuizBookAndQuizSummaryResponse
    fun getQuizBooksByCategory(category: String, user: User): List<GetQuizBookResponse>
    fun getMyQuizBooks(user: User): List<GetQuizBookResponse>

    fun updateQuizBook(id: Long, request: UpdateQuizBookRequest, user: User)


    fun deleteQuizBook(id: Long, user: User)
}