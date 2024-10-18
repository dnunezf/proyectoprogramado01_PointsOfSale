package pos.presentation.historico;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

import pos.Application;
import pos.logic.*;
import pos.presentation.historico.Model;
import pos.presentation.historico.View;

import javax.swing.*;
import java.util.List;

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;


    /*Inicializa el modelo con una lista de facturas y líneas obtenidas de Servicio*/
    public Controller(View view, Model model)
    {
        if (model == null || view == null) {
            throw new IllegalArgumentException("El modelo y la vista no pueden ser nulos.");
        }

        this.model = model;
        this.view = view;

        // Inicializar el modelo con datos
        try {
            model.init(Service.getInstance().search(new Factura()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al inicializar el modelo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Enlazar la vista con el modelo y el controlador

        view.setModel(model);
        view.setController(this);

        this.model = model;
        this.view = view;
    }

    /*Método para buscar clientes que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la búsqueda*/
    public void search(Cliente filter) throws Exception
    {
        model.setFilter(filter); // Establece el filtro

//        List<Factura> facturas = Service.getInstance().searchBillsByName(filter);
//        model.setListBills(facturas);
//
//        List<Linea> lineas = Service.getInstance().searchLinesByName(filter);
//        model.setListLines(lineas);

        model.setMode(Application.MODE_CREATE); // Establece modo de operación en CREAR
        model.setCurrentBill(new Factura()); // Resetea factura actual
    }

    public void loadLinesForSelectedBill(Factura factura) throws Exception {
        // Obtener las líneas de la factura seleccionada
        List<Linea> lineas = factura.getLineas();

        // Actualizar el modelo con las líneas obtenidas
        model.setCurrentBill(factura); // Actualizar la factura actual en el modelo
        model.setListLines(lineas); // Actualizar las líneas de la factura en el modelo
    }

    public void addFactura(Factura factura)
    {
        List<Factura> facturas = model.getListBills();
        facturas.add(factura);
        model.setListBills(facturas);
    }

    public Model getModel() {

        return model;
    }
}
