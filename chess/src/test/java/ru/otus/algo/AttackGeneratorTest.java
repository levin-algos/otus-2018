package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.otus.algo.converters.MovesConverter;
import ru.otus.algo.parsers.FenParser;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttackGeneratorTest {

    private final FenParser parser = new FenParser();
    private final AttackGenerator generator = new AttackGenerator();

    @ParameterizedTest
    @CsvFileSource(resources = "/fens.txt", delimiter = ';')
    void getAllMoves(String fen, @ConvertWith(MovesConverter.class) Set<String> moves) {

        final Position position = parser.parse(fen, new Position.Builder());

        final Set<Move> allMoves = position.getAllMoves();
        Set<String> m = allMoves.stream()
                .filter(move -> !position.move(move).isCheckTo(position.getSideToMove()))
                .map(move -> String.format("%s%s%s", move.getFrom(), move.getDestination(),
                        move.getType() == MoveType.PROMOTION ? move.getPromotion().getShortName():""))
                .collect(Collectors.toSet());

        assertEquals(moves, m);
    }
}