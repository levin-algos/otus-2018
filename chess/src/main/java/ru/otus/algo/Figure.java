package ru.otus.algo;

import java.util.Set;

public interface Figure {


    Position getPosition();

    Side getSide();

    Figures getFigureType();

    Set<Position> getMoves();
}
