package pos.presentation.productos;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Producto;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

/*Clase Model de Producto, representa el modelo de datos para gestionar productos.
 * Notifica a los listeners sobre cambios en las propiedades del modelo, hereda de AbstractModel*/

public class Model extends AbstractModel
{
    /*Producto que se utiliza como filtro para la búsqueda en la lista*/
    Producto filter;
    /*Lista de productos que contiene los datos actuales*/
    List<Producto> list;
    /*Producto actualmente seleccionado o en edición*/
    Producto current;
    List<Categoria> categorias;
    /*Modo de operación del modelo, ya sea modificar, ingresar, eliminar...*/
    int mode;

    /*Constantes que representan los nombres de las propiedades que cambian*/
    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";
    public static final String CATEGORIAS="categorias";

    /*Añade un listener para recibir notificaciones cuando una propiedad cambia*/
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(CATEGORIAS);
    }

    public Model()
    {

    }

    /*Inicializa la vista con una lista de productos, un producto actual vacío y un filtro vacío*/
    public void init(List<Producto> list)
    {
        this.list = list;
        this.current = new Producto();
        this.filter = new Producto();
        this.mode = Application.MODE_CREATE;
    }

    /*GETTERS AND SETTERS
     * En los setters notifica el cambio a los listeners registrados*/

    public List<Producto> getList()
    {
        return list;
    }

    public void setList(List<Producto> list)
    {
        this.list = list;
        firePropertyChange(LIST);
    }

    public Producto getCurrent()
    {
        return current;
    }

    public void setCurrent(Producto current)
    {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Producto getFilter()
    {
        return filter;
    }

    public void setFilter(Producto filter)
    {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public List<Categoria> getCategorias()
    {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        firePropertyChange(CATEGORIAS);
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
