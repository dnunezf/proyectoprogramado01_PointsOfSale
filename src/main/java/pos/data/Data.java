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

    public Data()
    {
        clientes = new ArrayList<>();
    }

    public List<Cliente> getClientes()
    {
        return clientes;
    }
}
