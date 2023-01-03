package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController controller;
    private User user;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;

    @BeforeEach
    public void start() {
        createUsersForTests();
    }

    private void createUsersForTests() {
        user = new User(1, "login@mail.ru", "login", "Name",
                LocalDate.of(1990, 10, 6));
        user1 = new User(1, "", "login1", "Name1",
                LocalDate.of(1991, 10, 6));
        user2 = new User(1, "login.ru", "login2", "Name2",
                LocalDate.of(1992, 10, 6));
        user3 = new User(1, "login@t.ru", "", "Name3",
                LocalDate.of(1993, 10, 6));
        user4 = new User(1, "login@t.ru", "login and login two", "Name4",
                LocalDate.of(1994, 10, 6));
        user5 = new User(1, "login@t.ru5", "login5", "",
                LocalDate.of(1995, 10, 6));
        user6 = new User(1, "login@t.ru6", "login6", "Name6",
                LocalDate.now().minusDays(1));
        user7 = new User(1, "login@t.ru7", "login7", "Name7",
                LocalDate.now());
        user8 = new User(1, "login@t.ru8", "login8", "Name8",
                LocalDate.now().plusDays(1));
    }

    @Test
    void shouldReturnAllUsers() {
        assertEquals(0, controller.getAllUsers().size(), "Должно быть пустым.");
        controller.addUser(user);
        Collection<User> users = controller.getAllUsers();
        assertEquals(1, users.size(), "Хранилище не должно быть пустым.");
        assertTrue(users.contains(user), "Пользователь не добавлен.");
    }

    @Test
    void shouldAddUserWhenDataIsValid() {
        controller.addUser(user);
        assertTrue(controller.getAllUsers().contains(user), "Пользователь не добавлен.");
    }

    @Test
    void shouldNotAddUserWhenEmailIsEmpty() {
        assertThrows(ValidationException.class, () -> controller.addUser(user1), "Email не пустой.");
        assertFalse(controller.getAllUsers().contains(user1), "Фильм добавлен.");
    }

    @Test
    void shouldNotAddUserWhenEmailIsNotContainsSymbol() {
        assertThrows(ValidationException.class, () -> controller.addUser(user2), "Email" +
                " содержит символ @.");
        assertFalse(controller.getAllUsers().contains(user2), "Пользователь добавлен .");
    }

    @Test
    void shouldNotAddUserWhenLoginIsEmpty() {
        assertThrows(ValidationException.class, () -> controller.addUser(user3), "Логин" +
                " не пустой.");
        assertFalse(controller.getAllUsers().contains(user3), "Пользователь добавлен.");
    }

    @Test
    void shouldNotAddUserWhenLoginContainsSpaces() {
        assertThrows(ValidationException.class, () -> controller.addUser(user4), "Логин" +
                " не содержит пробелы.");
        assertFalse(controller.getAllUsers().contains(user4), "Пользователь добавлен.");
    }

    @Test
    void shouldAddUserWhenNameIsEmpty() {
        controller.addUser(user5);
        assertTrue(controller.getAllUsers().contains(user5), "Пользователь не добавлен.");
        assertEquals(user5.getLogin(), user5.getName(), "Логин и имя различны.");
    }

    @Test
    void shouldAddUserWhenBirthdayIsBeforeNow() {
        controller.addUser(user6);
        assertTrue(controller.getAllUsers().contains(user6), "Пользователь добавлен." +
                " в хранилище.");
    }

    @Test
    void shouldAddUserWhenBirthdayIsEqualsNow() {
        controller.addUser(user7);
        assertTrue(controller.getAllUsers().contains(user7), "Пользователь не добавлен.");
    }

    @Test
    void shouldNotAddUserWhenBirthdayIsAfterNow() {
        assertThrows(ValidationException.class, () -> controller.addUser(user8), "Дата" +
                " рождения не в будущем.");
        assertFalse(controller.getAllUsers().contains(user8), "Пользователь добавлен.");
    }

    @Test
    void shouldUpdateUserWhenDataIsValid() {
        controller.addUser(user);
        assertEquals(1, controller.getAllUsers().size(), "Хранилище не должно" +
                " быть пустым.");
        assertTrue(controller.getAllUsers().contains(user), "Фильм не добавлен в хранилище.");
        controller.updateUser(user6);
        assertEquals(1, controller.getAllUsers().size(), "Хранилище не должно быть пустым.");
        assertEquals(user.getEmail(), user6.getEmail(), "Адреса email не совпадают.");
        assertEquals(user.getLogin(), user6.getLogin(), "Логины не совпадают.");
        assertEquals(user.getName(), user6.getName(), "Имена не совпадают.");
        assertEquals(user.getBirthday(), user6.getBirthday(), "Даты рождения не совпадают.");
    }

    @Test
    void shouldNotUpdateUserWhenEmailIsEmpty() {
        controller.addUser(user);
        assertThrows(ValidationException.class, () -> controller.updateUser(user1), "Email не пустой.");
        assertNotEquals(user.getEmail(), user1.getEmail(), "Адреса email совпадают.");
        assertNotEquals(user.getLogin(), user1.getLogin(), "Логины совпадают.");
        assertNotEquals(user.getName(), user1.getName(), "Имена совпадают.");
        assertNotEquals(user.getBirthday(), user1.getBirthday(), "Даты рождения совпадают.");
    }

    @Test
    void shouldNotUpdateUserWhenEmailIsNotContainsSymbol() {
        controller.addUser(user);
        assertThrows(ValidationException.class, () -> controller.updateUser(user2), "Email" +
                " содержит символ @.");
        assertNotEquals(user.getEmail(), user2.getEmail(), "Адреса email совпадают.");
        assertNotEquals(user.getLogin(), user2.getLogin(), "Логины совпадают.");
        assertNotEquals(user.getName(), user2.getName(), "Имена совпадают.");
        assertNotEquals(user.getBirthday(), user2.getBirthday(), "Даты рождения совпадают.");
    }

    @Test
    void shouldNotUpdateUserWhenLoginIsEmpty() {
        controller.addUser(user);
        assertThrows(ValidationException.class, () -> controller.updateUser(user3), "Логин" +
                " не пустой.");
        assertNotEquals(user.getEmail(), user3.getEmail(), "Адреса email совпадают.");
        assertNotEquals(user.getLogin(), user3.getLogin(), "Логины совпадают.");
        assertNotEquals(user.getName(), user3.getName(), "Имена совпадают.");
        assertNotEquals(user.getBirthday(), user3.getBirthday(), "Даты рождения совпадают.");
    }

    @Test
    void shouldNotUpdateUserWhenLoginContainsSpace() {
        controller.addUser(user);
        assertThrows(ValidationException.class, () -> controller.updateUser(user4), "Логин" +
                " не содержит пробелы.");
        assertNotEquals(user.getEmail(), user4.getEmail(), "Адреса email совпадают.");
        assertNotEquals(user.getLogin(), user4.getLogin(), "Логины совпадают.");
        assertNotEquals(user.getName(), user4.getName(), "Имена совпадают.");
        assertNotEquals(user.getBirthday(), user4.getBirthday(), "Даты рождения совпадают.");
    }

    @Test
    void shouldUpdateUserWhenNameIsEmpty() {
        controller.addUser(user);
        assertTrue(controller.getAllUsers().contains(user), "Пользователь не добавлен.");
        controller.updateUser(user5);
        assertEquals(user.getLogin(), user.getName(), "Логин и имя различны.");
        assertEquals(user.getEmail(), user5.getEmail(), "Адреса email не совпадают.");
        assertEquals(user.getLogin(), user5.getLogin(), "Логины не совпадают.");
        assertEquals(user.getName(), user5.getName(), "Имена не совпадают.");
        assertEquals(user.getBirthday(), user5.getBirthday(), "Даты рождения не совпадают.");
    }

    @Test
    void shouldUpdateUserWhenBirthdayIsBeforeNow() {
        controller.addUser(user);
        assertTrue(controller.getAllUsers().contains(user), "Пользователь не добавлен.");
        controller.updateUser(user6);
        assertEquals(user.getEmail(), user6.getEmail(), "Адреса email не совпадают.");
        assertEquals(user.getLogin(), user6.getLogin(), "Логины не совпадают.");
        assertEquals(user.getName(), user6.getName(), "Имена не совпадают.");
        assertEquals(user.getBirthday(), user6.getBirthday(), "Даты рождения не совпадают.");
    }

    @Test
    void shouldUpdateUserWhenBirthdayIsEqualsNow() {
        controller.addUser(user);
        assertTrue(controller.getAllUsers().contains(user), "Пользователь не добавлен.");
        controller.updateUser(user7);
        assertEquals(user.getEmail(), user7.getEmail(), "Адреса email не совпадают.");
        assertEquals(user.getLogin(), user7.getLogin(), "Логины не совпадают.");
        assertEquals(user.getName(), user7.getName(), "Имена не совпадают.");
        assertEquals(user.getBirthday(), user7.getBirthday(), "Даты рождения не совпадают.");
    }

    @Test
    void shouldNotUpdateUserWhenBirthdayIsAfterNow() {
        controller.addUser(user);
        assertThrows(ValidationException.class, () -> controller.updateUser(user8), "Дата" +
                " рождения не в будущем.");
        assertNotEquals(user.getEmail(), user8.getEmail(), "Адреса email совпадают.");
        assertNotEquals(user.getLogin(), user8.getLogin(), "Логины совпадают.");
        assertNotEquals(user.getName(), user8.getName(), "Имена совпадают.");
        assertNotEquals(user.getBirthday(), user8.getBirthday(), "Даты рождения совпадают.");
    }
}