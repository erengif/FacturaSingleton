package TEIS.dao.mysql;

import TEIS.dao.*;
import TEIS.modelo.TipoItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao implements DAOManager {
    private static MySqlDao mySqlDao = null;

    private Connection conn;
    private ClienteDao cliente = null;
    private FacturaDao factura = null;
    private ItemsDao item = null;
    private DetalleFacturaDao detalleFactura = null;
    private TipoItemDao tipoItem = null;

    private MySqlDao(String host ,String username, String password, String database) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://" + host + "/"+ database, username, password);

    }

    public static MySqlDao getInstance(String host ,String username, String password, String database) throws SQLException {
        if (mySqlDao == null){
            mySqlDao = new MySqlDao(host, username, password, database);
        }
        return mySqlDao;
    }

    public static MySqlDao getInstance() throws SQLException {
        if( mySqlDao == null )
            throw new NullPointerException("mySqlDao no ha sido creado");
        return mySqlDao;
    }

    @Override
    public ClienteDao getClienteDao() {
        if (cliente == null){
            cliente = new SqlClienteDao(conn);
        }
        return  cliente;
    }

    @Override
    public FacturaDao getFacturaDao() {
        if (factura == null){
            factura = new SqlFacturaDao(conn);
        }
        return factura;
    }

    @Override
    public ItemsDao getItemsDao() {
        if(item == null){
            item = new SqlItemsDao(conn);
        }
        return item;
    }

    @Override
    public TipoItemDao getTipoItemDao() {
        if ( tipoItem == null){
            tipoItem = new SqlTipoItemDao(conn);
        }
        return tipoItem;
    }

    @Override
    public DetalleFacturaDao getDetalleFacturaDao() {
        if ( detalleFactura == null){
            tipoItem = new SqlTipoItemDao(conn);
        }
        return detalleFactura;
    }
}
