import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SvgDomParser {

    public List<SvgShape> parse(File file) throws Exception {
        List<SvgShape> shapes = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        // Deshabilitar entidades externas por seguridad
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();

        processNode(document.getDocumentElement(), shapes);
        return shapes;
    }

    private void processNode(Node node, List<SvgShape> shapes) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tagName = element.getTagName();

            SvgShape shape = switch (tagName) {
                case "circle" -> new SvgCircle(
                        getDouble(element, "cx"),
                        getDouble(element, "cy"),
                        getDouble(element, "r")
                );
                case "line" -> new SvgLine(
                        // Implementación similar extraída de x1, y1, x2, y2
                        getDouble(element, "x1"), getDouble(element, "y1"),
                        getDouble(element, "x2"), getDouble(element, "y2")
                );
                // Agregar casos para rect, polyline, ellipse, text
                default -> null;
            };

            if (shape != null) {
                shape.setStyles(
                        element.getAttribute("fill"),
                        element.getAttribute("stroke"),
                        element.getAttribute("stroke-width")
                );
                shapes.add(shape);
            }
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            processNode(children.item(i), shapes);
        }
    }

    private double getDouble(Element e, String attribute) {
        String val = e.getAttribute(attribute);
        return val.isEmpty() ? 0 : Double.parseDouble(val);
    }
}