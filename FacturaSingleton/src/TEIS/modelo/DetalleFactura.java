package TEIS.modelo;

public class DetalleFactura {
    private int id;
    private int id_cliente;
    private int id_item;

    public DetalleFactura(int id_cliente, int id_item) {
        this.id_cliente = id_cliente;
        this.id_item = id_item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }
}
