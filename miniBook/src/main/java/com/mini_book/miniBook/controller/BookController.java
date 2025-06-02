package com.mini_book.miniBook.controller;

import com.mini_book.miniBook.domain.Book;
import com.mini_book.miniBook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody Book book){
        book.setCreatedAt(LocalDateTime.now());
        return bookService.insertBook(book);
    }

    @GetMapping("/{id}")
    public Book findBook(@PathVariable Long id){
        return bookService.findBook(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id , @RequestBody Book book){
        book.setUpdatedAt(LocalDateTime.now());
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "도서 삭제 성공!";
    }

    @GetMapping
    public List<Book> findAllBook(){
        return bookService.findAllBook();
    }

}
