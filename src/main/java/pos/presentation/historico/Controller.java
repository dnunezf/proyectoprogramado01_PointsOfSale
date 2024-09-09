package pos.presentation.historico;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

import pos.Application;
import pos.logic.*;
import pos.presentation.historico.Model;
import pos.presentation.historico.View;

import java.util.List;

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;

    /*Inicializa el modelo con una lista de facturas y líneas obtenidas de Servicio*/
    public Controller(View view, Model model)
    {
        // Inicializa el modelo con listas vacías al principio
        model.init(Service.getInstance().search(new Factura())); // Inicializa con una lista de facturas vacía

        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);
    }

    /*Método para buscar clientes que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la búsqueda*/
    public void search(Cliente filter) throws Exception
    {
        model.setFilter(filter); // Establece el filtro

        // Obtiene facturas y líneas basadas en el cliente
        List<Factura> facturas = Service.getInstance().searchBillsByName(filter);
        List<Linea> lineas = Service.getInstance().searchLinesByName(filter);

        model.setMode(Application.MODE_CREATE); // Establece modo de operación en CREAR
        model.setCurrentBill(new Factura()); // Resetea factura actual
        model.setListBills(facturas); // Actualiza la lista de facturas en el modelo
        model.setListLines(lineas); // Actualiza la lista de líneas en el modelo
    }
}
