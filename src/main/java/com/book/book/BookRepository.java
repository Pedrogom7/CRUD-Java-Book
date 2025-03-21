package com.book.book;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    //Optional<BookModel> findById(Long id); // Ensure this is here
}
