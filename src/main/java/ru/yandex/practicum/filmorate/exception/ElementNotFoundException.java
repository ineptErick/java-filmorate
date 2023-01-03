package ru.yandex.practicum.filmorate.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String param) {
        super(param);
    }
}