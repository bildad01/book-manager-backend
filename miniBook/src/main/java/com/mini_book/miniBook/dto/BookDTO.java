package com.mini_book.miniBook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

public class BookDTO {

    // [POST] 도서 등록 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        private String coverImageUrl;
    }

    // 🛠 [PUT] 도서 수정 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Put {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        @NotBlank(message = "저자 이름은 필수 입력 값입니다")
        private String author;
    }

    // [GET] 단일 도서 조회 응답
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String coverImageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String author;
    }

    // [GET] 도서 목록 요약 응답
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Summary {
        private Long id;
        private String title;
        private String coverImageUrl;
        private LocalDateTime createdAt;
        private String author;
    }

    // [POST] AI 표지 생성 응답
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CoverResponse {
        private String coverImageUrl;
    }
}
