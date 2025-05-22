package com.bsn.api.user;

import com.bsn.api.auth.RegisterRequest;
import com.bsn.api.book.Book;
import com.bsn.api.book_transaction.BookTransaction;
import com.bsn.api.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean accountLocked;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "borrower")
    private List<BookTransaction> bookTransactions = new ArrayList<>();

    public User(RegisterRequest registerRequest) {
        this.firstname = registerRequest.getFirstname();
        this.lastname = registerRequest.getLastname();
        this.email = registerRequest.getEmail();
        this.password = registerRequest.getPassword();
        this.birthDate = registerRequest.getBirthDate();
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
