import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class SvgShape {
    protected Color fill = null;
    protected Color stroke = Color.BLACK;
    protected float strokeWidth = 1.0f;

    public void setStyles(String fillStr, String strokeStr, String strokeWidthStr) {
        this.fill = parseColor(fillStr);
        if (strokeStr != null && !strokeStr.equals("none")) {
            this.stroke = parseColor(strokeStr);
        } else if ("none".equals(strokeStr)) {
            this.stroke = null;
        }
        if (strokeWidthStr != null && !strokeWidthStr.isEmpty()) {
            this.strokeWidth = Float.parseFloat(strokeWidthStr);
        }
    }

    protected void applyStyles(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(strokeWidth));
    }

    private Color parseColor(String colorStr) {
        if (colorStr == null || colorStr.equals("none")) return null;
        return switch (colorStr.toLowerCase()) {
            case "red" -> Color.RED;
            case "blue" -> Color.BLUE;
            case "green" -> Color.GREEN;
            case "black" -> Color.BLACK;
            case "yellow" -> Color.YELLOW;
            case "orange" -> Color.ORANGE;
            case "lightblue" -> new Color(173, 216, 230);
            case "lightgreen" -> new Color(144, 238, 144);
            case "navy" -> new Color(0, 0, 128);
            default -> {
                if (colorStr.startsWith("#")) {
                    yield Color.decode(colorStr);
                }
                yield null;
            }
        };
    }

    public abstract void draw(Graphics2D g2d);
}