package TEIS.dao;

import TEIS.modelo.Factura;
import TEIS.modelo.Items;

import java.util.List;

public interface DetalleFacturaDao extends DAO<Factura, Integer> {

    public void insertar(int id_factura, int id_item) throws DaoException;

    public List<Items> obtenerItems(Integer id) throws DaoException;

}
