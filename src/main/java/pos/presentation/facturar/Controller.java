package pos.presentation.facturar;


import pos.Application;
import pos.logic.*;
import pos.presentation.facturar.Model;
import pos.presentation.facturar.View;

import javax.swing.*;


public class Controller {

    /*Referencias a la vista y modelo*/
    View view;
    Model model;
    private pos.presentation.productos.Controller controllerProd;

    /*Inicializa el modelo con una lista de Lineas obtenida de Servicio*/
    public Controller(View view, Model model, pos.presentation.productos.Controller controllerProd )
    {
        model.init(Service.getInstance().search(new Linea())); // Inicializa el modelo con todas las Facturas

        this.view = view;
        this.model = model;

        this.view.setController(this);
        view.setModel(model);
        this.controllerProd = controllerProd;

    }

    public void add(Producto filter)
    {
        try {
        Producto prod = Service.getInstance().searchOne(filter);

        if (prod != null)
        {
            // Obtén el cliente actual y su descuento
            Cliente clienteActual = (Cliente) view.getComboBoxCli().getSelectedItem();
            float descuentoCliente = clienteActual != null ? clienteActual.getDescuento() : 0;

            // Crea una nueva línea de producto
            Linea nuevaLinea = new Linea(prod, model.getList().size() + 1);

            // Asigna el descuento del cliente a la nueva línea de producto
            nuevaLinea.setDescuentoCliente(descuentoCliente);

            // Añade la nueva línea de producto a la lista en el modelo
            model.getList().add(nuevaLinea);
            model.setList(model.getList()); // Notifica a la vista sobre el cambio

            JOptionPane.showMessageDialog(view.getPanel(), "Producto Ingresado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view.getPanel(), "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view.getPanel(), "Error al agregar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
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
