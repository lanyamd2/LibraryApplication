package com.lmd.libraryapplication.model.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity
@Table(name = "authors")
public class AuthorDTO extends RepresentationModel<AuthorDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
    private Integer id;

    @Size(max = 40)
    @Column(name = "full_name", length = 40)
    private String fullName;

    @OneToMany(mappedBy = "author",
    fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookDTO> books;

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", books=" + books +
                '}';
    }
}