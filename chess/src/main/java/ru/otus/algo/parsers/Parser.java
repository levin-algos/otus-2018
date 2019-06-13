package ru.otus.algo.parsers;

import ru.otus.algo.Castle;
import ru.otus.algo.Side;
import ru.otus.algo.Square;

import java.util.Optional;

/**
 * Abstraction for converter text formats into game position.
 */
public interface Parser {

    /**
     * Returns {@link Side} Side who will make next move
     * @return - Returns {@link Side} Side who will make next move
     */
    Side getNextMove();

    /**
     * Returns number of half move since the last captures or pawn advance.
     * @return - number of half move since the last captures or pawn advance.
     */
    int getHalfMoveClock();

    /**
     * Return pawn {@link Square} Square can be captured by en passant
     * @return {@link Square} Square of pawn can be captured by en passant.
     */
    Optional<Square> getEnPassant();

    /**
     * Checks if {@code side} can castle {@code castleSide}
     * @param side - castle side
     * @param castleSide - castle direction
     * @return - true if {@code side} can castle ({@code castleSide},
     *          false - otherwise
     */
    boolean canCastle(Side side, Castle castleSide);
}
