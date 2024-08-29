package pos.presentation.clientes;

import pos.Application;
import pos.logic.Cliente;
import pos.logic.Service;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;

    /*Inicializa el modelo con una lista de clientes obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Cliente())); // Inicializa el modelo con todos los clientes

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
        model.setCurrent(new Cliente()); // Resetea el cliente actual
        model.setList(Service.getInstance().search(model.getFilter())); // Busca y actualiza la lista en el modelo
    }

    /*Metodo para guardar un cliente. Dependiendo del modo de operacion, crea o actualiza el cliente*/
    public void save(Cliente cliente) throws Exception
    {
        switch (model.getMode())
        {
            case Application.MODE_CREATE:
                Service.getInstance().create(cliente);
                break;

            case Application.MODE_EDIT:
                Service.getInstance().update(cliente);
                break;
        }
        model.setFilter(new Cliente());
        search(model.getFilter());
    }

    /*Metodo para editar un cliente. Establece el modo en EDITAR, y carga el cliente seleccionado
    * en el modelo*/
    public void edit(int row)
    {
        Cliente cliente = model.getList().get(row);

        try
        {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.getInstance().read(cliente));
        }
        catch (Exception e) {}
    }

    /*Metodo para eliminar el cliente actual del modelo. Despues de eliminar,
    * actualiza la lista de clientes en el modelo*/
    public void delete() throws Exception
    {
        Service.getInstance().delete(model.getCurrent());
        search(model.getFilter());
    }

    /*Metodo para limpiar el formulario. Resetea el modo de operacion a CREAR, y establece
    * un nuevo cliente vacio en el modelo*/
    public void clear()
    {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cliente());
    }
}
