package TEIS.dao.mysql;

import TEIS.dao.DaoException;
import TEIS.dao.DetalleFacturaDao;
import TEIS.dao.ItemsDao;
import TEIS.modelo.Cliente;
import TEIS.modelo.DetalleFactura;
import TEIS.modelo.Factura;
import TEIS.modelo.Items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlDetalleFacturaDao implements DetalleFacturaDao {
    final String INSERTAR = "INSERT INTO detalleFactura(id_factura, id_item) VALUES(?, ?)";
    final String MODIFICAR = "UPDATE detalleFactura SET id_factura = ?, id_item = ? VALUES( ?, ?)";
    final String ELIMINAR = "DELETE FROM detalleFactura WHERE id_factura= ?";
    final String OBTENERTODOS = "SELECT * FROM detalleFactura";
    final String OBTENER = "SELECT * FROM detalleFactura WHERE id_detalleFactura= ?";

    Connection conn = null;
    MySqlDao mySqlDao;


    public void insertar(int id_factura, int id_item) throws DaoException {
        PreparedStatement stat = null;
        try{
            stat.setInt(1, id_factura);
            stat.setInt(1, id_item );

            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se guardó");
            }
        }catch ( SQLException e){
            throw new DaoException("Error en SQL", e);
        }
        finally {
            if(stat != null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DaoException("Error en SQL", e);
                }
            }

        }

    }


    @Override
    public void insertar(Factura a) throws DaoException {

    }

    @Override
    public void modificar(Factura a) throws DaoException {
        eliminar(a);
        for (Items item: a.getItems()){
            insertar(a.getId(), item.getId());
        }
    }

    @Override
    public void eliminar(Factura a) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(ELIMINAR);
            stat.setInt(1, a.getId());
            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se guardó");
            }
        } catch ( SQLException e){
            throw new DaoException("Error en SQL", e);
        }
        finally {
            if(stat != null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DaoException("Error en SQL", e);
                }
            }

        }

    }

    @Override
    public List<Factura> obtenerTodos() throws DaoException {
        return null;
    }

    @Override
    public Factura obtener(Integer id) throws DaoException {
        return null;
    }


    public List<Items> obtenerItems(Integer id) throws DaoException {

        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Items> items= null;
        try{
            stat = conn.prepareStatement(OBTENER);
            stat.setInt(1, id);
            rs= stat.executeQuery();
            items = convertir(rs);
            return items;

        } catch (SQLException e){
            throw new DaoException("Error en SQL", e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                }catch (SQLException e){
                    throw new DaoException("Error en SQL");
                }
            }
            if(stat != null){
                try {
                    stat.close();
                }catch (SQLException e){
                    throw new DaoException("Error en SQL");
                }
            }
        }
    }

    private List<Items> convertir(ResultSet rs) throws SQLException, DaoException {
        int idItems;
        List<Items> items = new ArrayList<>();
        mySqlDao = MySqlDao.getInstance();
        ItemsDao itemsDao = mySqlDao.getItemsDao();

        do {
            idItems = rs.getInt(", id_items");
            Items item = itemsDao.obtener(idItems);
            items.add(item);
        }while (rs.next());
        return  items;
    }
}