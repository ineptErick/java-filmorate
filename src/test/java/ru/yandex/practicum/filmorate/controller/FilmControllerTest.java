package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.UserService;


import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController controller;
    private Film film;
    private Film film1;
    private Film film2;
    private Film film3;
    private Film film4;
    private Film film5;
    private Film film6;
    private Film film7;
    private Film film8;
    private Film film9;
    private Film film10;
    private UserService userService;

    @BeforeEach
    void start() {
        createFilmsForTests();
    }

    private void createFilmsForTests() {
        film = new Film(1, "The Film", "Someone and sometwo",
                LocalDate.of(1996, 6, 7), 136);
        film1 = new Film(1, "", "Someone and sometwo2",
                LocalDate.of(1996, 6, 7), 136);
        film2 = new Film(1, "The Film2", "Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah Blah blah blah blah blah blah ",
                LocalDate.of(1996, 6, 7), 136);
        film3 = new Film(1, "The Film3", "Someone and sometwo3",
                LocalDate.of(1895, 12, 27), 131);
        film4 = new Film(1, "The Film4", "Someone and sometwo4",
                LocalDate.of(1994, 6, 7), 0);
        film5 = new Film(1, "The Film5", "Someone and sometwo5",
                LocalDate.of(1996, 5, 7), -8);
        film6 = new Film(1, "The Film6", "Someone and sometwo6",
                LocalDate.of(1895, 12, 28), 132);
        film7 = new Film(1, "The Film7", "Someone and sometwo7",
                LocalDate.of(1895, 12, 29), 130);
        film8 = new Film(1, "The Film12", "Someone and sometwo and somethree" +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah ",
                LocalDate.of(1994, 6, 7), 134);
        film9 = new Film(1, "The Film3", "Someone and sometwo and who else" +
                "second-in-command Major Tom Baxter lead a rogue group of U.S. Force Recon Marines against a heavily " +
                "guarded naval weapons depot to steal a stock",
                LocalDate.of(1993, 6, 7), 137);
        film10 = new Film(1, "The Film1", "Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah " +
                "Blah blah blah blah blah blah Blah blah blah blah blah blah ",
                LocalDate.of(1995, 6, 7), 135);
    }

    @Test
    void shouldReturnAllFilms() {
        assertEquals(0, controller.getAllFilms().size(), "Хранилище должно быть пустым.");
        controller.addFilm(film);
        Collection<Film> films = controller.getAllFilms();
        assertEquals(1, films.size(), "Хранилище не должно быть пустым.");
        assertTrue(films.contains(film), "Фильм не добавлен.");
    }

    @Test
    void shouldAddFilmWhenDataIsValid() {
        controller.addFilm(film);
        assertTrue(controller.getAllFilms().contains(film), "Фильм не добавлен в хранилище.");
    }

    @Test
    void shouldNotAddFilmWhenNameIsEmpty() {
        assertThrows(ValidationException.class, () -> controller.addFilm(film1), "Название фильма не пустое.");
        assertFalse(controller.getAllFilms().contains(film1), "Фильм добавлен в хранилище.");
    }

    @Test
    void shouldNotAddFilmWhenDescriptionIsOver200Symbols() {
        assertThrows(ValidationException.class, () -> controller.addFilm(film2), "Описание меньше 200.");
        assertFalse(controller.getAllFilms().contains(film2), "Фильм добавлен в хранилище.");
    }

    @Test
    void shouldAddFilmWhenDescriptionIsEquals200Symbols() {
        controller.addFilm(film8);
        assertTrue(controller.getAllFilms().contains(film8), "Фильм не добавлен в хранилище.");
    }

    @Test
    void shouldAddFilmWhenDescriptionIsLess200Symbols() {
        controller.addFilm(film9);
        assertTrue(controller.getAllFilms().contains(film9), "Фильм не добавлен в хранилище.");
    }

    @Test
    void shouldAddFilmWhenReleaseDateIsEqualsBirthdayOfMovie() {
        controller.addFilm(film6);
        assertTrue(controller.getAllFilms().contains(film6), "Фильм не добавлен в хранилище.");
    }

    @Test
    void shouldAddFilmWhenReleaseDateIsAfterBirthdayOfMovie() {
        controller.addFilm(film7);
        assertTrue(controller.getAllFilms().contains(film7), "Фильм не добавлен в хранилище.");
    }

    @Test
    void shouldNotAddFilmWhenReleaseDateIsBeforeBirthdayOfMovie() {
        assertThrows(ValidationException.class, () -> controller.addFilm(film3),
                "Дата выхода фильма позже 28.12.1895");
        assertFalse(controller.getAllFilms().contains(film3), "Фильм добавлен в хранилище.");
    }

    @Test
    void shouldNotAddFilmWhenDurationIsEqualsZero() {
        assertThrows(ValidationException.class, () -> controller.addFilm(film4),
                "Продолжительность фильма не равна 0.");
        assertFalse(controller.getAllFilms().contains(film4), "Фильм добавлен в хранилище.");
    }

    @Test
    void shouldNotAddFilmWhenDurationIsNegative() {
        assertThrows(ValidationException.class, () -> controller.addFilm(film5),
                "Продолжительность фильма положительная.");
        assertFalse(controller.getAllFilms().contains(film5), "Фильм добавлен в хранилище.");
    }

    @Test
    void shouldUpdateFilmWhenDataIsValid() {
        controller.addFilm(film);
        assertEquals(1, controller.getAllFilms().size(), "Хранилище не должно быть пустым.");
        assertTrue(controller.getAllFilms().contains(film), "Фильм не добавлен в хранилище.");
        controller.updateFilm(film10);
        assertEquals(1, controller.getAllFilms().size(), "Хранилище не должно быть пустым.");
        assertEquals(film.getName(), film10.getName(), "Названия фильмов не совпадают.");
        assertEquals(film.getDescription(), film10.getDescription(), "Описания фильмов не совпадают.");
        assertEquals(film.getReleaseDate(), film10.getReleaseDate(), "Даты выхода фильмов не совпадают.");
        assertEquals(film.getDuration(), film10.getDuration(), "Продолжительности фильмов не совпадают.");
    }

    @Test
    void shouldNotUpdateFilmWhenNameIsEmpty() {
        controller.addFilm(film);
        assertThrows(ValidationException.class, () -> controller.updateFilm(film1),
                "Название фильма не пустое.");
    }

    @Test
    void shouldNotUpdateFilmWhenDescriptionIsOver200Symbols() {
        controller.addFilm(film);
        assertThrows(ValidationException.class, () -> controller.updateFilm(film2), "Описание меньше 200 символов.");
    }

    @Test
    void shouldUpdateFilmWhenDescriptionIsEquals200Symbols() {
        controller.addFilm(film);
        assertNotEquals(film.getName(), film8.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film8.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film8.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film8.getDuration(), "Продолжительности фильмов совпадают.");
        controller.updateFilm(film8);
        assertEquals(film.getName(), film8.getName(), "Названия фильмов не совпадают.");
        assertEquals(film.getDescription(), film8.getDescription(), "Описания фильмов не совпадают.");
        assertEquals(film.getReleaseDate(), film8.getReleaseDate(), "Даты выхода фильмов не совпадают.");
        assertEquals(film.getDuration(), film8.getDuration(), "Продолжительности фильмов не совпадают.");
    }

    @Test
    void shouldUpdateFilmWhenDescriptionIsLess200Symbols() {
        controller.addFilm(film);
        assertNotEquals(film.getName(), film9.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film9.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film9.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film9.getDuration(), "Продолжительности фильмов совпадают.");
        controller.updateFilm(film9);
        assertEquals(film.getName(), film9.getName(), "Названия фильмов не совпадают.");
        assertEquals(film.getDescription(), film9.getDescription(), "Описания фильмов не совпадают.");
        assertEquals(film.getReleaseDate(), film9.getReleaseDate(), "Даты выхода фильмов не совпадают.");
        assertEquals(film.getDuration(), film9.getDuration(), "Продолжительности фильмов не совпадают.");
    }

    @Test
    void shouldUpdateFilmWhenReleaseDateIsEqualsBirthdayOfMovie() {
        controller.addFilm(film);
        assertNotEquals(film.getName(), film6.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film6.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film6.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film6.getDuration(), "Продолжительности фильмов совпадают.");
        controller.updateFilm(film6);
        assertEquals(film.getName(), film6.getName(), "Названия фильмов не совпадают.");
        assertEquals(film.getDescription(), film6.getDescription(), "Описания фильмов не совпадают.");
        assertEquals(film.getReleaseDate(), film6.getReleaseDate(), "Даты выхода фильмов не совпадают.");
        assertEquals(film.getDuration(), film6.getDuration(), "Продолжительности фильмов не совпадают.");
    }

    @Test
    void shouldUpdateFilmWhenReleaseDateIsAfterBirthdayOfMovie() {
        controller.addFilm(film);
        assertNotEquals(film.getName(), film7.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film7.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film7.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film7.getDuration(), "Продолжительности фильмов совпадают.");
        controller.updateFilm(film7);
        assertEquals(film.getName(), film7.getName(), "Названия фильмов не совпадают.");
        assertEquals(film.getDescription(), film7.getDescription(), "Описания фильмов не совпадают.");
        assertEquals(film.getReleaseDate(), film7.getReleaseDate(), "Даты выхода фильмов не совпадают.");
        assertEquals(film.getDuration(), film7.getDuration(), "Продолжительности фильмов не совпадают.");
    }

    @Test
    void shouldNotUpdateFilmWhenReleaseDateIsBeforeBirthdayOfMovie() {
        controller.addFilm(film);
        assertThrows(ValidationException.class, () -> controller.updateFilm(film3),
                "Дата выхода фильма позже 28.12.1895");
        assertNotEquals(film.getName(), film3.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film3.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film3.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film3.getDuration(), "Продолжительности фильмов совпадают.");
    }

    @Test
    void shouldNotUpdateFilmWhenDurationIsEqualsZero() {
        controller.addFilm(film);
        assertThrows(ValidationException.class, () -> controller.updateFilm(film4),
                "Продолжительность фильма не равна 0.");
        assertNotEquals(film.getName(), film4.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film4.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film4.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film4.getDuration(), "Продолжительности фильмов совпадают.");
    }

    @Test
    void shouldNotUpdateFilmWhenDurationIsNegative() {
        controller.addFilm(film);
        assertThrows(ValidationException.class, () -> controller.updateFilm(film5),
                "Продолжительность фильма положительная.");
        assertNotEquals(film.getName(), film5.getName(), "Названия фильмов совпадают.");
        assertNotEquals(film.getDescription(), film5.getDescription(), "Описания фильмов совпадают.");
        assertNotEquals(film.getReleaseDate(), film5.getReleaseDate(), "Даты выхода фильмов совпадают.");
        assertNotEquals(film.getDuration(), film5.getDuration(), "Продолжительности фильмов совпадают.");
    }
}