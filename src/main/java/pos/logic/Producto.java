package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
@XmlAccessorType(XmlAccessType.FIELD)
public class Producto
{
    /*Atributo codigo, único. Este campo se utiliza como clave primaria en el XML*/
    @XmlID
    private String codigo;

    private String descripcion;
    private String unidad;
    private double precioUnitario;
    private int existencias;
    Categoria categoria;

    public Producto(){

        this.codigo = "";
        this.descripcion = "";
        this.unidad = "";
        this.precioUnitario = 0;
        this.existencias = 0;
        this.categoria = null;

    }
    public Producto(String cod, String desc, String uni, double precio, int exis, Categoria cat){

        this.codigo = cod;
        this.descripcion = desc;
        this.unidad = uni;
        this.precioUnitario = precio;
        this.existencias = exis;
        this.categoria = cat;

    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {

        this.unidad = unidad;
    }

    public double getPrecioUnitario() {

        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {

        this.precioUnitario = precioUnitario;
    }

    public void setExistencias(int existencias) {

        this.existencias = existencias;
    }

    public int getExistencias() {

        return existencias;
    }

    public void actualizarExistencias(int existencias) {

        this.existencias = existencias;
    }

    public Categoria getCategoria() {

        return categoria;
    }

    public void setCategoria(Categoria categoria) {

        this.categoria = categoria;
    }

    public String toString(){

        return getDescripcion();
    }

}
