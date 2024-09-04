package backend.book.repo;

import backend.book.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN b.tags t WHERE t = :tag")
    Page<Book> findByTag(@Param("tag") String tag, Pageable pageable);
}
