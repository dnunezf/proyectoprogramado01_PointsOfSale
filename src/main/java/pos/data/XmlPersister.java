package pos.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*Clase XmlPersister, se encarga de la persistencia de datos en formato XML, permitiendo la
* serializacion y deserializacion de objetos de la clase Data.
* Patron Singleton */

public class XmlPersister
{
    /*SINGLETON APPLY*/

    /*Instancia unica*/
    private static XmlPersister instance = null;

    /*Ruta dell archivo XML donde se almacenan los datos*/
    private String path;

    public XmlPersister(String path)
    {
        this.path = path;
    }

    /*Obtiene instancia unica*/
    public static XmlPersister getInstance()
    {
        if (instance == null)
        {
            /*Permite que el bloque no sea ejecutado al mismo tiempo por varios procesos*/
            synchronized (XmlPersister.class)
            {
                if (instance == null)
                {
                    instance = new XmlPersister("pos.xml");
                }
            }
        }
        return instance;
    }

    /*Carga los datos desde el archivo XML en un objeto Data*/
    public Data load() throws Exception
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        FileInputStream is = new FileInputStream(path);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Data result = (Data) unmarshaller.unmarshal(is);

        is.close();

        return result;
    }

    /*Guarda los datos de un objeto Data en el archivo XML*/
    public void store(Data data) throws Exception
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

        FileOutputStream os = new FileOutputStream(path);
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.marshal(data, os);

        os.flush();
        os.close();
    }
}
