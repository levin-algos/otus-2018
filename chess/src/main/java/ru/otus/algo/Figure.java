package ru.otus.algo;

import java.util.Set;

public interface Figure {


    int getPosition();

    Side getSide();

    Figures getFigureType();

    Iterable<Integer> getMoves();
}
