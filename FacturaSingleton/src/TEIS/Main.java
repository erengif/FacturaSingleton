package TEIS;
import TEIS.dao.ClienteDao;
import TEIS.dao.DaoException;
import TEIS.dao.FacturaDao;
import TEIS.dao.mysql.MySqlDao;
import TEIS.modelo.Cliente;
import TEIS.modelo.Factura;
import TEIS.modelo.Items;
import TEIS.modelo.TipoItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String args[]) throws SQLException, DaoException {

        MySqlDao mySqlDao = MySqlDao.getInstance("localhost:3306","root","x455l", "facturacion");

        try{

            Date date = new Date();


            ClienteDao clienteDao = mySqlDao.getClienteDao();
            FacturaDao facturaDao = mySqlDao.getFacturaDao();
            Cliente sean = clienteDao.obtener(8);

            Factura factura = new Factura(date, sean, 100000, "Pagado");
            TipoItem tipoItem = new TipoItem("Aseo");
            Items item = new Items(tipoItem, "Detergente", 100000);
            List<Items> items = new ArrayList<>();
            items.add(item);
            factura.setItems(items);
            facturaDao.insertar(factura);




            for(Cliente cliente1: clienteDao.obtenerTodos()){
                System.out.println(cliente1.getId());
            } ;
        } catch (DaoException e) {
            throw new DaoException("Usuario no ha podido ser agregado a la base de datos", e);
        }

    }
}
