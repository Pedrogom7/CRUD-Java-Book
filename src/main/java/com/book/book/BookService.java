package com.book.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;
  
  public List<BookModel> findAll() {
    return bookRepository.findAll();
  }

  public BookModel findById(Long id) {
    return bookRepository.findById(id).orElse(null);
  }

  public BookModel createBook(BookModel bookModel) {
    return bookRepository.save(bookModel);
  }

  public void delete(Long id) {
    bookRepository.deleteById(id);
  }

  public BookModel update(BookModel bookModel) {
    return bookRepository.save(bookModel);
  }
}
