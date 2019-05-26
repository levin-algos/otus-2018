package ru.otus.algo.parsers;

import ru.otus.algo.*;

import java.util.HashSet;
import java.util.Set;

public class FenParser {

    Position parse(String fen, PositionBuilder builder) {
        String[] fields = fen.split(" ");

        addFigures(fields[0], builder);
        setNextMove(fields[1], builder);
        setCastling(fields[2], builder);
        setEnPassant(fields[3], builder);
        setHalfMove(fields[4], builder);
        setNumOfMoves(fields[5], builder);

        return builder.build();
    }

    private void setNumOfMoves(String field, PositionBuilder builder) {
        int moves = Integer.parseInt(field);
        if (moves <= 0)
            throw new ParseError("wrong number of moves");

        builder.setMovesCount(moves);
    }

    private void setHalfMove(String field, PositionBuilder builder) {
        int halfMoveClock = Integer.parseInt(field);
        if (halfMoveClock < 0)
            throw new ParseError("Half move cloak < 0");
        builder.setHalfMoveClock(halfMoveClock);
    }

    private void setEnPassant(String field, PositionBuilder builder) {
        if ("-".equals(field))
            return;

        try {
            Square enPassant = Square.valueOf(field);
            builder.setEnPassant(enPassant);
        } catch (IllegalArgumentException e) {
            throw new ParseError(field +" is not recognised");
        }
    }

    private void setCastling(String field, PositionBuilder builder) {
        if ("-".equals(field))
            return;

        Set<Character> set = new HashSet<>();
        for (char f : field.toCharArray()) {
            if (!Character.isLetter(f))
                throw new ParseError("'" + f + "'" + " must be letter");

            if (set.contains(f))
                throw new ParseError("wrong castle block");
            Side side = Character.isLowerCase(f) ? Side.BLACK : Side.WHITE;
            char letter = Character.toUpperCase(f);

            Castle castle;
            if (letter == 'K')
                castle = Castle.KINGS_SIDE;
            else if (letter == 'Q')
                castle = Castle.QUEENS_SIDE;
            else
                throw new ParseError("'" + f + "'" + " is not recognised");

            builder.setCaslte(side, castle);
            set.add(f);
        }
    }

    private void setNextMove(String field, PositionBuilder builder) {
        if ("w".equals(field)) builder.setMoveSide(Side.WHITE);
        else if ("b".equals(field)) builder.setMoveSide(Side.BLACK);
        else throw new ParseError(field + " is not recognised");
    }

    private void addFigures(String field, PositionBuilder builder) {
        Square.Rank[] ranks = {Square.Rank.FIRST, Square.Rank.SECOND, Square.Rank.THIRD, Square.Rank.FORTH,
                Square.Rank.FIFTH, Square.Rank.SIXTH, Square.Rank.SEVENTH, Square.Rank.EIGHTS};
        Square.File[] files = {Square.File.A, Square.File.B, Square.File.C, Square.File.D, Square.File.E,
                Square.File.F, Square.File.G, Square.File.H};
        int rank = 7, file = 0;
        for (char f : field.toCharArray()) {
            if (Character.isDigit(f)) {
                file += f - '0';
            } else if (f == '/') {
                if (file != 8 || rank <= 0)
                    throw new ParseError("Wrong format");
                file = 0;
                rank--;
            } else if (Character.isLetter(f)) {
                assert file <= 7;
                Side side = Character.isLowerCase(f) ? Side.BLACK : Side.WHITE;
                Figure figure = getFigure(f);
                builder.add(Piece.of(side, figure, Square.of(ranks[rank], files[file])));
                file++;
                assert file <= 8;
            } else {
                throw new ParseError(f + " unrecognised character");
            }
        }
    }

    private Figure getFigure(char f) {
        f = Character.toUpperCase(f);
        Figure res = null;
        if (f == 'P') res = Figure.PAWN;
        else if (f == 'R') res = Figure.ROOK;
        else if (f == 'N') res = Figure.KNIGHT;
        else if (f == 'B') res = Figure.BISHOP;
        else if (f == 'Q') res = Figure.QUEEN;
        else if (f == 'K') res = Figure.KING;
        if (res == null)
            throw new ParseError(f + " is undefined character for piece");
        return res;
    }
}
