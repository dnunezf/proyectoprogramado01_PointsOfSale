package pos.presentation.facturar;

import pos.Application;
import pos.logic.Cajero;
import pos.logic.Factura;
import pos.logic.Producto;
import pos.logic.Linea;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

/*Clase Model de Factura, representa el modelo de datos para gestionar clientes.
 * Notifica a los listeners sobre cambios en las propiedades del modelo, hereda de AbstractModel*/

public class Model extends AbstractModel {

    /*Producto que se utiliza como filtro para la busqueda en la lista para ingresarlo a la factura*/
    Producto filter;
    /*Lista de Lineas que contiene los datos actuales*/
    List<Linea> list;
    /*Linea actualmente seleccionado o en edicion*/
    Linea current;
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

    /*Inicializa la vista con una lista de Facturas, un cajero actual vacio y un filtro vacio*/
    public void init(List<Linea> list) {
        this.list = list;
        this.current = new Linea();
        this.filter = new Producto();
        this.mode = Application.MODE_CREATE;

    }

    /*GETTERS AND SETTERS
     * En los setters notifica el cambio a los listeners registrados*/

    public List<Linea> getList()
    {
        return list;
    }

    public void setList(List<Linea> list) {
        this.list = list;
        firePropertyChange(LIST, null, list);  // Ensure the listeners are properly notified
    }


    public Linea getCurrent()
    {
        return current;
    }

    public void setCurrent(Linea current) {
        Linea oldCurrent = this.current;
        this.current = current;
        firePropertyChange(CURRENT, oldCurrent, current);
    }


    public Producto getFilter()
    {
        return filter;
    }

    public void setFilter(Producto filter) {
        Producto oldFilter = this.filter;
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
}

