package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.Objects;

/*CLASE CAJERO, SE IMPLEMENTA LA LÓGICA DEL OBJETO CLIENTE*/

/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
@XmlAccessorType(XmlAccessType.FIELD)
public class Cajero
{
    /*Atributo ID, único. Este campo se utiliza como clave primaria en el XML*/
    @XmlID
    String id;

    String nombre;


    public Cajero()
    {
        this("","");
    }

    public Cajero(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getId()
    {
        return id;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    /*Compara este objeto Cajero con otro, para saber si son iguales*/
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cajero cajero = (Cajero) o;
        return Objects.equals(id, cajero.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    @Override
    public String toString()
    {
        return nombre;
    }
}
