package com.example.client1.entity;

import lombok.Data;

import java.util.List;

@Data
public class AuthorEntity {
    private Long id;
    private String name;
    private String lastname;
    private String surname;
    private List<BookEntity> book;

    @Override
    public String toString() {
        return surname + ' ' + name.toUpperCase().charAt(0) + ". " + lastname.toUpperCase().charAt(0) + ". ";
    }
}
