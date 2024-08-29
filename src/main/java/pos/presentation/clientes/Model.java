package pos.presentation.clientes;

import pos.logic.Cliente;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

/*Clase Model de Cliente, representa el modelo de datos para gestionar clientes.
* Notifica a los listeners sobre cambios en las propiedades del modelo, hereda de AbstractModel*/

public class Model extends AbstractModel
{
    /*Cliente que se utiliza como filtro para la busqueda en la lista*/
    Cliente filter;
    /*Lista de clientes que contiene los datos actuales*/
    List<Cliente> list;
    /*Cliente actualmente seleccionado o en edicion*/
    Cliente current;
    /*Modo de operacion del modelo, ya sea modificar, ingresar, eliminar...*/
    int mode;

    /*Constantes que representan los nombres de las propiedades que cambian*/
    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";

    /*Añade un listener para recibir notificaciones cuando una propiedad cambia*/
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model()
    {

    }

    /*Inicializa la vista con una lista de clientes, un cliente actual vacio y un filtro vacio*/
    public void init(List<Cliente> list)
    {
        this.list = list;
        this.current = new Cliente();
        this.filter = new Cliente();
        this.mode = Application.MODE_CREATE;
    }

    /*GETTERS AND SETTERS
    * En los setters notifica el cambio a los listeners registrados*/

    public List<Cliente> getList()
    {
        return list;
    }

    public void setList(List<Cliente> list)
    {
        this.list = list;
        firePropertyChange(LIST);
    }

    public Cliente getCurrent()
    {
        return current;
    }

    public void setCurrent(Cliente current)
    {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Cliente getFilter()
    {
        return filter;
    }

    public void setFilter(Cliente filter)
    {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }
}
