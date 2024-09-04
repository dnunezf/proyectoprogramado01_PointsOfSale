package pos.presentation.facturar;

import pos.Application;
import pos.logic.Cliente;
import pos.logic.Factura;
import pos.logic.Service;
import pos.presentation.facturar.Model;
import pos.presentation.facturar.View;


public class Controller {

    /*Referencias a la vista y modelo*/
    View view;
    Model model;

    /*Inicializa el modelo con una lista de cajeros obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Factura())); // Inicializa el modelo con todas las Facturas

        this.view = view;
        this.model = model;

        this.view.setController(this);
        view.setModel(model);
    }




}
