package pos.presentation.historico;

import pos.Application;
import pos.logic.*;
import pos.presentation.AbstractModel;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/*Clase Model de Historico, representa el modelo de datos para gestionar datos de factura.
 * Notifica a los listeners sobre cambios en las propiedades del modelo, hereda de AbstractModel*/

public class Model extends AbstractModel
{
    /*Cliente que se utiliza como filtro para la busqueda en la lista*/
    Cliente filter;
    /*Lista de facturas que contiene los datos actuales*/
    List<Factura> listBills;
    /*Factura actualmente seleccionado o en edicion*/
    Factura currentBill;
    /*Lista de lineas que contienen los datos actuales*/
    List<Linea> listLines;
    /*Modo de operacion del modelo, ya sea modificar, ingresar, eliminar...*/
    int mode;

    /*Constantes que representan los nombres de las propiedades que cambian*/
    public static final String LIST_BILLS = "listBills";
    public static final String CURRENT_BILL = "currentBill";
    public static final String FILTER = "filter";
    public static final String LIST_LINES = "listLines";

    /*Añade un listener para recibir notificaciones cuando una propiedad cambia*/
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST_BILLS);
        firePropertyChange(CURRENT_BILL);
        firePropertyChange(FILTER);
        firePropertyChange(LIST_LINES);
    }

    public Model()
    {

    }

    /*Inicializa la vista con una lista de facturas, lista de lineas, factura actual vacio y un filtro vacio*/
    public void init(List<Factura> listBills)
    {
        if (listBills == null || listBills.isEmpty()) {
            // Mostrar mensaje de información si no hay facturas disponibles
            JOptionPane.showMessageDialog(null,
                    "No hay facturas disponibles.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            this.currentBill = new Factura();  // Factura vacía por defecto
        } else {
            // Mostrar mensaje con el número de facturas cargadas
            JOptionPane.showMessageDialog(null,
                    "Facturas cargadas: " + listBills.size(),
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            this.currentBill = listBills.get(0);  // Usar la primera factura
        }

        this.listBills = listBills;
        this.listLines = new ArrayList<>();  // Inicializar lista de líneas vacía
        this.filter = new Cliente();         // Filtro inicial vacío
        this.mode = Application.MODE_CREATE; // Modo de operación
    }

    /*GETTERS AND SETTERS
     * En los setters notifica el cambio a los listeners registrados*/

    public List<Factura> getListBills()
    {
        return listBills;
    }

    public void setListBills(List<Factura> listBills)
    {
        this.listBills = listBills;
        firePropertyChange(LIST_BILLS);
    }

    public Factura getCurrentBill()
    {
        return currentBill;
    }

    public void setCurrentBill(Factura currentBill)
    {
        this.currentBill = currentBill;
        firePropertyChange(CURRENT_BILL);
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

    // Métodos GET y SET
    public List<Linea> getListLines() {
        return listLines;
    }

    public void setListLines(List<Linea> listLines) {
        this.listLines = listLines;
        firePropertyChange(LIST_LINES);
    }
}
