package ru.otus;

import java.util.Comparator;
import java.util.List;

/**
 * Class represents chess player from https://ratings.fide.com/download.phtml
 */
public final class ChessPlayer implements Comparable<ChessPlayer> {

    public static final Comparator<ChessPlayer> RATING_DESC = (chessPlayer, t1) -> Integer.compare(t1.rating, chessPlayer.rating);

    private final int id;
    private final String def;
    private final Sex sex;
    private final int rating;
    private final String title;

    public ChessPlayer(int id, String def, Sex sex, int rating, String title) {
        this.id = id;
        this.def = def;
        this.sex = sex;
        this.rating = rating;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getDef() {
        return def;
    }

    public Sex getSex() {
        return sex;
    }

    public int getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public static List<ChessPlayer> load(String standardRating, int i) {
        return null;
    }

    @Override
    public int compareTo(ChessPlayer chessPlayer) {
        return Integer.compare(this.rating, chessPlayer.rating);
    }
}
