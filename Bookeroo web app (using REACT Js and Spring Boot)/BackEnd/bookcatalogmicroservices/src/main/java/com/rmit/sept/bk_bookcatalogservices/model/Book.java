package com.rmit.sept.bk_bookcatalogservices.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    private String category;
    private String description;
    @Column(unique = true)
    @NotNull
    private Long isbn;

    private String image;

    public Book() {
    }

    public Long getId() { return this.id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return this.category; }

    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public Long getIsbn() { return isbn; }

    public void setIsbn(Long isbn) { this.isbn = isbn; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
