package com.bsn.api.book;

import com.bsn.api.file.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponse {

    private Long id;

    private String title;

    private String authorName;

    private String isbn;

    private String synopsis;

    private String owner;

    private byte[] cover;

    private String coverPath;

    private double rate;

    private boolean archived;

    private boolean shareable;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorName = book.getAuthorName();
        this.isbn = book.getIsbn();
        this.synopsis = book.getSynopsis();
        this.owner = book.getOwner().getFullName();
        this.cover = FileUtils.readFileFromLocation(book.getBookCoverPath());
        this.coverPath = book.getBookCoverPath();
        this.rate = book.getRate();
        this.archived = book.isArchived();
        this.shareable = book.isShareable();
    }
}
