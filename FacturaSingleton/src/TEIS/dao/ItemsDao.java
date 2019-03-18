package TEIS.dao;
import TEIS.modelo.Items;

import java.util.List;

public interface ItemsDao extends DAO<Items, Integer> {

    public void insertar(int idFactura ,Items a) throws DaoException;

}
