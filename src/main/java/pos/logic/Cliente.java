package pos.logic;

/*CLASE CLIENTE, SE IMPLEMENTA LA LÓGICA DEL OBJETO CLIENTE*/

import java.util.Objects;

public class Cliente
{
    String id;  //Llave primaria

    String nombre;
    String telefono;
    String email;
    float descuento;

    /*Constructor defecto*/
    public Cliente()
    {
        this("","","","",0);
    }

    /*Constructor parametrizado*/
    public Cliente(String id, String nombre, String telefono, String email, float descuento) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.descuento = descuento;
    }

    /*GETTERS AND SETTERS*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }



    /*Compara este objeto Cliente con otro, para saber si son iguales*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
