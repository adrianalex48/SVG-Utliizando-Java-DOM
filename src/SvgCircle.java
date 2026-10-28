import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class SvgCircle extends SvgShape {
    private double cx, cy, r;

    public SvgCircle(double cx, double cy, double r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    @Override
    public void draw(Graphics2D g2d) {
        applyStyles(g2d);
        Ellipse2D.Double circle = new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2);

        if (fill != null) {
            g2d.setColor(fill);
            g2d.fill(circle);
        }
        if (stroke != null) {
            g2d.setColor(stroke);
            g2d.draw(circle);
        }
    }
}