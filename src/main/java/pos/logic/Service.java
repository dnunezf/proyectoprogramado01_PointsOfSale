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
}
