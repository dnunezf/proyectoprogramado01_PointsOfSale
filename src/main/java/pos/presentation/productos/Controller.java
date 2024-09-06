package pos.presentation.productos;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Service;
import pos.presentation.facturar.FacturarBuscar;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;

    /*Inicializa el modelo con una lista de productos obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Producto())); // Inicializa el modelo con todos los productos

        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);
    }


    /*Método para buscar productos que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la búsqueda*/
    public void search(Producto filter) throws Exception
    {
        model.setFilter(filter); // Establece el filtro
        model.setMode(Application.MODE_CREATE); //Establece modo de operación en CREAR
        model.setCurrent(new Producto()); // Resetea el producto actual
        model.setList(Service.getInstance().search(model.getFilter())); // Busca y actualiza la lista en el modelo
    }

    /*Método para guardar un producto. Dependiendo del modo de operación, crea o actualiza el producto*/
    public void save(Producto producto) throws Exception
    {
        switch (model.getMode())
        {
            case Application.MODE_CREATE:
                Service.getInstance().create(producto);
                break;

            case Application.MODE_EDIT:
                Service.getInstance().update(producto);
                break;
        }
        model.setFilter(new Producto());
        search(model.getFilter());
    }

    /*Método para editar un producto. Establece el modo en EDITAR, y carga el producto seleccionado
     * en el modelo*/
    public void edit(int row)
    {
        Producto producto = model.getList().get(row);

        try
        {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.getInstance().read(producto));
        }
        catch (Exception e) {}
    }

    /*Método para eliminar el producto actual del modelo. Después de eliminar,
     * actualiza la lista de productos en el modelo*/
    public void delete() throws Exception
    {
        Service.getInstance().delete(model.getCurrent());
        search(model.getFilter());
    }

    /*Método para limpiar el formulario. Resetea el modo de operación a CREAR, y establece
     * un nuevo producto vacío en el modelo*/
    public void clear()
    {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
    }
}
