package pos.logic;

import java.util.Objects;

/*CLASE CAJERO, SE IMPLEMENTA LA LÓGICA DEL OBJETO CLIENTE*/

public class Cajero
{
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
