package pos.presentation.facturar;


import pos.Application;
import pos.logic.*;
import pos.presentation.facturar.Model;
import pos.presentation.facturar.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    View view;
    Model model;
    pos.presentation.productos.Controller controllerProd;
    pos.presentation.productos.TableModel tableModelProd;

    public Controller(View view, Model model, pos.presentation.productos.Controller controllerProd)
    {
        model.init(Service.getInstance().search(new Linea()),Service.getInstance().search(new Producto())); // Inicializa el modelo con todas las Facturas

        this.view = view;
        this.model = model;

        this.view.setController(this);
        view.setModel(model);
        this.controllerProd = controllerProd;

        view.setProductosController(controllerProd);
    }

    public void add(Producto filter)
    {
        try
        {
            Producto prod = Service.getInstance().read(filter);

            if (prod != null)
            {
                // Obtiene cliente actual y descuento
                Cliente clienteActual = (Cliente) view.getComboBoxCli().getSelectedItem();
                float descuentoCliente = clienteActual != null ? clienteActual.getDescuento() : 0;

                // Crea una nueva línea de producto
                Linea nuevaLinea = new Linea(prod, model.getList().size() + 1);
                nuevaLinea.setDescuentoCliente(descuentoCliente);

                // Añade la nueva línea de producto a la lista en el modelo
                model.getList().add(nuevaLinea);
                model.setList(model.getList()); // Notifica a la vista sobre el cambio

        } else {
            JOptionPane.showMessageDialog(view.getPanel(), "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error al agregar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCantidad(int cantidad)
    {
        if (cantidad >= 0) {
            model.getCurrent().setCantidadVendida(cantidad);
        } else {
            JOptionPane.showMessageDialog(view.getPanel(), "La cantidad debe ser mayor o igual a 0", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setDescuento(float descuento){
        model.getCurrent().setDescuento(descuento);
    }

    public void reset(){
        model.setList(new ArrayList<>()); // Vacía la lista y notifica a los listeners
    }

    public int getCantidadArticulos()
    {
        int cant = 0;

        List<Linea> lineas = model.getList();

        if (lineas != null && !lineas.isEmpty())
        {
            for (Linea linea : lineas)
            {
                cant += linea.getCantidadVendida();
            }
        }

        return cant;

    }

    public float getDescuentos()
    {
        Cliente clienteActual = (Cliente) view.getComboBoxCli().getSelectedItem();
        float descuento = clienteActual != null ? clienteActual.getDescuento() : 0;

        float totalConDescuento = 0;

        if(!model.getList().isEmpty())
        {
            for(Linea linea : model.getList()){
                totalConDescuento += (float) (((descuento > 0 ? descuento : linea.getDescuento()) / 100) * linea.getProductoVendido().getPrecioUnitario() * linea.getCantidadVendida());
            }

        }

        return totalConDescuento;
    }

    public float getSubtotal()
    {
        float subtotal = 0;

        if(!model.getList().isEmpty())
        {
            for(Linea linea : model.getList())
            {
                subtotal += linea.getProductoVendido().getPrecioUnitario() * linea.getCantidadVendida();
            }

        }

        return subtotal;
    }

    public float getTotal()
    {
        return getSubtotal() - getDescuentos();
    }

}
