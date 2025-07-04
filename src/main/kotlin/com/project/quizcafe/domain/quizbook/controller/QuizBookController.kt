package com.project.quizcafe.domain.quizbook.controller

import com.project.quizcafe.domain.auth.security.UserDetailsImpl
import com.project.quizcafe.common.response.ApiResponse
import com.project.quizcafe.common.response.ApiResponseFactory
import com.project.quizcafe.domain.quizbook.dto.request.CreateQuizBookRequest
import com.project.quizcafe.domain.quizbook.dto.request.UpdateQuizBookRequest
import com.project.quizcafe.domain.quizbook.dto.response.GetAllCategoriesResponse
import com.project.quizcafe.domain.quizbook.dto.response.GetQuizBookAllInfoResponse
import com.project.quizcafe.domain.quizbook.dto.response.GetQuizBookAndQuizSummaryResponse
import com.project.quizcafe.domain.quizbook.dto.response.GetQuizBookResponse
import com.project.quizcafe.domain.quizbook.entity.QuizCategory
import com.project.quizcafe.domain.quizbook.extensions.toGetAllCategoriesResponse
import com.project.quizcafe.domain.quizbook.service.QuizBookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quiz-book")
@Tag(name = "03.QuizBook", description = "문제집 관련 API")
class QuizBookController(
    private val quizBookService: QuizBookService
) {
    @PostMapping
    @Operation(summary = "퀴즈북 만들기", description = "사용자가 퀴즈북 생성")
    fun createQuizBook(
        @RequestBody request: CreateQuizBookRequest,
        @AuthenticationPrincipal principal: UserDetailsImpl
    ): ResponseEntity<ApiResponse<Long?>> {
        val createdQuizBook = quizBookService.createQuizBook(request, principal.getUser())
        return ApiResponseFactory.success(
            data = createdQuizBook.id,
            message = "문제집 생성 성공",
            status = HttpStatus.CREATED
        )
    }

    @GetMapping
    @Operation(summary = "카테고리로 퀴즈북 조회", description = "카테고리에 해당하는 퀴즈북 조회")
    fun getQuizBooksByCategoryOfMine(
        @RequestParam category: String,
        @AuthenticationPrincipal principal: UserDetailsImpl
    ): ResponseEntity<ApiResponse<List<GetQuizBookResponse>?>> {
        val result = quizBookService.getQuizBooksByCategory(category, principal.getUser())
        return ApiResponseFactory.success(
            data = result,
            message = "문제집 조회 성공",
            status = HttpStatus.OK
        )
    }

    @GetMapping("/{quizBookId}")
    @Operation(summary = "퀴즈북 id로 퀴즈북 정보 조회", description = "퀴즈북 id에 해당하는 퀴즈북 조회")
    fun getQuizBookById(
        @AuthenticationPrincipal principal: UserDetailsImpl,
        @PathVariable quizBookId: Long,
    ): ResponseEntity<ApiResponse<GetQuizBookAndQuizSummaryResponse?>> {
        val result = quizBookService.getQuizBookById(quizBookId, principal.getUser())
        return ApiResponseFactory.success(
            data = result,
            message = "문제집 조회 성공",
            status = HttpStatus.OK
        )
    }

    @GetMapping("/all/{quizBookId}")
    @Operation(summary = "퀴즈북 id로 퀴즈북의 모든 정보 조회", description = "퀴즈북 id에 해당하는 퀴즈북의 모든 정보 조회, 퀴즈의 모든 정보도 포함")
    fun getQuizBookAllInfoById(
        @AuthenticationPrincipal principal: UserDetailsImpl,
        @PathVariable quizBookId: Long,
    ): ResponseEntity<ApiResponse<GetQuizBookAllInfoResponse?>> {
        val result = quizBookService.getQuizBookAllInfoById(quizBookId, principal.getUser())
        return ApiResponseFactory.success(
            data = result,
            message = "문제집 조회 성공",
            status = HttpStatus.OK
        )
    }

    @PatchMapping("/{quizBookId}")
    @Operation(summary = "퀴즈북 업데이트", description = "퀴즈북 id에 해당하는 퀴즈북 업데이트")
    fun updateQuizBook(
        @PathVariable quizBookId: Long,
        @RequestBody request: UpdateQuizBookRequest,
        @AuthenticationPrincipal principal: UserDetailsImpl
    ): ResponseEntity<ApiResponse<Unit?>> {
        quizBookService.updateQuizBook(quizBookId, request, principal.getUser())
        return ApiResponseFactory.success(
            message = "문제집 수정 완료",
            status = HttpStatus.OK
        )
    }

    @DeleteMapping("/{quizBookId}")
    @Operation(summary = "퀴즈북 삭제", description = "퀴즈북 id에 해당하는 퀴즈북 삭제")
    fun deleteQuizBook(
        @PathVariable quizBookId: Long,
        @AuthenticationPrincipal principal: UserDetailsImpl
    ): ResponseEntity<ApiResponse<Unit?>> {
        quizBookService.deleteQuizBook(quizBookId, principal.getUser())
        return ApiResponseFactory.success(
            message = "문제집 삭제 성공",
            status = HttpStatus.NO_CONTENT
        )
    }

    @GetMapping("/category")
    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리 조회")
    fun getAllCategories(): ResponseEntity<ApiResponse<List<GetAllCategoriesResponse>?>> {
        val categories = QuizCategory.entries.map { it.toGetAllCategoriesResponse() }
        return ApiResponseFactory.success(
            data = categories,
            message = "카테고리 조회 성공",
            status = HttpStatus.OK
        )
    }

    @GetMapping("/latest")
    @Operation(summary = "최신 퀴즈북 조회", description = "최신 퀴즈북을 생성일 기준으로 limit 개수만큼 반환 (기본 10개)")
    fun getLatestQuizBooks(
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<ApiResponse<List<GetQuizBookResponse>?>> {
        val quizBooks = quizBookService.getLatestQuizBooks(limit)
        return ApiResponseFactory.success(
            data = quizBooks,
            message = "최신 문제집 조회 성공",
            status = HttpStatus.OK
        )
    }

}