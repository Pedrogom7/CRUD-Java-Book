package com.book.book;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/books")
public class BookController {
  
    @Autowired
    private BookService bookService;

    // List all books
    @GetMapping
    public ResponseEntity<List<BookModel>> findAll() {
        List<BookModel> books = bookService.findAll();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    // Create a new book
    @PostMapping
    public ResponseEntity<BookModel> create(@RequestBody BookModel bookModel) {
        if (bookModel == null || bookModel.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        BookModel savedBook = bookService.createBook(bookModel);
        if (savedBook == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();
        return ResponseEntity.created(uri).body(savedBook);
    }

    // Find book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> findById(@PathVariable Long id) {
        Optional<BookModel> book = bookService.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

// Update book by ID
@PutMapping("/{id}")
public ResponseEntity<BookModel> update(@PathVariable Long id, @RequestBody BookModel bookModel) {
    if (bookModel == null || id == null) {
        return ResponseEntity.badRequest().build();
    }
    Optional<BookModel> existingBook = bookService.findById(id);
    if (existingBook.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    bookModel.setId(id);
    BookModel updatedBook = bookService.update(id, bookModel).orElse(null);  // Call the correct method
    if (updatedBook == null) {
        return ResponseEntity.unprocessableEntity().build(); // Handling potential failure case
    }
    return ResponseEntity.ok(updatedBook);
}

    // Delete book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<BookModel> book = bookService.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
