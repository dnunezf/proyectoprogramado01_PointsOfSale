package pos.presentation.historico;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

import pos.Application;
import pos.logic.*;
import pos.presentation.historico.Model;
import pos.presentation.historico.View;

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;

    /*Inicializa el modelo con una lista de clientes obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Factura())); // Inicializa el modelo con todos los clientes

        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);
    }

    /*Metodo para buscar clientes que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la busqueda*/
    public void search(Cliente filter) throws Exception
    {
        model.setFilter(filter); // Establece el filtro


        model.setMode(Application.MODE_CREATE); //Establece modo de operacion en CREAR
        model.setCurrentBill(new Factura()); // Resetea el cliente actual
        model.setListBills(Service.getInstance().searchBillsByName(model.getFilter())); // Busca y actualiza la lista en el modelo
    }
}
