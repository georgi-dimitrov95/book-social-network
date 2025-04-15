package com.bsn.api.feedback;

import com.bsn.api.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
