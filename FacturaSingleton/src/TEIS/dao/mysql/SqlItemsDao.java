package TEIS.dao.mysql;
import TEIS.dao.DaoException;
import TEIS.dao.DetalleFacturaDao;
import  TEIS.dao.ItemsDao;
import TEIS.dao.TipoItemDao;
import TEIS.modelo.Items;
import TEIS.modelo.TipoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlItemsDao implements ItemsDao {
    final String INSERTAR = "INSERT INTO items(descripcion, valorUnidad) VALUES( ?, ?)";
    final String MODIFICAR = "UPDATE items SET id_tipoProducto = ?, descripcion = ?, valorUnidad = ? WHERE id_items = ? VALUES( ?, ?, ?)";
    final String ELIMINAR = "DELETE FROM items WHERE id_items = ?";
    final String OBTENERTODOS = "SELECT * FROM items";
    final String OBTENER = "SELECT * FROM items WHERE id_items= ?";

    Connection conn;

    MySqlDao mySqlDao;

    public SqlItemsDao(Connection conn) {
        this.conn = conn;
    }



    @Override
    public void insertar(Items a) throws DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTAR);
            stat.setString(1, a.getDescripcion());
            stat.setDouble(2, a.getValorUnidad());

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

    public void insertar(int idFactura ,Items a) throws DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTAR);
            stat.setString(2, a.getDescripcion());
            stat.setDouble(3, a.getValorUnidad());

            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se guardó");
            }
            a.setId(stat.getGeneratedKeys().getInt(1));
            MySqlDao mySqlDao = MySqlDao.getInstance();
            DetalleFacturaDao detalleFacturaDao = mySqlDao.getDetalleFacturaDao();
            detalleFacturaDao.insertar(idFactura, a.getId());


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
    public void modificar(Items a) throws DaoException{
        PreparedStatement stat = null;
        try {

            stat = conn.prepareStatement(MODIFICAR);
            stat.setInt(1, a.getTipoItem().getId());
            stat.setString(2, a.getDescripcion());
            stat.setDouble(3, a.getValorUnidad());

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
    public void eliminar(Items a) throws  DaoException{
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
    public List<Items> obtenerTodos() throws DaoException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Items> items= new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs= stat.executeQuery();
            while(rs.next()){
                items.add(convertir(rs));
            }

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
        return items;
    }

    @Override
    public Items obtener(Integer id) throws  DaoException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        Items items= null;
        try{
            stat = conn.prepareStatement(OBTENER);
            stat.setInt(1, id);
            rs= stat.executeQuery();
            if(rs.next()){
                items= convertir(rs);

            }else{
                throw new DaoException("No se ha encontrado ningún registro");
            }

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
        return items;
    }

    private Items convertir(ResultSet rs) throws SQLException, DaoException {

        mySqlDao = MySqlDao.getInstance();
        TipoItemDao tipoItemDao = mySqlDao.getTipoItemDao();
        int id_tipoItem= rs.getInt("id_tipoItem");
        String descripcion = rs.getString("descripcion");
        double valorUnidad= rs.getDouble("valorUnidad");
        TipoItem tipoItem = tipoItemDao.obtener(id_tipoItem);
        Items item= new Items(tipoItem, descripcion, valorUnidad);
        item.setId(rs.getInt("id"));
        return item;
    }
}
