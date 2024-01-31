package com.abelg.BibliotecApp.service;

import com.abelg.BibliotecApp.entity.Book;
import com.abelg.BibliotecApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> getBooks(){
        return bookRepository.findAll();

    }

    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }

    public Book saveOrUpdate(Book book){
            bookRepository.save(book);
            return book;
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public List<Book> getBookByCategoria(String categoria){
        return bookRepository.findByCategoria(categoria);
    }
}
