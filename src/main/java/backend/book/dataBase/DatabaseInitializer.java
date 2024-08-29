package backend.book.dataBase;

import backend.book.repo.BookRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private DataBaseFiller dataBaseFiller;

    @Value("${books.data.path}")
    private String booksDataPath;

    @PostConstruct
    public void init() {
        if (bookRepo.count() == 0) {
            dataBaseFiller.addBooks(booksDataPath);
        }
    }

}
