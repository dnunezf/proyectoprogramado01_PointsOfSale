//package pos.presentation.cajeros;
//import pos.Application;
//import pos.logic.Cajero;
//import pos.presentation.AbstractModel;
//
//import java.beans.PropertyChangeListener;
//import java.util.List;
//
///*Clase Model de Cajero, representa el modelo de datos para gestionar cajeros.
// * Notifica a los listeners sobre cambios en las propiedades del modelo, hereda de AbstractModel*/
//public class Model extends AbstractModel
//{
//    /*Cajero que se utiliza como filtro para la busqueda en la lista*/
//    Cajero filter;
//    /*Lista de cajeros que contiene los datos actuales*/
//    List<Cajero> list;
//    /*Cajero actualmente seleccionado o en edición*/
//    Cajero current;
//    /*Modo de operación del modelo, ya sea modificar, ingresar, eliminar...*/
//    int mode;
//
//    /*Constantes que representan los nombres de las propiedades que cambian*/
//    public static final String LIST = "list";
//    public static final String CURRENT = "current";
//    public static final String FILTER = "filter";
//
//    /*Añade un listener para recibir notificaciones cuando una propiedad cambia*/
//    @Override
//    public void addPropertyChangeListener(PropertyChangeListener listener)
//    {
//        super.addPropertyChangeListener(listener);
//        firePropertyChange(LIST);
//        firePropertyChange(CURRENT);
//        firePropertyChange(FILTER);
//    }
//
//    public Model() {}
//
//    /*Inicializa la vista con una lista de cajeros, un cajero actual vacío y un filtro vacío*/
//    public void init(List<Cajero> list)
//    {
//        this.list = list;
//        this.current = new Cajero();
//        this.filter = new Cajero();
//        this.mode = Application.MODE_CREATE;
//    }
//
//    /*GETTERS AND SETTERS
//     * En los setters notifica el cambio a los listeners registrados*/
//
//    public List<Cajero> getList()
//    {
//        return list;
//    }
//
//    public void setList(List<Cajero> list)
//    {
//        this.list = list;
//        firePropertyChange(LIST);
//    }
//
//    public Cajero getCurrent()
//    {
//        return current;
//    }
//
//    public void setCurrent(Cajero current)
//    {
//        this.current = current;
//        firePropertyChange(CURRENT);
//    }
//
//    public Cajero getFilter()
//    {
//        return filter;
//    }
//
//    public void setFilter(Cajero filter)
//    {
//        this.filter = filter;
//        firePropertyChange(FILTER);
//    }
//
//    public int getMode()
//    {
//        return mode;
//    }
//
//    public void setMode(int mode)
//    {
//        this.mode = mode;
//    }
//}
//
