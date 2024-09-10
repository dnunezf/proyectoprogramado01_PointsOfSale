package pos.presentation.cajeros;

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
import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Service;

/*Maneja la lógica de interacción entre la vista (View) y el modelo (Model), respondiendo a
 *las acciones del usuario y actualizando la vista y el modelo en consecuencia.*/

public class Controller
{
    /*Referencias a la vista y modelo*/
    View view;
    Model model;

    /*Inicializa el modelo con una lista de cajeros obtenida de Servicio*/
    public Controller(View view, Model model)
    {
        model.init(Service.getInstance().search(new Cajero())); // Inicializa el modelo con todos los cajeros

        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);
    }

    /*Metodo para buscar cajeros que coincidan con los criterios del filtro especificado.
     * Actualiza el modelo con los resultados de la busqueda*/
    public void search(Cajero filter) throws Exception
    {
        model.setFilter(filter); // Establece el filtro
        model.setMode(Application.MODE_CREATE); //Establece modo de operacion en CREAR
        model.setCurrent(new Cajero()); // Resetea el cajero actual
        model.setList(Service.getInstance().search(model.getFilter())); // Busca y actualiza la lista en el modelo
    }

    /*Metodo para guardar un cajero. Dependiendo del modo de operacion, crea o actualiza el cliente*/
    public void save(Cajero cajero) throws Exception
    {
        switch (model.getMode())
        {
            case Application.MODE_CREATE:
                Service.getInstance().create(cajero);
                break;

            case Application.MODE_EDIT:
                Service.getInstance().update(cajero);
                break;
        }
        model.setFilter(new Cajero());
        search(model.getFilter());
    }

    /*Metodo para editar un cajero. Establece el modo en EDITAR, y carga el cajero seleccionado
     * en el modelo*/
    public void edit(int row)
    {
        Cajero cajero = model.getList().get(row);

        try
        {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.getInstance().read(cajero));
        }
        catch (Exception e) {}
    }

    /*Metodo para eliminar el cajero actual del modelo. Despues de eliminar,
     * actualiza la lista de cajeros en el modelo*/
    public void delete() throws Exception
    {
        Service.getInstance().delete(model.getCurrent());
        search(model.getFilter());
    }

    /*Metodo para limpiar el formulario. Resetea el modo de operacion a CREAR, y establece
     * un nuevo cajero vacio en el modelo*/
    public void clear()
    {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Cajero());
    }

    public void print()throws Exception
    {
        String dest="cajeros.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);

        //Document document = new Document(pdf, PageSize.A4.rotate());
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(400);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Listado de Cajeros").setFont(font).setBold().setFontSize(20f), TextAlignment.CENTER,false));
        //header.addCell(getCell(new Image(ImageDataFactory.create("logo.jpg")), HorizontalAlignment.CENTER,false));
        document.add(header);

        document.add(new Paragraph(""));document.add(new Paragraph(""));

        Color bkg = ColorConstants.RED;
        Color frg= ColorConstants.WHITE;
        Table body = new Table(2);
        body.setWidth(400);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Id").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Nombre").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));

        for(Cajero e: model.getList()){
            body.addCell(getCell(new Paragraph(e.getId()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(e.getNombre()),TextAlignment.CENTER,true));
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
}
