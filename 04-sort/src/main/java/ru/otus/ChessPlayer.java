package ru.otus;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Class represents chess player from https://ratings.fide.com/download.phtml
 */
public final class ChessPlayer implements Comparable<ChessPlayer> {

    public static final Comparator<ChessPlayer> RATING_DESC = (chessPlayer, t1) -> Integer.compare(t1.rating, chessPlayer.rating);

    private final String id;
    private final String def;
    private final Sex sex;
    private final int rating;
    private final String title;

    public ChessPlayer(String id, String def, Sex sex, int rating, String title) {
        this.id = id;
        this.def = def;
        this.sex = sex;
        this.rating = rating;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPlayer that = (ChessPlayer) o;
        return rating == that.rating &&
                id.equals(that.id) &&
                def.equals(that.def) &&
                sex == that.sex &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, def, sex, rating, title);
    }

    public String getId() {
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

    public static List<ChessPlayer> loadXML(String xmlRating, int i) {
        ArrayList<ChessPlayer> res = new ArrayList<>();

        return res;
    }

    @Override
    public int compareTo(ChessPlayer chessPlayer) {
        return Integer.compare(this.rating, chessPlayer.rating);
    }
}
