package backend.book.dataBase;

import backend.book.models.Book;
import backend.book.repo.BookRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class DataBaseFiller {

    @Autowired
    private BookRepo bookRepo;

    public void addBooks(String path) {
        File directory = new File(path);

        File[] jsonFiles = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (jsonFiles != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            for (File file : jsonFiles) {
                try {
                    List<Book> books = objectMapper.readValue(file, new TypeReference<List<Book>>() {});

                    String genre = file.getName().replaceFirst("[.][^.]+$", "");

                    for (Book book : books) {
                        book.setGenre(genre);
                    }

                    bookRepo.saveAll(books);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
