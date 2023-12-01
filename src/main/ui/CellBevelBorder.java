package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

// Used to create bevel border of cells with a variable width, unlike the normal BevelBorder class
public class CellBevelBorder extends AbstractBorder {
    private final Color topColor;
    private final Color rightColor;
    private final Color bottomColor;
    private final Color leftColor;
    private final Integer borderWidth;

    public CellBevelBorder(Color topColor, Color rightColor, Color bottomColor, Color leftColor, Integer borderWidth) {
        this.topColor = topColor;
        this.rightColor = rightColor;
        this.bottomColor = bottomColor;
        this.leftColor = leftColor;
        this.borderWidth = borderWidth;
    }

    // EFFECTS: overrides paintBorder method of AbstractBorder in order to create custom border
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);

        Integer bw = getBorderWidth();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);

        createTopPolygon(g2, width, height, bw);
        createRightPolygon(g2, width, height, bw);
        createBottomPolygon(g2, width, height, bw);
        createLeftPolygon(g2, width, height, bw);

        g2.dispose();
    }


    // EFFECTS: created polygon used for top of bevel border
    private void createTopPolygon(Graphics2D g2, Integer w, Integer h, Integer bw) {
        Polygon topPolygon = createPolygon(new Point(0, 0), new Point(w, 0), new Point(w - bw, bw), new Point(bw, bw),
                new Point(0, 0));
        g2.setColor(getTopColor());
        g2.fill(topPolygon);
    }

    // EFFECTS: created polygon used for right of bevel border
    private void createRightPolygon(Graphics2D g2, Integer w, Integer h, Integer bw) {
        Polygon rightPolygon = createPolygon(new Point(w - 1, 0), new Point(w - 1, h), new Point(w - bw - 1, h - bw),
                new Point(w - bw - 1, bw), new Point(w - 1, 0));
        g2.setColor(getRightColor());
        g2.fill(rightPolygon);
    }

    // EFFECTS: created polygon used for bottom of bevel border
    private void createBottomPolygon(Graphics2D g2, Integer w, Integer h, Integer bw) {
        Polygon bottomPolygon = createPolygon(new Point(0, h - 1), new Point(w, h - 1), new Point(w - bw, h - bw - 1),
                new Point(bw, h - bw - 1), new Point(0, h - 1));
        g2.setColor(getBottomColor());
        g2.fill(bottomPolygon);
    }

    // EFFECTS: created polygon used for left of bevel border
    private void createLeftPolygon(Graphics2D g2, Integer w, Integer h, Integer bw) {
        Polygon leftPolygon = createPolygon(new Point(0, 0), new Point(0, h), new Point(bw, h - bw), new Point(bw, bw),
                new Point(0, 0));
        g2.setColor(getLeftColor());
        g2.fill(leftPolygon);
    }

    // EFFECTS: created a polygon with however many points
    private Polygon createPolygon(Point... points) {
        Polygon polygon = new Polygon();
        for (Point point : points) {
            polygon.addPoint(point.x, point.y);
        }
        return polygon;
    }

    public Color getTopColor() {
        return topColor;
    }

    public Color getRightColor() {
        return rightColor;
    }

    public Color getBottomColor() {
        return bottomColor;
    }

    public Color getLeftColor() {
        return leftColor;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }
}
