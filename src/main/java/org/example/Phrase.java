package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "phrase")
public class Phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phrase", nullable = false, length = 2000)
    private String phrase;

    @Column(name = "author" , nullable = false, length = 2000)
    private String author;

    public Phrase(String phrase, String author) {
        this.phrase = phrase;
        this.author = author;
    }

    public Phrase() {
    }
}
