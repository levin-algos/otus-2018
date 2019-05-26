package ru.otus.algo;

public interface PositionBuilder {

    /**
     * Adds {@link Piece} Pieces into {@link Position} Position object.
     * @param piece - pieces to add
     * @return - PositionBuilder instance;
     */
    PositionBuilder add(Piece... piece);

    /**
     * Sets {@link Side} Side who will make next move
     * @return PositionBuilder instance
     */
    PositionBuilder setMoveSide(Side side);

    /**
     *  Sets pawn {@link Square} Square can be captured by en passant
     * @param enPassant - {@link Square} Square of the pawn
     * @return PositionBuilder instance
     */
    PositionBuilder setEnPassant(Square enPassant);

    /**
     * Set number of half move since the last captures or pawn advance.
     * @param halfMoveClock - number of half moves
     * @return - PositionBuilder instance
     */
    PositionBuilder setHalfMoveClock(int halfMoveClock);

    /**
     * Sets {@code side} can castle {@code castleSide}
     * @param side - player side
     * @param castle - castling side
     * @return PositionBuilder instance;
     */
    PositionBuilder setCaslte(Side side, Castle castle);

    /**
     * Sets number of played moves
     * @param count - positive number of moves;
     * @return PositionBuilder instance
     */
    PositionBuilder setMovesCount(int count);
    /**
     * Constructs {@link Position} Position object
     * @return Position instance
     */
    Position build();
}