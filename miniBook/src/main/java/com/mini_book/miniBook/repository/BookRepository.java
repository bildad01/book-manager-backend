package com.mini_book.miniBook.repository;


import com.mini_book.miniBook.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}



public interface BookRepository {
}

