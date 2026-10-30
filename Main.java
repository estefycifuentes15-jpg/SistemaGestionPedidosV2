import vista.VentanaPrincipal;
import controlador.ControladorPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           
            VentanaPrincipal ventana = new VentanaPrincipal();
            
            new ControladorPrincipal(ventana); 
            
            ventana.setVisible(true);
        });
    }
}
