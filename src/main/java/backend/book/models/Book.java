package backend.book.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String author;

    @Column(columnDefinition = "TEXT")
    private String isbn;

    private float rating;

    @JsonProperty("year")
    private int publication_year;

    @Column(name = "is_bestseller")
    @JsonProperty("is_bestseller")
    private boolean isBestseller;

    @ElementCollection
    @Column(columnDefinition = "TEXT")
    private List<String> tags = new ArrayList<>();


    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonProperty("image_url")
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    public Book() {
    }

    public Book(String title, String author, String isbn, int publication_year, boolean isBestseller, String description, String imageUrl) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publication_year = publication_year;
        this.isBestseller = isBestseller;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
