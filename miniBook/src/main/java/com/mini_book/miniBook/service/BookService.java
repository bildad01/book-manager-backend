package com.mini_book.miniBook.service;

import com.mini_book.miniBook.domain.Book;

import java.util.List;

public interface BookService {
    //도서등록
    Book insertBook(Book book);

    //도서상세조회
    Book findBook(Long id);

    //도서수정
    Book updateBook(Long id, Book book);

    //도서삭제
    void deleteBook(Long id);

    //도서전체목록조회
    List<Book> findAllBook();

}
