//package pos.presentation.cajeros;
//
//import pos.Application;
//import pos.logic.Cajero;
//import pos.logic.Service;
//
///*Controlador para manejar la lógica de interacción entre la vista (View) y el modelo (Model)
// * específico para la entidad Cajero.*/
//public class Controller
//{
//    /*Referencias a la vista y modelo*/
//    View view;
//    Model model;
//
//    /*Inicializa el modelo con una lista de cajeros obtenida de Servicio*/
//    public Controller(View view, Model model)
//    {
//        model.init(Service.getInstance().search(new Cajero())); // Inicializa el modelo con todos los cajeros
//
//        this.view = view;
//        this.model = model;
//
//        view.setController(this);
//        view.setModel(model);
//    }
//
//    /*Metodo para buscar cajeros que coincidan con los criterios del filtro especificado.
//     * Actualiza el modelo con los resultados de la búsqueda*/
//    public void search(Cajero filter) throws Exception
//    {
//        model.setFilter(filter); // Establece el filtro
//        model.setMode(Application.MODE_CREATE); // Establece modo de operación en CREAR
//        model.setCurrent(new Cajero()); // Resetea el cajero actual
//        model.setList(Service.getInstance().search(model.getFilter())); // Busca y actualiza la lista en el modelo
//    }
//
//    /*Metodo para guardar un cajero. Dependiendo del modo de operación, crea o actualiza el cajero*/
//    public void save(Cajero cajero) throws Exception
//    {
//        switch (model.getMode())
//        {
//            case Application.MODE_CREATE:
//                Service.getInstance().create(cajero);
//                break;
//
//            case Application.MODE_EDIT:
//                Service.getInstance().update(cajero);
//                break;
//        }
//        model.setFilter(new Cajero());
//        search(model.getFilter());
//    }
//
//    /*Metodo para editar un cajero. Establece el modo en EDITAR, y carga el cajero seleccionado
//     * en el modelo*/
//    public void edit(int row)
//    {
//        Cajero cajero = model.getList().get(row);
//
//        try
//        {
//            model.setMode(Application.MODE_EDIT);
//            model.setCurrent(Service.getInstance().read(cajero));
//        }
//        catch (Exception e) {}
//    }
//
//    /*Metodo para eliminar el cajero actual del modelo. Despues de eliminar,
//     * actualiza la lista de cajeros en el modelo*/
//    public void delete() throws Exception
//    {
//        Service.getInstance().delete(model.getCurrent());
//        search(model.getFilter());
//    }
//
//    /*Metodo para limpiar el formulario. Resetea el modo de operación a CREAR, y establece
//     * un nuevo cajero vacio en el modelo*/
//    public void clear()
//    {
//        model.setMode(Application.MODE_CREATE);
//        model.setCurrent(new Cajero());
//    }
//}