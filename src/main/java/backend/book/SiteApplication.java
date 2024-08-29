package backend.book;

import backend.book.dataBase.DataBaseFiller;
import backend.book.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SiteApplication {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private DataBaseFiller dataBaseFiller;

    @Value("${books.data.path}")
    private String booksDataPath;

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (bookRepo.count() == 0) {
                dataBaseFiller.addBooks(booksDataPath);
                System.out.println("Books data loaded");
            }
        };
    }

}
