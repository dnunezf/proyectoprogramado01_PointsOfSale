package pos.data;

/*SE IMPORTAN LAS CLASES Y PACKAGES A UTILIZAR*/
import pos.logic.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/*Clase Data, la cual posee un contenedor para almacenar y gestionar lista de objetos Cliente, Cajero y Producto.
 * Utilizada en el contexto de serialización y deserialización de datos XML*/

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data
{
    /*Lista que almacena objetos tipo Cliente. Dicha lista será serializada a XML
     * con el nombre "Clientes"*/
    @XmlElementWrapper(name = "clientes")
    @XmlElement(name = "cliente")
    private List<Cliente> clientes;

    /*Lista que almacena objetos tipo Cajero. Dicha lista será serializada a XML
     * con el nombre "Cajeros"*/
    @XmlElementWrapper(name = "cajeros")
    @XmlElement(name = "cajero")
    private List<Cajero> cajeros;

    /*Lista que almacena objetos tipo Producto. Dicha lista será serializada a XML
     * con el nombre "Productos"*/
    @XmlElementWrapper(name = "productos")
    @XmlElement(name = "producto")
    private List<Producto> productos;


    /*Lista que almacena objetos tipo Factura. Dicha lista será serializada a XML
     * con el nombre "Factura"*/
    @XmlElementWrapper(name = "facturas")
    @XmlElement(name = "factura")
    private List<Factura> facturas;


    /*Lista que almacena objetos tipo Linea. Dicha lista será serializada a XML
     * con el nombre "Linea"*/
    @XmlElementWrapper(name = "lineas")
    @XmlElement(name = "linea")
    private List<Linea> lineas;


    @XmlElementWrapper(name = "categorias")
    @XmlElement(name = "categoria")
    private List<Categoria> categorias;

    /*Constructor por defecto que inicializa las listas de Clientes, Cajeros y Productos*/
    public Data()
    {
        clientes = new ArrayList<>();
        cajeros = new ArrayList<>();
        productos = new ArrayList<>();
        facturas = new ArrayList<>();
        lineas = new ArrayList<>();
        categorias = new ArrayList<>();
    }

    public List<Cliente> getClientes()
    {
        return clientes;
    }

    public List<Cajero> getCajeros()
    {
        return cajeros;
    }

    public List<Producto> getProductos()
    {
        return productos;
    }

    public List<Factura> getFacturas(){return facturas;}

    public List<Linea> getLineas(){return lineas;}

    public List<Categoria> getCategorias(){return categorias;}
}
