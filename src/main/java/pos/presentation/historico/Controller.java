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

    public Controller(View view, Model model) {
        if (model == null || view == null) {
            throw new IllegalArgumentException("El modelo y la vista no pueden ser nulos.");
        }

        this.model = model;
        this.view = view;

        // Inicializar el modelo con datos
        try {
            List<Factura> facturas = Service.getInstance().search(new Factura());
            model.init(facturas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al inicializar el modelo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        view.setModel(model);
        view.setController(this);
    }

    public void search(Cliente filter) throws Exception {
        model.setFilter(filter); // Establece el filtro
        List<Factura> facturas = Service.getInstance().searchBillsByClientName(filter.getNombre()); // Asegúrate de que estás usando el nombre del cliente
        model.setListBills(facturas);

        if (!facturas.isEmpty()) {
            loadLinesForSelectedBill(facturas.get(0));
        }

        model.setMode(Application.MODE_CREATE);
        model.setCurrentBill(new Factura());
    }

    public void loadLinesForSelectedBill(Factura factura) throws Exception {
        // Obtener las líneas de la factura seleccionada
        if (factura != null) {
            List<Linea> lineas = factura.getLineas();
            model.setCurrentBill(factura); // Actualizar la factura actual en el modelo
            if (lineas != null) {
                model.setListLines(lineas); // Actualizar las líneas de la factura en el modelo
            }
        }
    }

    public void addFactura(Factura factura) {
        List<Factura> facturas = model.getListBills();
        facturas.add(factura);
        model.setListBills(facturas);
    }

    public Model getModel() {
        return model;
    }
}
