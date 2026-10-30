package vista;

import modelo.*;
import util.UtilGenerico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    private ArrayList<Producto> listaProductos;
    private ArrayList<Pedido> listaPedidos;

    private JTabbedPane tabbedPane;
    
   
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock;
    private JTable tablaProductos;
    private DefaultTableModel modeloTablaProductos;
    private JButton btnGuardarProducto, btnReponerStock;

    
    private JComboBox<Producto> cmbProductos;
    private JTextField txtCantidadPedido;
    private JTextArea txtAreaFactura;
    private JButton btnAgregarDetalle, btnConfirmarPedido;

   
    private JTextArea txtReporte;
    private JButton btnGenerarReporte;

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Pedidos - El Buen Sabor v2.0");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        listaProductos = GestionArchivos.cargarProductos();
        listaPedidos = GestionArchivos.cargarPedidos();

        if (listaProductos.isEmpty()) {
            listaProductos = UtilGenerico.crearLista(
                new Producto("P001", "Café Americano", 1.50, 50),
                new Producto("P002", "Bolón de Queso", 2.00, 30),
                new Producto("P003", "Jugo Natural", 1.75, 40)
            );
            GestionArchivos.guardarProductos(listaProductos);
        }

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Módulo Productos", crearPanelProductos());
        tabbedPane.addTab("Módulo Pedidos", crearPanelPedidos());
        tabbedPane.addTab("Módulo Reportes", crearPanelReportes());

        add(tabbedPane);
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Gestión de Productos"));

        panelForm.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Precio ($):"));
        txtPrecio = new JTextField();
        panelForm.add(txtPrecio);

        panelForm.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelForm.add(txtStock);

        btnGuardarProducto = new JButton("Guardar / Actualizar");
        panelForm.add(btnGuardarProducto);

        btnReponerStock = new JButton("Reponer Stock (+10)");
        panelForm.add(btnReponerStock);

        panel.add(panelForm, BorderLayout.NORTH);

        modeloTablaProductos = new DefaultTableModel(new String[]{"Código", "Nombre", "Precio", "Stock"}, 0);
        tablaProductos = new JTable(modeloTablaProductos);
        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        actualizarTablaProductos();
        return panel;
    }

    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelSup = new JPanel(new FlowLayout());
        cmbProductos = new JComboBox<>();
        actualizarComboProductos();

        txtCantidadPedido = new JTextField(5);
        btnAgregarDetalle = new JButton("Agregar al Pedido");
        btnConfirmarPedido = new JButton("Confirmar Pedido");

        panelSup.add(new JLabel("Producto:"));
        panelSup.add(cmbProductos);
        panelSup.add(new JLabel("Cantidad:"));
        panelSup.add(txtCantidadPedido);
        panelSup.add(btnAgregarDetalle);
        panelSup.add(btnConfirmarPedido);

        panel.add(panelSup, BorderLayout.NORTH);

        txtAreaFactura = new JTextArea();
        txtAreaFactura.setEditable(false);
        panel.add(new JScrollPane(txtAreaFactura), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        btnGenerarReporte = new JButton("Actualizar Reportes de Ventas");

        panel.add(btnGenerarReporte, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtReporte), BorderLayout.CENTER);

        return panel;
    }

    public ArrayList<Producto> getListaProductos() { return listaProductos; }
    public ArrayList<Pedido> getListaPedidos() { return listaPedidos; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtStock() { return txtStock; }
    public JTable getTablaProductos() { return tablaProductos; }
    public DefaultTableModel getModeloTablaProductos() { return modeloTablaProductos; }
    public JComboBox<Producto> getCmbProductos() { return cmbProductos; }
    public JTextField getTxtCantidadPedido() { return txtCantidadPedido; }
    public JTextArea getTxtAreaFactura() { return txtAreaFactura; }
    public JTextArea getTxtReporte() { return txtReporte; }
    public JButton getBtnGuardarProducto() { return btnGuardarProducto; }
    public JButton getBtnReponerStock() { return btnReponerStock; }
    public JButton getBtnAgregarDetalle() { return btnAgregarDetalle; }
    public JButton getBtnConfirmarPedido() { return btnConfirmarPedido; }
    public JButton getBtnGenerarReporte() { return btnGenerarReporte; }

    public void actualizarTablaProductos() {
        modeloTablaProductos.setRowCount(0);
        for (Producto p : listaProductos) {
            modeloTablaProductos.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio(), p.getStock()});
        }
    }

    public void actualizarComboProductos() {
        cmbProductos.removeAllItems();
        for (Producto p : listaProductos) {
            cmbProductos.addItem(p);
        }
    }

    public void limpiarFormularioProducto() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }
}