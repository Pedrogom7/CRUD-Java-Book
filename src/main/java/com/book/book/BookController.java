package com.book.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
  
  @Autowired
  private BookService bookService;

  @GetMapping
  private ResponseEntity<List<BookModel>> findAll() {
    return ResponseEntity.ok(bookService.findAll());
  }

  @PostMapping
  private ResponseEntity<BookModel> create(@RequestBody BookModel bookModel) {
    BookModel bookModel1 = bookService.createBook(bookModel);
    return ResponseEntity.ok().body(bookModel1);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<BookModel> delete(@PathVariable Long id) {
    bookService.delete(id);
    return ResponseEntity.noContent().build();
  }
}