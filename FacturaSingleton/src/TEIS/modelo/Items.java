package TEIS.modelo;



public class Items {
    private int id;
    private TipoItem tipoItem;
    private String descripcion;
    private double valorUnidad;

    public Items(TipoItem tipoItem,String descripcion, double valorUnidad) {
        this.tipoItem = tipoItem;
        this.descripcion = descripcion;
        this.valorUnidad = valorUnidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValorUnidad() {
        return valorUnidad;
    }

    public void setValorUnidad(double valorUnidad) {
        this.valorUnidad = valorUnidad;
    }
}
