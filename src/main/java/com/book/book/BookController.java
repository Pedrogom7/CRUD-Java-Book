package com.book.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController {
  
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookModel>> findAll() {
        List<BookModel> books = bookService.findAll();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookModel> create(@RequestBody BookModel bookModel) {
        if (bookModel == null || bookModel.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        BookModel savedBook = bookService.createBook(bookModel);
        if (savedBook == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();
        return ResponseEntity.created(uri).body(savedBook);
    }

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
