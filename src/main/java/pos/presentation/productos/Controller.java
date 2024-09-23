package pos.presentation.productos;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import pos.Application;
import pos.logic.Producto;
import pos.logic.Service;

import java.util.List;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;
    pos.presentation.facturar.FacturarBuscar facturarBuscarView;

    /*Inicializa el modelo con una lista de productos obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Producto())); // Inicializa el modelo con todos los productos

        this.view = view;
        this.model = model;

        //facturarBuscarView.setController(this);
        view.setController(this);
        view.setModel(model);
    }


    /*Método para buscar productos que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la búsqueda*/
    public List<Producto> search(Producto filter) throws Exception
    {
        model.setFilter(filter); // Establece el filtro
        model.setMode(Application.MODE_CREATE); //Establece modo de operación en CREAR
        model.setCurrent(new Producto()); // Resetea el producto actual
        model.setList(Service.getInstance().search(filter)); // Busca y actualiza la lista en el modelo

//        if (facturarBuscarView != null) {
//            facturarBuscarView.updateProductList(model.getList());
//        }
        return null;
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

    public void print()throws Exception{
        String dest="productos.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);

        //Document document = new Document(pdf, PageSize.A4.rotate());
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(400);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Listado de Productos").setFont(font).setBold().setFontSize(20f), TextAlignment.CENTER,false));
        //header.addCell(getCell(new Image(ImageDataFactory.create("logo.jpg")), HorizontalAlignment.CENTER,false));
        document.add(header);

        document.add(new Paragraph(""));document.add(new Paragraph(""));

        Color bkg = ColorConstants.RED;
        Color frg= ColorConstants.WHITE;
        Table body = new Table(6);
        body.setWidth(400);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Codigo").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Descripcion").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Unidad").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Precio").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Existencias").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Categoria").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));


        for(Producto e: model.getList()){
            body.addCell(getCell(new Paragraph(e.getCodigo()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getDescripcion()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getUnidad()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(String.valueOf(e.getPrecioUnitario())),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(String.valueOf(e.getExistencias())),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(String.valueOf(e.getCategoria())),TextAlignment.CENTER,true));
        }
        document.add(body);
        document.close();
    }

    private Cell getCell(Paragraph paragraph, TextAlignment alignment, boolean hasBorder) {
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        if(!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell getCell(Image image, HorizontalAlignment alignment, boolean hasBorder) {
        Cell cell = new Cell().add(image);
        image.setHorizontalAlignment(alignment);
        cell.setPadding(0);
        if(!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public Model getModel(){
        return this.model;
    }
}
