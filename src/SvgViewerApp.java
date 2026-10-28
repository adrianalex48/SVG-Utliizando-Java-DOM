import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SvgViewerApp extends JFrame {
    private DrawingPanel drawingPanel;
    private SvgDomParser parser;

    public SvgViewerApp() {
        setTitle("Visor SVG Java 2D");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        parser = new SvgDomParser();
        drawingPanel = new DrawingPanel();

        JButton btnLoad = new JButton("Cargar SVG");
        btnLoad.addActionListener(e -> loadSvgFile());

        add(btnLoad, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
    }

    private void loadSvgFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                List<SvgShape> shapes = parser.parse(file);
                drawingPanel.setShapes(shapes);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar XML: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SvgViewerApp().setVisible(true));
    }

    class DrawingPanel extends JPanel {
        private List<SvgShape> shapes = new ArrayList<>();

        public void setShapes(List<SvgShape> shapes) {
            this.shapes = shapes;
            repaint(); // Solicita el repintado de Swing
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            // Configurar antialiasing para mejorar el renderizado
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (SvgShape shape : shapes) {
                shape.draw(g2d);
            }
        }
    }
}