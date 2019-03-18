package TEIS.dao;
import java.util.List;

public interface DAO<T, K>  {

    void insertar(T a) throws  DaoException;
    void modificar(T a) throws  DaoException;
    void eliminar(T a) throws  DaoException;
    List<T> obtenerTodos() throws  DaoException;
    T obtener(K id) throws DaoException;

}
