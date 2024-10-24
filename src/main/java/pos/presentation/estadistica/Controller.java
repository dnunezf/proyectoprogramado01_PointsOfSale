package pos.presentation.estadistica;

import pos.Application;
import pos.logic.Factura;
import pos.logic.Producto;
import pos.logic.Service;
import pos.logic.Categoria;

public class Controller {

    View view;
    Model model;
    pos.presentation.historico.Model historicoModel;

    public Controller(pos.presentation.estadistica.View view, pos.presentation.estadistica.Model model, pos.presentation.historico.Model historicoModel)
    {
        model.init(Service.getInstance().search(new Categoria()));

        this.view = view;
        this.model = model;
        this.historicoModel = historicoModel;

        view.setController(this);
        view.setModel(model);
    }

    public pos.presentation.historico.Model getHistoricoModel() {return this.historicoModel;}

    /*Metodo para buscar clientes que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la busqueda*/
    public void search(Categoria filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Categoria());
        model.setList(Service.getInstance().search(model.getFilter())); // Asegúrate de que el método search en Service esté preparado para filtrar por las categorías correctas
    }


    /*Metodo para guardar un cliente. Dependiendo del modo de operacion, crea o actualiza el cliente*/
//    public void save(Categoria categoria) throws Exception
//    {
//        switch (model.getMode())
//        {
//            case Application.MODE_CREATE:
//                Service.getInstance().create(categoria);
//                break;
//
//            case Application.MODE_EDIT:
//                Service.getInstance().update(categoria);
//                break;
//        }
//        model.setFilter(new Categoria());
//        search(model.getFilter());
//    }

    /*Metodo para editar un cliente. Establece el modo en EDITAR, y carga el cliente seleccionado
     * en el modelo*/
    public void edit(int row)
    {
        Categoria categoria = model.getList().get(row);

        try
        {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.getInstance().read(categoria));
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
        model.setCurrent(new Categoria());
    }
}

