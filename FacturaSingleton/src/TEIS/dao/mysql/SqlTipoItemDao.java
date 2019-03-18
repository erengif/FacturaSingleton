package TEIS.dao.mysql;
import TEIS.dao.DaoException;
import  TEIS.dao.TipoItemDao;
import TEIS.modelo.TipoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlTipoItemDao implements  TipoItemDao{
    final String INSERTAR = "INSERT INTO items(descripcion) VALUES(?)";
    final String MODIFICAR = "UPDATE items SET descripcion = ? WHERE idTipoItem = ? VALUES(?)";
    final String ELIMINAR = "DELETE FROM tipoItem WHERE idTipoItem = ?";
    final String OBTENERTODOS = "SELECT * FROM tipoItem";
    final String OBTENER = "SELECT * FROM tipoItem WHERE idFactura= ?";

    Connection conn;

    public SqlTipoItemDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(TipoItem a) throws  DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTAR);
            stat.setString(1, a.getDescripcion());

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
    public void modificar(TipoItem a) throws DaoException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(MODIFICAR);
            stat.setString(1, a.getDescripcion());

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
    public void eliminar(TipoItem a) throws DaoException {
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
    public List<TipoItem> obtenerTodos() throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<TipoItem> tipoItems= new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs= stat.executeQuery();
            while(rs.next()){
                tipoItems.add(convertir(rs));
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
        return tipoItems;
    }

    @Override
    public TipoItem obtener(Integer id) throws DaoException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        TipoItem tipoItem= null;
        try{
            stat = conn.prepareStatement(OBTENER);
            stat.setInt(1, id);
            rs= stat.executeQuery();
            if(rs.next()){
                tipoItem= convertir(rs);

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
        return tipoItem;
    }

    private TipoItem convertir(ResultSet rs) throws  SQLException{
        String descripcion= rs.getString(", descripcion");
        TipoItem tipoItem= new TipoItem(descripcion);
        tipoItem.setId(rs.getInt("id"));
        return tipoItem;
    }

}
