package com.mini_book.miniBook.service;

import com.mini_book.miniBook.domain.Book;
import com.mini_book.miniBook.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mini_book.miniBook.exception.ResourceNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override //도서등록
    public Book insertBook(Book book) {
        return bookRepository.save(book);
    }

    @Override //도서상세조회
    public Book findBook(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("책을 찾을 수 없습니다")
        );
    }

    @Override //도서수정
    public Book updateBook(Long id, Book book) {
        Book b = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("책을 찾을 수 없습니다")
        );
        b.setTitle(book.getTitle());
        b.setContent(book.getContent());
        b.setCreatedAt(book.getCreatedAt());
        b.setUpdatedAt(book.getUpdatedAt());
        b.setCoverImageUrl(book.getCoverImageUrl());
        return bookRepository.save(b);
    }

    @Override //도서삭제
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override //도서전체목록조회
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }
}
