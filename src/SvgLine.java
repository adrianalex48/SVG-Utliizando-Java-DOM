import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class SvgLine extends SvgShape {
    private double x1, y1, x2, y2;

    public SvgLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Aplica el grosor de la línea
        applyStyles(g2d);

        // Crea la línea geométrica de Java 2D
        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);

        // A diferencia del círculo, las líneas no tienen "relleno" (fill), 
        // solo dibujamos el contorno (stroke)
        if (stroke != null) {
            g2d.setColor(stroke);
            g2d.draw(line);
        }
    }
}