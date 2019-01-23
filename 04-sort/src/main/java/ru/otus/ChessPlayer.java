package ru.otus;

import lombok.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Class represents chess player from https://ratings.fide.com/download.phtml
 */
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
public final class ChessPlayer implements Comparable<ChessPlayer> {

    public static final Comparator<ChessPlayer> RATING_DESC = (chessPlayer, t1) -> Integer.compare(t1.rating, chessPlayer.rating);

    private final String id;
    private final String name;
    private final Sex sex;
    private final int rating;
    private final String title;


    public static List<ChessPlayer> loadXML(String xmlRating, int i) {
        ArrayList<ChessPlayer> res = new ArrayList<>();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        try {
            SAXParser saxParser = spf.newSAXParser();

            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new ChessPlayerSAXHandler(res));
            ClassLoader classLoader = ChessPlayer.class.getClassLoader();
            xmlReader.parse(classLoader.getResource(xmlRating).getFile());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public int compareTo(ChessPlayer chessPlayer) {
        return Integer.compare(this.rating, chessPlayer.rating);
    }

    private static class ChessPlayerSAXHandler extends DefaultHandler {
        private final List<ChessPlayer> players;
        private ChessPlayerBuilder cur;
        private String chars;

        public ChessPlayerSAXHandler(List<ChessPlayer> players) {
            if (players == null)
                throw new IllegalArgumentException();

            this.players = players;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (cur == null) {
                if ("player".equals(localName)) {
                    cur = new ChessPlayerBuilder();
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (cur == null) {
                if ("player".equals(localName)) {
                    throw new IllegalStateException();
                }
            } else {
                if ("fideid".equals(localName)) {
                    cur.id(chars);
                } else if ("name".equals(localName)) {
                    cur.name(chars);
                } else if ("sex".equals(localName)) {
                    if ("M".equals(chars)) {
                        cur.sex(Sex.MALE);
                    } else {
                        cur.sex(Sex.FEMALE);
                    }
                } else if ("title".equals(localName)) {
                    cur.title(chars);
                } else if ("rating".equals(localName)) {
                    cur.rating(Integer.valueOf(chars));
                } else if ("player".equals(localName)) {
                    players.add(cur.build());
                    cur = null;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            chars = new String(ch, start, length).trim();
        }
    }
}
