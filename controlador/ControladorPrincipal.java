package controlador;

import modelo.*;
import vista.VentanaPrincipal;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorPrincipal {
    private VentanaPrincipal vista;
    private ArrayList<DetallePedido> detallesActuales;

    public ControladorPrincipal(VentanaPrincipal vista) {
        this.vista = vista;
        this.detallesActuales = new ArrayList<>();
        initListeners();
    }

    private void initListeners() {
        
        vista.getBtnGuardarProducto().addActionListener(e -> {
            try {
                String cod = vista.getTxtCodigo().getText().trim();
                String nom = vista.getTxtNombre().getText().trim();
                double precio = Double.parseDouble(vista.getTxtPrecio().getText().trim());
                int stock = Integer.parseInt(vista.getTxtStock().getText().trim());

                if (cod.isEmpty() || nom.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<Producto> productos = vista.getListaProductos();
                Producto existente = productos.stream().filter(p -> p.getCodigo().equalsIgnoreCase(cod)).findFirst().orElse(null);
                
                if (existente != null) {
                    existente.setNombre(nom);
                    existente.setPrecio(precio);
                    existente.setStock(stock);
                } else {
                    productos.add(new Producto(cod, nom, precio, stock));
                }

                GestionArchivos.guardarProductos(productos);
                vista.actualizarTablaProductos();
                vista.actualizarComboProductos();
                JOptionPane.showMessageDialog(vista, "Producto guardado con éxito.");
                vista.limpiarFormularioProducto();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese valores numéricos válidos en Precio y Stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        vista.getBtnReponerStock().addActionListener(e -> {
            int fila = vista.getTablaProductos().getSelectedRow();
            if (fila >= 0) {
                String cod = (String) vista.getModeloTablaProductos().getValueAt(fila, 0);
                Producto p = vista.getListaProductos().stream().filter(prod -> prod.getCodigo().equals(cod)).findFirst().orElse(null);
                if (p != null) {
                    p.setStock(p.getStock() + 10);
                    GestionArchivos.guardarProductos(vista.getListaProductos());
                    vista.actualizarTablaProductos();
                    JOptionPane.showMessageDialog(vista, "Stock repuesto en 10 unidades.");
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla.");
            }
        });

        vista.getBtnAgregarDetalle().addActionListener(e -> {
            try {
                Producto prod = (Producto) vista.getCmbProductos().getSelectedItem();
                int cant = Integer.parseInt(vista.getTxtCantidadPedido().getText().trim());

                if (prod == null) return;
                if (cant <= 0) {
                    JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor a 0.");
                    return;
                }
                if (prod.getStock() < cant) {
                    JOptionPane.showMessageDialog(vista, "Stock insuficiente. Disponible: " + prod.getStock(), "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                detallesActuales.add(new DetallePedido(prod, cant));
                prod.setStock(prod.getStock() - cant);
                GestionArchivos.guardarProductos(vista.getListaProductos());
                vista.actualizarTablaProductos();

                vista.getTxtAreaFactura().append(prod.getNombre() + " x " + cant + " = $" + (prod.getPrecio() * cant) + "\n");
                vista.getTxtCantidadPedido().setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese una cantidad numérica válida.");
            }
        });

        vista.getBtnConfirmarPedido().addActionListener(e -> {
            if (detallesActuales.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El pedido no tiene productos agregados.");
                return;
            }

            ArrayList<Pedido> pedidos = vista.getListaPedidos();
            int id = pedidos.size() + 1;
            Pedido nuevoPedido = new Pedido(id, new ArrayList<>(detallesActuales));
            pedidos.add(nuevoPedido);
            GestionArchivos.guardarPedidos(pedidos);

            double subtotal = nuevoPedido.getTotal();
            double iva = subtotal * 0.15;
            double total = subtotal + iva;

            vista.getTxtAreaFactura().append("\n--- FACTURA FINAL (Pedido #" + id + ") ---\n");
            vista.getTxtAreaFactura().append("Subtotal: $" + subtotal + "\n");
            vista.getTxtAreaFactura().append("IVA (15%): $" + iva + "\n");
            vista.getTxtAreaFactura().append("TOTAL: $" + total + "\n\n");

            detallesActuales.clear();
        });

        vista.getBtnGenerarReporte().addActionListener(e -> {
            JTextArea txtReporte = vista.getTxtReporte();
            txtReporte.setText("=== REPORTE GENERAL DE VENTAS ===\n\n");
            ArrayList<Pedido> pedidos = vista.getListaPedidos();
            txtReporte.append("Total de pedidos registrados: " + pedidos.size() + "\n");
            double totalVentas = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
            txtReporte.append("Monto total recaudado: $" + totalVentas + "\n\n");
            for (Pedido p : pedidos) {
                txtReporte.append("Pedido ID: " + p.getIdPedido() + " - Total: $" + p.getTotal() + "\n");
            }
        });
    }
}