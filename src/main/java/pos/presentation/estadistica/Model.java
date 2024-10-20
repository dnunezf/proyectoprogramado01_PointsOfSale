package pos.presentation.estadistica;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Factura;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

public class Model extends AbstractModel
{
    /*Cliente que se utiliza como filtro para la busqueda en la lista*/
    Categoria filter;
    /*Lista de clientes que contiene los datos actuales*/
    List<Categoria> list;
    /*Cliente actualmente seleccionado o en edicion*/
    Categoria current;
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
    public void init(List<Categoria> list)
    {
        this.list = list;
        this.current = new Categoria();
        this.filter = new Categoria();
        this.mode = Application.MODE_CREATE;

    }

    /*GETTERS AND SETTERS
     * En los setters notifica el cambio a los listeners registrados*/

    public List<Categoria> getList()
    {
        return list;
    }



    public Categoria getCurrent()
    {
        return current;
    }



    public Categoria getFilter()
    {
        return filter;
    }

    public void setList(List<Categoria> list) {
        List<Categoria> oldList = this.list;
        this.list = list;
        firePropertyChange(LIST, oldList, list);
    }

    public void setCurrent(Categoria current) {
        Categoria oldCurrent = this.current;
        this.current = current;
        firePropertyChange(CURRENT, oldCurrent, current);
    }

    public void setFilter(Categoria filter) {
        Categoria oldFilter = this.filter;
        this.filter = filter;
        firePropertyChange(FILTER, oldFilter, filter);
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }

    public void addElement(Categoria categoria) {

        this.list.add(categoria);

    }
}
