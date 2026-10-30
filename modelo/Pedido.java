package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Pedido implements Serializable {
    private int idPedido;
    private ArrayList<DetallePedido> detalles;
    private double total;

    public Pedido(int idPedido, ArrayList<DetallePedido> detalles) {
        this.idPedido = idPedido;
        this.detalles = detalles;
        this.total = detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();
    }

    public int getIdPedido() { return idPedido; }
    public ArrayList<DetallePedido> getDetalles() { return detalles; }
    public double getTotal() { return total; }
}