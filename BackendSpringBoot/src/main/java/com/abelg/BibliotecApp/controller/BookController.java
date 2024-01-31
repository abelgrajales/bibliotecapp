package com.abelg.BibliotecApp.controller;

import com.abelg.BibliotecApp.entity.Book;
import com.abelg.BibliotecApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {

    @Autowired
    private  BookService bookService;

    @GetMapping
    public List<Book> getAll(){
        return bookService.getBooks();
    }

    @GetMapping("/{bookId}")
    public Optional<Book> getById(@PathVariable("bookId") Long bookId){
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public Book saveUpdate(@RequestBody Book book){
        return bookService.saveOrUpdate(book);
    }

    @DeleteMapping("/delete/{bookId}")
    public void deleteById(@PathVariable("bookId") Long bookId){
        bookService.delete(bookId);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Book> getByCategoria(@PathVariable("categoria") String categoria){
        return bookService.getBookByCategoria(categoria);
    }

}
