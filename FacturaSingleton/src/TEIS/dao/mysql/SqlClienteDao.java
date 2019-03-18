package TEIS.dao.mysql;

import java.sql.*;

import TEIS.dao.ClienteDao;
import TEIS.dao.DaoException;
import TEIS.modelo.Cliente;
import TEIS.dao.DaoException;

import java.util.ArrayList;
import java.util.List;

public class SqlClienteDao implements ClienteDao{
    final String INSERTAR = "INSERT INTO cliente(nombre, genero, fechaNacimiento, estadoCivil) VALUES( ?, ?, ?, ?)";
    final String MODIFICAR = "UPDATE cliente SET nombre = ? , genero = ?, fechaNacimiento = ?, estadoCivil = ? WHERE id_cliente = ?";
    final String ELIMINAR = "DELETE FROM cliente WHERE id_cliente = ? ";
    final String OBTENERTODOS = "SELECT * FROM cliente";
    final String OBTENER = "SELECT * FROM cliente WHERE id_cliente = ? ";

    Connection conn;

    public SqlClienteDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Cliente a) throws  DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTAR, stat.RETURN_GENERATED_KEYS);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getGenero());
            stat.setDate(3, new Date(a.getFechaNacimiento().getTime()));
            stat.setString(4, a.getEstadoCivil());

            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se guardó");
            }
            ResultSet rs = stat.getGeneratedKeys();
            rs.next();
            int id_cliente = rs.getInt(1);
            a.setId(id_cliente);
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
    public void modificar(Cliente a) throws DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(MODIFICAR);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getGenero());
            stat.setDate(3, new Date(a.getFechaNacimiento().getTime()));
            stat.setString(4, a.getEstadoCivil());
            stat.setInt(5, a.getId());

            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se modificó");
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
    public void eliminar(Cliente a) throws DaoException {
        throw new DaoException("Metodo no implementado");
    }

    @Override
    public void eliminar(int id_cliente) throws DaoException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(ELIMINAR);
            stat.setInt(1, id_cliente);
            if (stat.executeUpdate() == 0){
                throw new DaoException("Posiblemente no se eliminó");
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
    public List<Cliente> obtenerTodos() throws DaoException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs= stat.executeQuery();
            while(rs.next()){
                 clientes.add(convertir(rs));
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
        return clientes;
    }

    @Override
    public Cliente obtener(Integer id) throws DaoException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try{
            stat = conn.prepareStatement(OBTENER);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if(rs.next()){
                cliente = convertir(rs);

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
        return cliente;
    }

    private Cliente convertir(ResultSet rs) throws  SQLException{
         String nombre = rs.getString("nombre");
         String genero= rs.getString("genero");
         Date fechaNacimiento = rs.getDate("fechaNacimiento");
         String estadoCivil = rs.getString("estadoCivil");
         Cliente cliente = new Cliente(nombre, genero, fechaNacimiento, estadoCivil);
         cliente.setId(rs.getInt("id_cliente"));
         return  cliente;
    }

}
