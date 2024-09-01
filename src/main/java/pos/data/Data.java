package pos.data;

/*SE IMPORTAN LAS CLASES Y PACKAGES A UTILIZAR*/
import pos.logic.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/*Clase Data, la cual posee un contenedor para almacenar y gestionar lista de objetos Cliente.
* Utilizada en el contexto de serializacion y deserializacion de datos XML*/

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data
{
    /*Lista que almacena objetos tipo Cliente. Dicha lista sera serializada a XML
    * con el nombre "Clientes"*/
    @XmlElementWrapper(name = "clientes")
    @XmlElement(name = "cliente")
    private List<Cliente> clientes;

    /*Lista que almacena objetos tipo Cajero. Dicha lista sera serializada a XML
     * con el nombre "Cajeros"*/
    @XmlElementWrapper(name = "cajeros")
    @XmlElement(name = "cajero")
    private List<Cajero> cajeros;

    public Data()
    {
        clientes = new ArrayList<>();
        cajeros = new ArrayList<>();
    }

    public List<Cliente> getClientes()
    {
        return clientes;
    }

    public List<Cajero> getCajeros()
    {
        return cajeros;
    }
}
