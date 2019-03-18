package TEIS.dao;

import TEIS.modelo.TipoItem;

public interface DAOManager {

    ClienteDao getClienteDao();

    FacturaDao getFacturaDao();

    ItemsDao getItemsDao();

    TipoItemDao getTipoItemDao();

    DetalleFacturaDao getDetalleFacturaDao();

}