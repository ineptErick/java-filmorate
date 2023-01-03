package ru.yandex.practicum.filmorate.model;

import lombok.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
public class User {


    private Integer id;

    @NonNull
    private String email;

    @NonNull
    private String login;

    private String name;

    @NonNull
    private LocalDate birthday;

    public User() {
        super();
    }

}