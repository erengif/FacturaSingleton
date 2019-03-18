package TEIS.dao;

import java.util.List;
import TEIS.modelo.Cliente;

public interface ClienteDao extends DAO<Cliente, Integer> {
    public void eliminar(int id_cliente) throws  DaoException;
}
