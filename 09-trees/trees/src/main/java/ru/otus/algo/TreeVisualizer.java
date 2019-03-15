package ru.otus.algo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


class Image {
    private static final AffineTransform affinetransform = new AffineTransform();
    private static final FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
    private static final Font font = new Font("Tahoma", Font.PLAIN, 16);
    private BufferedImage image;
    private Graphics graphics;

    private Image(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setFont(font);
        setBackground();
    }

    int getWidth() {
        return image.getWidth();
    }

    int getHeight() {
        return image.getHeight();
    }

    static Image of(int width, int height) {
        return new Image(width, height);
    }

    static Image of(String text) {
        Rectangle2D bounds = font.getStringBounds(text, frc);
        Image image = new Image((int) bounds.getWidth(), (int) bounds.getHeight());
        image.drawString(text, 0, (int)bounds.getHeight(), TextAlign.LEFT);
        return image;
    }

    void drawString(String text, int x, int y, TextAlign align) {
        graphics.setColor(Color.BLACK);
        int paddingX = 0;
        if (TextAlign.CENTER == align) {
            paddingX = graphics.getFontMetrics().stringWidth(text) / 2;
        }
        graphics.drawString(text, x - paddingX, y);
    }

    void setBackground() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    void drawLine(int x0, int y0, int x1, int y1, Color color) {
        int h = graphics.getFontMetrics().getHeight();
        graphics.setColor(color);
        graphics.drawLine(x0, y0 + h + 5, x1, y1);
    }

    void save(Path path) {
        try {
            File file = new File(path.toString());
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Image combineHorizontal(Image left, Image right) {
        if (left == null || right == null)
            throw new IllegalArgumentException();

        int newW = left.getWidth() + right.getWidth();
        int newH = left.getHeight() > right.getHeight() ? left.getHeight() : right.getHeight();

        Image res = new Image(newW, newH);
        res.drawImage(left, 0, 0);
        res.drawImage(right, left.getWidth(), 0);
        return res;
    }

    static Image combineVertical(Image top, Image bottom) {
        if (top == null || bottom == null)
            throw new IllegalArgumentException();

        int newW = top.getWidth() > bottom.getWidth() ? top.getWidth() : bottom.getWidth();
        int newH = top.getHeight() + bottom.getHeight();

        int topX = 0, bottomX = 0;
        if (top.getWidth() > bottom.getWidth()) {
            bottomX = top.getWidth() / 2 - bottom.getWidth()/2;
        } else if (top.getWidth() < bottom.getWidth()) {
            topX = bottom.getWidth() / 2 - top.getWidth()/2;
        }
        Image res = new Image(newW, newH);
        res.drawImage(top, topX, 0);
        res.drawImage(bottom, bottomX, top.getHeight());
        return res;
    }

    private void drawImage(Image image, int x, int y) {
        this.image.getGraphics().drawImage(image.image, x, y, null);
    }
}

enum TextAlign {
    LEFT,
    CENTER
}

class TreeVisualizer {
    private Image image;

    public TreeVisualizer(AbstractBinarySearchTree<?> tree, String text) {
        image = Image.combineVertical(Image.of(text), tree.asImage());
    }

    public TreeVisualizer(AbstractBinarySearchTree<?> tree) {
        image = tree.asImage();
    }

    public <T> void add(AbstractBinarySearchTree<T> tree, String text) {
        Image txtImage = Image.of(text);
        Image bot = tree.asImage();

        image = Image.combineVertical(image,
                Image.combineVertical(txtImage, bot));
    }

    public <T> void add(AbstractBinarySearchTree<T> left) {
        image = Image.combineVertical(image, left.asImage());
    }

    public void addBottom(String s) {
        image = Image.combineVertical(image, Image.of(s));
    }

    public <T> void addRight(AbstractBinarySearchTree<T> right) {
        image = Image.combineHorizontal(image, right.asImage());
    }

    public void addBottom(TreeVisualizer visualizer) {
        image = Image.combineVertical(image, visualizer.image);
    }

    public <T> void addBottom(AbstractBinarySearchTree<T> bottom, String s) {
        image = Image.combineVertical(image,
                Image.combineVertical(Image.of(s), bottom.asImage()));
    }

    void save(Path path) {
        if (image != null)
            image.save(path);
    }
}