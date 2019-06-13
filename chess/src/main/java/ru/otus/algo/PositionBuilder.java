package ru.otus.algo;

public interface PositionBuilder {

    /**
     * Adds {@link Piece} Pieces into {@link Position} Position object.
     */
    void add(Side side, Figure figure, Square square);

    /**
     * Sets {@link Side} Side who will make next move
     */
    void setMoveSide(Side side);

    /**
     *  Sets pawn {@link Square} Square can be captured by en passant
     * @param enPassant - {@link Square} Square of the pawn
     */
    void setEnPassant(Square enPassant);

    /**
     * Set number of half move since the last captures or pawn advance.
     * @param halfMoveClock - number of half moves
     */
    void setHalfMoveClock(int halfMoveClock);

    /**
     * Sets {@code side} can castle {@code castleSide}
     * @param side - player side
     * @param castle - castling side
     */
    void setCastle(Side side, Castle castle);

    /**
     * Sets number of played moves
     * @param count - positive number of moves;
     */
    void setMovesCount(int count);
    /**
     * Constructs {@link Position} Position object
     * @return Position instance
     */
    Position build();
}