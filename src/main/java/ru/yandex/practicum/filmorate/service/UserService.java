package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@Component
public class UserService {
    Map<Integer, User> users = new HashMap<>();

    public int getId() {
        return ++id;
    }

    private int id = 0;

    private boolean checkValidData(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Введен пустой email или отсутствует символ @.", UserService.class);
            throw new ValidationException("Email не может быть пустым и должен содержать символ @.");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Введен пустой логин или логин содержит пробелы.", UserService.class);
            throw new ValidationException("Логин не может быть пустым или содержать пробелы.");
        }
        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Введена дата рождения из будущего.", UserService.class);
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        return true;
    }

    private boolean checkUpdateValidData(User user) {
        if (checkValidData(user)) {
            if (!users.containsKey(user.getId())) {
                log.error("Введен неверный id.", UserService.class);
                throw new ElementNotFoundException("пользователь с id" + user.getId());
            }
        }
        return true;
    }

    public List<User> allUsers() {
        return users.values().stream().toList();
    }

    public User addUser(User user) {
        int id = getId();
        if (checkValidData(user)) {
            user.setId(id);
            users.put(id,user);
        }
        return user;
    }

    public User updateUser(User user, int id) {
        if (checkUpdateValidData(user)) {
            return users.put(id,user);
        }
        return user;
    }
}