package ru.otus.algo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class TreeVisualizer {
    private BufferedImage image;
    private int width, height;
    private int coeffX = 60;
    private int coeffY = 45;

    TreeVisualizer(int maxTreeHeight) {
        if (maxTreeHeight <=0)
            throw new IllegalArgumentException();

        int height = maxTreeHeight;
        this.width = (1 << (height - 1)) * coeffX;
        this.height = height * coeffY;
        image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().setFont(new Font("default", Font.BOLD, 16));
        setBackground();
    }

    private void drawNode(int x, int y, AbstractBinarySearchTree.Node<?> node) {
        Graphics graphics = image.getGraphics();
        String str = node.toString();
        int width = graphics.getFontMetrics().stringWidth(str);
        int h = graphics.getFontMetrics().getHeight();
        graphics.setColor(Color.BLACK);
        graphics.drawString(str, x-width/2, y+h);
    }

    private void setBackground() {
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
    }

    private void drawLine(int x0, int y0, int x1, int y1, Color color) {
        Graphics graphics = image.getGraphics();
        int h = graphics.getFontMetrics().getHeight();
        graphics.setColor(color);
        graphics.drawLine(x0, y0+h+5, x1, y1);
    }

    public void save(Path path, AbstractBinarySearchTree.Node<?> node) {
        drawTree(width/2, 0, node, Color.BLACK);
        try {
            File file = new File(path.toString());
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawTree(int x, int lvl, AbstractBinarySearchTree.Node<?> root, Color lineColor) {
        int y0 = lvl * coeffY;
        drawNode(x, y0, root);
        int pad = width / (1 << (lvl+1));
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
}