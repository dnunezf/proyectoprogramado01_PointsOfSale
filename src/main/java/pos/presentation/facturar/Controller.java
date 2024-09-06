package pos.presentation.facturar;


import pos.Application;
import pos.logic.*;
import pos.presentation.facturar.Model;
import pos.presentation.facturar.View;


public class Controller {

    /*Referencias a la vista y modelo*/
    View view;
    Model model;
    private pos.presentation.productos.Controller controllerProd;

    /*Inicializa el modelo con una lista de Lineas obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Linea())); // Inicializa el modelo con todas las Facturas

        this.view = view;
        this.model = model;

        this.view.setController(this);
        view.setModel(model);

    }

    public void searchProducto(Producto producto) throws Exception{
        controllerProd.search(producto);
    }

    public void setCantidad(int cantidad){
        model.getCurrent().setCantidadVendida(cantidad);
    }

    public void setDescuento(float descuento){
        model.getCurrent().setDescuento(descuento);
    }


}
