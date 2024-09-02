package pos.logic;

import java.lang.ref.Cleaner;
import java.util.ArrayList;
import pos.data.Data;
import pos.data.XmlPersister;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*Clase Service, implementa operaciones CRUD+S. Patron singleton*/

public class Service
{
    /* SINGLETON APPLY */

    private static Service instance = null;

    private Data data;

    /*Constructor privado. carga los datos desde un archivo XML, al inicializarse.
    * Si no se puede cargar, se inicializa una nueva instancia de Data*/
    private Service()
    {
        try
        {
            data = XmlPersister.getInstance().load();
        }
        catch (Exception e)
        {
            data = new Data();
        }
    }

    /*Obtiene instancia unica*/
    public static Service getInstance()
    {
        if (instance == null)
        {
            // PERMITE QUE EL BLOQUE NO SEA EJECUTADO AL MISMO TIEMPO
            // POR VARIOS PROCESOS
            synchronized (Service.class)
            {
                if (instance == null)
                {
                    instance = new Service();
                }
            }
        }
        return instance;
    }

    /*Detiene el servicio y almacena los datos en el archivo XML*/
    public void stop()
    {
        try
        {
            XmlPersister.getInstance().store(data);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /*Implementación CRUD+S lista de objetos Cliente*/

    /*Crea un nuevo Cliente en la lista de Clientes*/
    public void create(Cliente e) throws Exception
    {
        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);

        if (result == null)
        {
            data.getClientes().add(e);
        }
        else
        {
            throw new Exception("Cliente no existe");
        }
    }

    /*Lee un Cliente existente en la lista de Clientes*/
    public Cliente read(Cliente e) throws Exception
    {
        Cliente result = data.getClientes().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);

        if (result != null)
        {
            return result;
        }
        else
        {
            throw new Exception("Cliente no existe");
        }
    }

    /*Actualiza un Cliente existente en la lista de Clientes*/
    public void update(Cliente e) throws Exception
    {
        Cliente result;

        try
        {
            result = this.read(e);

            data.getClientes().remove(result);
            data.getClientes().add(e);
        }
        catch (Exception ex)
        {
            throw new Exception("Cliente no existe");
        }
    }

    /*Elimina un Cliente de la lista de Clientes*/
    public void delete(Cliente e) throws Exception
    {
        data.getClientes().remove(e);
    }

    /*Busca Clientes en la lista, cuyo nombre contiene una cadena especifica, y los
    * ordena alfabeticamente*/
    public List<Cliente> search(Cliente e)
    {
        return data.getClientes().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cliente::getNombre))
                .collect(Collectors.toList());
    }

    /*Implementación CRUD+S lista de objetos Cajero*/

    /*Crea un nuevo Cajero en la lista de Cajeros*/
    public void create(Cajero e) throws Exception
    {
        Cajero result = data.getCajeros().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);

        if (result == null)
        {
            data.getCajeros().add(e);
        }
        else
        {
            throw new Exception("Cajero no existe");
        }
    }

    /*Lee un Cajero existente en la lista de Cajeros*/
    public Cajero read(Cajero e) throws Exception
    {
        Cajero result = data.getCajeros().stream().filter(i->i.getId().equals(e.getId())).findFirst().orElse(null);

        if (result != null)
        {
            return result;
        }
        else
        {
            throw new Exception("Cajero no existe");
        }
    }

    /*Actualiza un Cajero existente en la lista de Cajeros*/
    public void update(Cajero e) throws Exception
    {
        Cajero result;

        try
        {
            result = this.read(e);

            data.getCajeros().remove(result);
            data.getCajeros().add(e);
        }
        catch (Exception ex)
        {
            throw new Exception("Cajero no existe");
        }
    }

    /*Elimina un Cajero de la lista de Cajeros*/
    public void delete(Cajero e) throws Exception
    {
        data.getCajeros().remove(e);
    }

    /*Busca Cajeros en la lista, cuyo nombre contiene una cadena especifica, y los
     * ordena alfabeticamente*/
    public List<Cajero> search(Cajero e)
    {
        return data.getCajeros().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cajero::getNombre))
                .collect(Collectors.toList());
    }

    /* Implementación CRUD+S lista de objetos Producto */

    /* Crea un nuevo Producto en la lista de Productos */
    public void create(Producto producto) throws Exception {
        Producto result = data.getProductos().stream()
                .filter(i -> i.getCodigo().equals(producto.getCodigo()))
                .findFirst()
                .orElse(null);

        if (result == null) {
            data.getProductos().add(producto);
        } else {
            throw new Exception("Producto ya existe");
        }
    }

    /* Lee un Producto existente en la lista de Productos */
    public Producto read(Producto producto) throws Exception {
        Producto result = data.getProductos().stream()
                .filter(i -> i.getCodigo().equals(producto.getCodigo()))
                .findFirst()
                .orElse(null);

        if (result != null) {
            return result;
        } else {
            throw new Exception("Producto no existe");
        }
    }

    /* Actualiza un Producto existente en la lista de Productos */
    public void update(Producto producto) throws Exception {
        Producto result;

        try {
            result = this.read(producto);

            data.getProductos().remove(result);
            data.getProductos().add(producto);
        } catch (Exception ex) {
            throw new Exception("Producto no existe");
        }
    }

    /* Elimina un Producto de la lista de Productos */
    public void delete(Producto producto) throws Exception {
        Producto result = this.read(producto);
        data.getProductos().remove(result);
    }

    /* Busca Productos en la lista, cuyo nombre contiene una cadena específica,
     * y los ordena alfabéticamente */
    public List<Producto> search(Producto producto) {
        return data.getProductos().stream()
                .filter(i -> i.getDescripcion().contains(producto.getDescripcion()))
                .sorted(Comparator.comparing(Producto::getDescripcion))
                .collect(Collectors.toList());
    }

}
