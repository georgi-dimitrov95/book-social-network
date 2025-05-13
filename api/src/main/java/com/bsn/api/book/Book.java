package com.bsn.api.book;

import com.bsn.api.book_transaction.BookTransaction;
import com.bsn.api.feedback.Feedback;
import com.bsn.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String authorName;

    private String isbn;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    private String bookCoverPath;

    private boolean archived = false;

    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookTransaction> bookTransactions = new ArrayList<>();

    public Book(BookRequest bookRequest) {
        this.id = bookRequest.id();
        this.title = bookRequest.title();
        this.authorName = bookRequest.authorName();
        this.isbn = bookRequest.isbn();
        this.synopsis = bookRequest.synopsis();
        this.shareable = bookRequest.shareable();
    }

//    check later - Feeback should have non-null rating field (otherwise this should work fine)
    public double getRate() {
        return feedbacks.stream()
                .mapToDouble(Feedback::getRating)
                .average()
                .orElse(0.0);
    }
}
