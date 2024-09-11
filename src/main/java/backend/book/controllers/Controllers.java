package backend.book.controllers;

import backend.book.dataBase.DataBaseFiller;
import backend.book.models.Book;
import backend.book.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;

@Controller
public class Controllers {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private DataBaseFiller dataBaseFiller;

    @Value("${books.data.path}")
    private String booksDataPath;

    private final Comparator<Book> bookComparator = Comparator.comparing(Book::isBestseller)
            .reversed()
            .thenComparing(Comparator.comparing(Book::getRating).reversed())
            .thenComparing(Book::getTitle);

    @GetMapping("/")
    public String getAllBooks(Model model) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("isBestseller").descending().and(Sort.by("rating").descending()).and(Sort.by("title")));
        Page<Book> books = bookRepo.findAll(pageable);
        model.addAttribute("booksPage", books);
        model.addAttribute("tag", "all");
        return "gallery";
    }

    @GetMapping("/{tag}")
    public String getBooksByTag(@PathVariable("tag") String tag, Model model) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("isBestseller").descending().and(Sort.by("rating").descending()).and(Sort.by("title")));
        Page<Book> books;
        if (tag.equals("all")) {
            books = bookRepo.findAll(pageable);
        } else {
            books = bookRepo.findByTag(tag, pageable);
        }
        model.addAttribute("booksPage", books);
        model.addAttribute("tag", tag.equals("all") ? "all" : tag);
        return "gallery";
    }

    @GetMapping("/{tag}/{pageNumber}")
    public String getBooksPage(@PathVariable("pageNumber") int pageNumber,
                               @PathVariable("tag") String tag, Model model) {
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("isBestseller").descending().and(Sort.by("rating").descending()).and(Sort.by("title")));
        Page<Book> booksPage;

        if (tag.equals("all")) {
            booksPage = bookRepo.findAll(pageable);
        } else {
            booksPage = bookRepo.findByTag(tag, pageable);
        }

        model.addAttribute("booksPage", booksPage);

        return "gallery";
    }

    @GetMapping("/book/{id}")
    public String singleBook(@PathVariable("id") Long id, Model model) {
        Book book = bookRepo.findById(id).orElse(null);
        model.addAttribute("book", book);
        assert book != null;
        model.addAttribute("tags", String.join(", ", book.getTags()));
        return "gallery-single";
    }
}