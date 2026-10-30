package modelo;

import java.io.Serializable;

public class Producto implements Serializable, Comparable<Producto> {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public int compareTo(Producto o) {
        return this.nombre.compareTo(o.nombre);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " ($" + precio + ") [Stock: " + stock + "]";
    }
}