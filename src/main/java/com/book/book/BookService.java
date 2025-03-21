package com.book.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    // Retrieve all books
    public List<BookModel> findAll() {
        return bookRepository.findAll();
    }

    // Find a book by ID
    public Optional<BookModel> findById(Long id) {
        return bookRepository.findById(id);
    }

    // Create a new book
    public BookModel createBook(BookModel bookModel) {
        if (bookModel == null || bookModel.getId() != null) {
            throw new IllegalArgumentException("Invalid book data");
        }
        return bookRepository.save(bookModel);
    }

    // Update an existing book
    public Optional<BookModel> update(Long id, BookModel bookModel) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    bookModel.setId(id);
                    return bookRepository.save(bookModel);
                });
    }

    // Delete a book by ID
    public boolean delete(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}
