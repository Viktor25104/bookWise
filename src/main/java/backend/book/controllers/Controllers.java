package backend.book.controllers;

import backend.book.models.Book;
import backend.book.repo.BookRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class Controllers {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/")
    public String getAllBooks(Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Book> books = bookRepo.findAll(pageable);
        model.addAttribute("booksPage", books);
        model.addAttribute("genre", "all");
        return "gallery";
    }

    @GetMapping("/{genre}")
    public String getBooksByGenre(@PathVariable("genre") String genre, Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Book> books;
        if (genre.equals("all")) {
            books = bookRepo.findAll(pageable);
        } else {
            books = bookRepo.findByGenre(genre, pageable);
        }
        model.addAttribute("booksPage", books);
        model.addAttribute("genre", genre.equals("all") ? "all" : genre);
        return "gallery";
    }

    @GetMapping("/{genre}/{pageNumber}")
    public String getBooksPage(@PathVariable("pageNumber") int pageNumber,
                               @PathVariable("genre") String genre, Model model) {
        Pageable pageable = PageRequest.of(pageNumber, 20);
        Page<Book> booksPage;

        if (genre.equals("all")) {
            booksPage = bookRepo.findAll(pageable);
        } else {
            booksPage = bookRepo.findByGenre(genre, pageable);
        }

        model.addAttribute("booksPage", booksPage);

        return "gallery";
    }

    @GetMapping("/book/{id}")
    public String singleBook(@PathVariable("id") Long id, Model model) {
        Book book = bookRepo.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "gallery-single";
    }

}
