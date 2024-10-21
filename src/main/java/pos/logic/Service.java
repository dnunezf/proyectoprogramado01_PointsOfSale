package pos.logic;

import pos.data.*;

import java.util.List;

/*Clase Service, implementa operaciones CRUD+S. Patron singleton*/

public class Service {
    /* SINGLETON APPLY */

    private static Service instance = null;

    private CategoriaDao categoriaDao;
    private CajeroDao cajeroDao;
    private ProductoDao productoDao;
    private ClienteDao clienteDao;
    private FacturaDao facturaDao;
    private LineaDao lineaDao;

    /*Constructor privado*/
    private Service() {
        try {
            categoriaDao = new CategoriaDao();
            productoDao = new ProductoDao();
            clienteDao = new ClienteDao();
            facturaDao = new FacturaDao();
            lineaDao = new LineaDao();
            cajeroDao = new CajeroDao();
        } catch (Exception e) {
        }
    }

    /*Obtiene instancia unica*/
    public static Service getInstance() {
        if (instance == null) {
            // PERMITE QUE EL BLOQUE NO SEA EJECUTADO AL MISMO TIEMPO
            // POR VARIOS PROCESOS
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Service();
                }
            }
        }
        return instance;
    }

    public void stop() {}

    //CRUDS BASADOS EN DAO's

    //================= CLIENTES ============
    public void create(Cliente e) throws Exception {
        clienteDao.create(e);
    }

    public Cliente read(Cliente e) throws Exception {
        return clienteDao.read(e.getId());
    }

    public void update(Cliente e) throws Exception {
        clienteDao.update(e);
    }

    public void delete(Cliente e) throws Exception {
        clienteDao.delete(e);
    }

    public List<Cliente> search(Cliente e) {
        try {
            return clienteDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //================ CAJEROS ==============

    public void create(Cajero e) throws Exception {
        cajeroDao.create(e);
    }

    public Cajero read(Cajero e) throws Exception {
        return cajeroDao.read(e.getId());
    }

    public void update(Cajero e) throws Exception {
        cajeroDao.update(e);
    }

    public void delete(Cajero e) throws Exception {
        cajeroDao.delete(e);
    }

    public List<Cajero> search(Cajero e)
    {
        try {
            return cajeroDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //================= PRPODUCTOS ============
    public void create(Producto e) throws Exception {
        productoDao.create(e);
    }

    public Producto read(Producto e) throws Exception {
        return productoDao.read(e.getCodigo());
    }

    public void update(Producto e) throws Exception {
        productoDao.update(e);
    }

    public void delete(Producto e) throws Exception {
        productoDao.delete(e);
    }

    public List<Producto> search(Producto e) {
        try {
            return productoDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //================= CATEGORIAS ============
    public List<Categoria> search(Categoria e) {
        try {
            return categoriaDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ================= FACTURAS ============
    public void create(Factura e) throws Exception {
        facturaDao.create(e);
    }

    public Factura read(Factura e) throws Exception {
        return facturaDao.read(e.getNumeroDeFactura());
    }

    public void update(Factura e) throws Exception {
        facturaDao.update(e);
    }

    public void delete(Factura e) throws Exception {
        facturaDao.delete(e);
    }

    public List<Factura> search(Factura e) {
        try {
            return facturaDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    // ================= LINEAS ============
    public void create(Linea e) throws Exception {
        lineaDao.create(e);
    }

    public Linea read(Linea e) throws Exception {
        return lineaDao.read(e.getNumeroDeLinea());
    }

    public void update(Linea e) throws Exception {
        lineaDao.update(e);
    }

    public void delete(Linea e) throws Exception {
        lineaDao.delete(e);
    }

    public List<Linea> search(Linea e) {
        try {
            return lineaDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Categoria read(Categoria categoria) throws Exception {
        return categoriaDao.read(categoria.getId());
    }

    public void delete(Categoria e) throws Exception {
        categoriaDao.delete(e);
    }

    public List<Factura> searchBillsByClientName(String nombreCliente) throws Exception {
        ClienteDao clienteDao = new ClienteDao();
        FacturaDao facturaDao = new FacturaDao();

        // Buscar clientes que coincidan con el nombre
        Cliente filtro = new Cliente();
        filtro.setNombre(nombreCliente);
        List<Cliente> clientes = clienteDao.search(filtro);

        // Si se encuentra al menos un cliente
        if (!clientes.isEmpty()) {
            // Usar el primer cliente que coincida (puedes ajustar la lógica si es necesario)
            Cliente cliente = clientes.get(0);
            Factura filtroFactura = new Factura();
            filtroFactura.setCliente(cliente); // Asignar el cliente como filtro
            return facturaDao.search(filtroFactura); // Ahora filtramos facturas por cliente
        } else {
            throw new Exception("No se encontró ningún cliente con el nombre especificado.");
        }
    }

    public List<Linea> searchLinesByBill(String numeroDeFactura) throws Exception {
        // Leer la factura usando el número de factura proporcionado
        FacturaDao facturaDao = new FacturaDao();
        Factura factura = facturaDao.read(numeroDeFactura);

        // Verificar si la factura fue encontrada
        if (factura != null) {
            // Retornar las líneas asociadas a esa factura
            return factura.getLineas();
        } else {
            // Lanzar una excepción si no se encuentra la factura
            throw new Exception("No se encontró ninguna factura con el número especificado.");
        }
    }
}
