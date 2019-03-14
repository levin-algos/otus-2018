package ru.otus.algo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class TreeVisualizer {
    private BufferedImage image;
    private static final int coeffX = 60;
    private static final int coeffY = 45;
    private int stringHeight;
    private Graphics graphics;

    private void createImage(int treeHeight) {
        if (treeHeight <= 0)
            throw new IllegalArgumentException();

        int h = treeHeight + 1;
        int width = (1 << (h - 1)) * coeffX;
        int height = h * coeffY;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setFont(new Font("default", Font.BOLD, 16));
        setBackground();

        stringHeight = this.graphics.getFontMetrics().getHeight();
    }


    private void drawNode(int x, int y, AbstractBinarySearchTree.Node<?> node) {
        String str = node.toString();
        int width = graphics.getFontMetrics().stringWidth(str);
        graphics.setColor(Color.BLACK);
        graphics.drawString(str, x - width / 2, y + stringHeight);
    }

    private void setBackground() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    private void drawLine(int x0, int y0, int x1, int y1, Color color) {
        int h = graphics.getFontMetrics().getHeight();
        graphics.setColor(color);
        graphics.drawLine(x0, y0 + h + 5, x1, y1);
    }

    void drawTree(AbstractBinarySearchTree.Node<?> root, int treeHeight) {
        createImage(treeHeight);
        drawTree(image.getWidth() / 2, 0, root, Color.BLACK);
    }

    private void drawTree(int x, int lvl, AbstractBinarySearchTree.Node<?> root, Color lineColor) {
        int y0 = lvl * coeffY;
        drawNode(x, y0, root);
        int pad = image.getWidth() / (1 << (lvl + 1));
        int y1 = (lvl + 1) * coeffY;
        if (root.left != null) {
            drawLine(x, y0, x - pad / 2, y1, lineColor);
            drawTree(x - pad / 2, lvl + 1, root.left, lineColor);
        }

        if (root.right != null) {
            drawLine(x, y0, x + pad / 2, y1, lineColor);
            drawTree(x + pad / 2, lvl + 1, root.right, lineColor);
        }
    }

    public <T> void add(AbstractBinarySearchTree<T> tree, String text) {
        TreeVisualizer vis = new TreeVisualizer();
        tree.accept(vis);
        if (image == null)
            image = vis.image;
    }

    public <T> void add(AbstractBinarySearchTree<T> left) {

    }

    public void addBottom(String s) {

    }

    public <T> void addRight(AbstractBinarySearchTree<T> right) {

    }

    public void addBottom(TreeVisualizer visualizer) {

    }

    public <T> void addBottom(AbstractBinarySearchTree<T> bottom, String s) {
    }

    public void save(Path path) {
        try {
            File file = new File(path.toString());
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}