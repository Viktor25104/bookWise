package backend.book.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private int year;

    @Column(columnDefinition = "TEXT")
    private String genre;

    @JsonProperty("is_bestseller")
    private boolean is_bestseller;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonProperty("image_url")
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    public Book() {
    }

    public Book(String title, String author, String isbn, int year, boolean is_bestseller, String description, String imageUrl) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.is_bestseller = is_bestseller;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
