package ru.otus.algo.parsers;

public class ParseError extends RuntimeException {
    public ParseError(String s) {
        super(s);
    }
}
