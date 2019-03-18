package TEIS.dao.mysql;
import TEIS.dao.*;
import TEIS.modelo.Cliente;
import TEIS.modelo.DetalleFactura;
import TEIS.modelo.Factura;
import TEIS.modelo.Items;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlFacturaDao implements  FacturaDao{
    final String INSERTAR = "INSERT INTO factura(fechaFactura, id_cliente, totalFactura, estado) VALUES( ?, ?, ?, ?)";
    final String MODIFICAR = "UPDATE factura SET fechaFactura = ?, idCliente = ?,totalFactura = ?, estado= ?  WHERE idFactura = ? VALUES( ?, ?, ?, ?, ?)";
    final String ELIMINAR = "DELETE FROM factura WHERE id_factura = ?";
    final String OBTENERTODOS = "SELECT * FROM factura";
    final String OBTENER = "SELECT * FROM factura WHERE id_factura= ?";

    Connection conn;

    MySqlDao mySqlDao;

    public SqlFacturaDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Factura a) throws  DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTAR);
            stat.setDate(1, new Date(a.getFechaFactura().getTime()));
            stat.setInt(2, a.getCliente().getId());
            stat.setDouble(3, a.getTotalFactura());
            stat.setString(4, a.getEstado());

            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se guardó");
            }
            a.setId(stat.getGeneratedKeys().getInt(1));
            MySqlDao mySqlDao = MySqlDao.getInstance();
            ItemsDao itemsDao = mySqlDao.getItemsDao();
            for(Items item : a.getItems()) {
                itemsDao.insertar(a.getId(), item);
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
    public void modificar(Factura a) throws  DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(MODIFICAR);
            stat.setDate(1, new Date(a.getFechaFactura().getTime()));
            stat.setInt(2, a.getCliente().getId());
            stat.setDouble(3, a.getTotalFactura());
            stat.setString(4, a.getEstado());
            stat.setInt(5, a.getId());

            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se guardó");
            }
            MySqlDao mySqlDao = MySqlDao.getInstance();
            ItemsDao itemsDao = mySqlDao.getItemsDao();
            for(Items item : a.getItems()) {
                itemsDao.insertar(item);
            }

            DetalleFacturaDao detalleFacturaDao = mySqlDao.getDetalleFacturaDao();
            detalleFacturaDao.modificar(a);

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
    public void eliminar(Factura a) throws DaoException{
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
    public List<Factura> obtenerTodos() throws  DaoException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Factura> facturas = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs = stat.executeQuery();
            while(rs.next()){
                facturas.add(convertir(rs));
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
        return facturas;
    }

    @Override
    public Factura obtener(Integer id) throws DaoException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        Factura factura = null;
        try{
            stat = conn.prepareStatement(OBTENER);
            stat.setInt(1, id);
            rs= stat.executeQuery();
            if(rs.next()){
                factura = convertir(rs);
                mySqlDao = MySqlDao.getInstance();
                DetalleFacturaDao detalleFacturaDao = mySqlDao.getDetalleFacturaDao();
                factura.setItems(detalleFacturaDao.obtenerItems(id));

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
        return factura;
    }

    private Factura convertir(ResultSet rs) throws SQLException, DaoException {
        Date fechaFactura = rs.getDate("fechaFactura");
        int idCliente = rs.getInt(", idCliente");
        double totalFactura = rs.getDouble("totalFactura");
        String estado = rs.getString("estado");
        mySqlDao = MySqlDao.getInstance();
        ClienteDao clienteDao = mySqlDao.getClienteDao();
        Cliente cliente = clienteDao.obtener(idCliente);
        Factura factura = new Factura(fechaFactura, cliente, totalFactura, estado);
        factura.setId(rs.getInt("id"));
        return  factura;
    }
}
