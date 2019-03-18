package TEIS.modelo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
    private int id;
    private Date fechaFactura;
    private Cliente cliente;
    private double totalFactura;
    private String estado;
    private List<Items> items;

    public Factura(Date fechaFactura, Cliente cliente, double totalFactura, String estado) {
        this.fechaFactura = fechaFactura;
        this.cliente = cliente;
        this.totalFactura = totalFactura;
        this.estado = estado;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
