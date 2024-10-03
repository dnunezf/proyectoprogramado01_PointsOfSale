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

    /*Constructor privado. carga los datos desde un archivo XML, al inicializarse.
     * Si no se puede cargar, se inicializa una nueva instancia de Data*/
    private Service() {
        try {
            categoriaDao = new CategoriaDao();
            productoDao = new ProductoDao();
            clienteDao = new ClienteDao();
            facturaDao = new FacturaDao();
            lineaDao = new LineaDao();
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

    /*Detiene el servicio y almacena los datos en el archivo XML*/
    public void stop() {}

    //CRUDS BASADOS EN DAO's

    //================= PRODUCTOS ============
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

    public List<Cajero> search(Cajero e) {
        try {
            return cajeroDao.search(e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


//Cambiar todo lo que sigue a Dao





//
//    /*Implementación CRUD+S lista de objetos Cliente*/
//
//    /*Crea un nuevo Cliente en la lista de Clientes*/
//    public void create(Cliente e) throws Exception
//    {
//        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
//
//        if (result == null)
//        {
//            data.getClientes().add(e);
//        }
//        else
//        {
//            throw new Exception("Cliente no existe");
//        }
//    }
//
//    /*Lee un Cliente existente en la lista de Clientes*/
//    public Cliente read(Cliente e) throws Exception
//    {
//        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
//
//        if (result != null)
//        {
//            return result;
//        }
//        else
//        {
//            throw new Exception("Cliente no existe");
//        }
//    }
//
//    /*Actualiza un Cliente existente en la lista de Clientes*/
//    public void update(Cliente e) throws Exception
//    {
//        Cliente result;
//
//        try
//        {
//            result = this.read(e);
//
//            data.getClientes().remove(result);
//            data.getClientes().add(e);
//        }
//        catch (Exception ex)
//        {
//            throw new Exception("Cliente no existe");
//        }
//    }
//
//    /*Elimina un Cliente de la lista de Clientes*/
//    public void delete(Cliente e) throws Exception
//    {
//        data.getClientes().remove(e);
//    }
//
//    /*Busca Clientes en la lista, cuyo nombre contiene una cadena especifica, y los
//    * ordena alfabeticamente*/
//    public List<Cliente> search(Cliente e)
//    {
//        return data.getClientes().stream()
//                .filter(i->i.getNombre().contains(e.getNombre()))
//                .sorted(Comparator.comparing(Cliente::getNombre))
//                .collect(Collectors.toList());
//    }
//
//    /*Implementación CRUD+S lista de objetos Cajero*/
//
//    /*Crea un nuevo Cajero en la lista de Cajeros*/
//    public void create(Cajero e) throws Exception
//    {
//        Cajero result = data.getCajeros().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
//
//        if (result == null)
//        {
//            data.getCajeros().add(e);
//        }
//        else
//        {
//            throw new Exception("Cajero no existe");
//        }
//    }
//
//    /*Lee un Cajero existente en la lista de Cajeros*/
//    public Cajero read(Cajero e) throws Exception
//    {
//        Cajero result = data.getCajeros().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);
//
//        if (result != null)
//        {
//            return result;
//        }
//        else
//        {
//            throw new Exception("Cajero no existe");
//        }
//    }
//
//    /*Actualiza un Cajero existente en la lista de Cajeros*/
//    public void update(Cajero e) throws Exception
//    {
//        Cajero result;
//
//        try
//        {
//            result = this.read(e);
//
//            data.getCajeros().remove(result);
//            data.getCajeros().add(e);
//        }
//        catch (Exception ex)
//        {
//            throw new Exception("Cajero no existe");
//        }
//    }
//
//    /*Elimina un Cajero de la lista de Cajeros*/
//    public void delete(Cajero e) throws Exception
//    {
//        data.getCajeros().remove(e);
//    }
//
//    /*Busca Cajeros en la lista, cuyo nombre contiene una cadena especifica, y los
//     * ordena alfabeticamente*/
//    public List<Cajero> search(Cajero e)
//    {
//        return data.getCajeros().stream()
//                .filter(i->i.getNombre().contains(e.getNombre()))
//                .sorted(Comparator.comparing(Cajero::getNombre))
//                .collect(Collectors.toList());
//    }
//
//    /* Implementación CRUD+S lista de objetos Producto */
//
//    /* Crea un nuevo Producto en la lista de Productos */
//    public void create(Producto producto) throws Exception {
//        Producto result = data.getProductos().stream()
//                .filter(i -> i.getCodigo().equals(producto.getCodigo()))
//                .findFirst()
//                .orElse(null);
//
//        if (result == null) {
//            data.getProductos().add(producto);
//        } else {
//            throw new Exception("Producto ya existe");
//        }
//    }
//
//    /* Lee un Producto existente en la lista de Productos */
//    public Producto read(Producto producto) throws Exception {
//        Producto result = data.getProductos().stream()
//                .filter(i -> i.getCodigo().equals(producto.getCodigo()))
//                .findFirst()
//                .orElse(null);
//
//        if (result != null) {
//            return result;
//        } else {
//            throw new Exception("Producto no existe");
//        }
//    }
//
//    /* Actualiza un Producto existente en la lista de Productos */
//    public void update(Producto producto) throws Exception {
//        Producto result;
//
//        try {
//            result = this.read(producto);
//
//            data.getProductos().remove(result);
//            data.getProductos().add(producto);
//        } catch (Exception ex) {
//            throw new Exception("Producto no existe");
//        }
//    }
//
//    /* Elimina un Producto de la lista de Productos */
//    public void delete(Producto producto) throws Exception {
//        Producto result = this.read(producto);
//        data.getProductos().remove(result);
//    }
//
//    public Producto searchOne(Producto filter)
//    {
//        for (Producto producto : data.getProductos())
//        {
//            if(producto.getCodigo().equals(filter.getCodigo()))
//            {
//                return producto;
//            }
//        }
//
//        return null;
//    }
//
//    /* Busca Productos en la lista, cuyo nombre contiene una cadena específica,
//     * y los ordena alfabéticamente */
//    public List<Producto> search(Producto producto) {
//        return data.getProductos().stream()
//                .filter(i -> i.getDescripcion().contains(producto.getDescripcion()))
//                .sorted(Comparator.comparing(Producto::getDescripcion))
//                .collect(Collectors.toList());
//    }
//
//    /* Implementación CRUD+S lista de objetos Factura */
//
//    public void create(Factura factura) throws Exception {
//        Factura result = data.getFacturas().stream()
//                .filter(i -> i.getNumeroDeFactura().equals(factura.getNumeroDeFactura()))
//                .findFirst()
//                .orElse(null);
//
//        if (result == null) {
//            data.getFacturas().add(factura);
//        } else {
//            throw new Exception("Factura ya existe");
//        }
//    }
//
//    /* Lee una Factura existente en la lista de Facturas */
//    public Factura read(Factura factura) throws Exception {
//        Factura result = data.getFacturas().stream()
//                .filter(i -> i.getNumeroDeFactura().equals(factura.getNumeroDeFactura()))
//                .findFirst()
//                .orElse(null);
//
//        if (result != null) {
//            return result;
//        } else {
//            throw new Exception("Factura no existe");
//        }
//    }
//
//    /* Actualiza una Factura existente en la lista de Facturas */
//    public void update(Factura factura) throws Exception {
//        Factura result;
//
//        try {
//            result = this.read(factura);
//
//            data.getFacturas().remove(result);
//            data.getFacturas().add(factura);
//        } catch (Exception ex) {
//            throw new Exception("Factura no existe");
//        }
//    }
//
//    /* Elimina una Factura de la lista de Facturas */
//    public void delete(Factura factura) throws Exception {
//        Factura result = this.read(factura);
//        data.getFacturas().remove(result);
//    }
//
//    /* Busca Facturas en la lista, cuyo número de factura contiene una cadena específica,
//     * y las ordena por fecha */
//    public List<Factura> search(Factura factura) {
//        return data.getFacturas().stream()
//                .filter(i -> String.valueOf(i.getNumeroDeFactura()).contains(String.valueOf(factura.getNumeroDeFactura())))
//                .sorted(Comparator.comparing(Factura::getFecha))
//                .collect(Collectors.toList()).reversed();
//    }
//
//    /*Search Bills by Name*/
//    public List<Factura> searchBillsByName(Cliente cliente) {
//        return data.getFacturas().stream()
//                .filter(i -> i.getCliente().getNombre().toLowerCase().contains(cliente.getNombre().toLowerCase()))
//                .sorted(Comparator.comparing(Factura::getFecha))
//                .collect(Collectors.toList());
//    }
//
//    // Clase Service
//
//    // Crea una nueva Linea en la lista de Lineas
//    public void create(Linea linea) throws Exception {
//        Linea result = data.getLineas().stream()
//                .filter(i -> i.getNumeroDeLinea().equals(linea.getNumeroDeLinea()))
//                .findFirst()
//                .orElse(null);
//
//        if (result == null) {
//            data.getLineas().add(linea);
//        } else {
//            throw new Exception("Linea ya existe");
//        }
//    }
//
//    // Lee una Linea existente en la lista de Lineas
//    public Linea read(Linea linea) throws Exception {
//        Linea result = data.getLineas().stream()
//                .filter(i -> i.getNumeroDeLinea().equals(linea.getNumeroDeLinea()))
//                .findFirst()
//                .orElse(null);
//
//        if (result != null) {
//            return result;
//        } else {
//            throw new Exception("Linea no existe");
//        }
//    }
//
//    // Actualiza una Linea existente en la lista de Lineas
//    public void update(Linea linea) throws Exception {
//        Linea result;
//
//        try {
//            result = this.read(linea);
//
//            data.getLineas().remove(result);
//            data.getLineas().add(linea);
//        } catch (Exception ex) {
//            throw new Exception("Linea no existe");
//        }
//    }
//
//    // Elimina una Linea de la lista de Lineas
//    public void delete(Linea linea) throws Exception {
//        Linea result = this.read(linea);
//        data.getLineas().remove(result);
//    }
//
//    // Busca Lineas en la lista, cuyo número de línea contiene una cadena específica
//    public List<Linea> search(Linea linea) {
//        return data.getLineas().stream()
//                .filter(i -> i.getNumeroDeLinea().contains(linea.getNumeroDeLinea()))
//                .sorted(Comparator.comparing(Linea::getNumeroDeLinea))
//                .collect(Collectors.toList());
//    }
//
//    /*Search Lines by Name*/
//    public List<Linea> searchLinesByName(Cliente cliente) {
//        return data.getFacturas().stream()
//                .filter(f -> f.getCliente().getNombre().toLowerCase().contains(cliente.getNombre().toLowerCase())) // Filtra facturas por cliente
//                .flatMap(f -> f.getLineas().stream()) // Descompone cada factura en sus líneas de productos
//                .sorted(Comparator.comparing(Linea::getNumeroDeLinea)) // Ordena las líneas por número de línea
//                .collect(Collectors.toList());
//    }
//
//
//    public List<Categoria> search(Categoria categoria) {
//        return data.getCategorias().stream()
//                .filter(i -> i.getTipo().contains(categoria.getTipo()))
//                .sorted(Comparator.comparing(Categoria::getTipo))
//                .collect(Collectors.toList());
//    }
//
//    public void create(Categoria categoria) throws Exception{
//        Categoria result = data.getCategorias().stream()
//                .filter(i -> i.getTipo().equals(categoria.getTipo()))
//                .findFirst()
//                .orElse(null);
//
//        if (result == null) {
//            data.getCategorias().add(categoria);
//        } else {
//            throw new Exception("Factura ya existe");
//        }
//    }
//
//    public void update(Categoria categoria) throws Exception{
//        Categoria result;
//
//        try {
//            result = this.read(categoria);
//
//            data.getCategorias().remove(result);
//            data.getCategorias().add(categoria);
//        } catch (Exception ex) {
//            throw new Exception("Categoria no existe");
//        }
//    }
//
//    public Categoria read(Categoria categoria) throws Exception{
//        Categoria result = data.getCategorias().stream()
//                .filter(i -> i.getTipo().equals(categoria.getTipo()))
//                .findFirst()
//                .orElse(null);
//
//        if (result != null) {
//            return result;
//        } else {
//            throw new Exception("Categoria no existe");
//        }
//    }
//
//    public void delete(Categoria categoria) throws Exception {
//        Categoria result = this.read(categoria);
//        data.getCategorias().remove(result);
//    }
}
