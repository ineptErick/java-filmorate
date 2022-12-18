package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
public class FilmService {
    private final FilmController filmController;

    public FilmService(@Qualifier("userDbStorage") FilmController filmController, FilmService filmService) {
        this.filmController = filmController;
        this.filmService = filmService;
    }
    private final FilmService filmService;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895, 12, 28);

    private boolean checkValidData(Film film) {
        if (film.getDescription().length() > 200) {
            log.error("Описание больше 200 символов.", FilmService.class);
            throw new ValidationException("Описание не должно составлять больше 200 символов.");
        }
        if (film.getDescription().isBlank() || film.getDescription().isEmpty()) {
            log.error("Описание пустое или состоит из пробелов.");
            throw new ValidationException("Описание не должно быть пустым или состоять из " +
                    "пробелов.");
        }
        if (film.getReleaseDate().isBefore(MOVIE_BIRTHDAY)) {
            log.error("Дата выхода фильма в прокат не может быть раньше 28.12.1895.",
                    FilmService.class);
            throw new ValidationException("Дата выхода фильма в прокат не может быть раньше " +
                    "28.12.1895.");
        }
        if (film.getDuration() <= 0) {
            log.error("Продолжительность фильма отрицательное число или равно нулю.",
                    FilmService.class);
            throw new ValidationException("Продолжительность фильма должна быть больше нуля.");
        }
        return true;
    }

    private boolean checkAddValidData(Film film) {
        if (checkValidData(film)) {
            if (film.getName().isBlank()) {
                log.error("Название пустое.", FilmService.class);
                throw new ValidationException("Название не может быть пустым.");
            }
        }
        return true;
    }

    private boolean checkUpdateValidData(Film film) {
        if (checkValidData(film)) {
            if (film.getId() == null) {
                log.error("Не введен id.", FilmService.class);
                throw new ValidationException("Нужно задать id.");
            }
            if (!getAllFilms().containsKey(film.getId())) {
                log.error("Неверный id.", FilmService.class);
                throw new ElementNotFoundException("фильм с id" + film.getId());
            }
        }
        return true;
    }

    public Map<Integer, Film> getAllFilms() {
        return (Map<Integer, Film>) filmController.getAllFilms();
    }

    public Film addFilm(Film film) {
        if (checkAddValidData(film)) {
            final Film createdFilm = filmController.addFilm(film);
        }
        return film;
    }

    public Film updateFilm(Film film) {
        if (checkUpdateValidData(film) && checkAddValidData(film)) {
            final Film updatedFilm = filmController.updateFilm(film);
        }
        return film;
    }

}