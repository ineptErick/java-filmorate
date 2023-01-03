package ru.yandex.practicum.filmorate.model;

import lombok.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
public class Film {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private Integer duration;

    public Film() {
        super();
    }
}