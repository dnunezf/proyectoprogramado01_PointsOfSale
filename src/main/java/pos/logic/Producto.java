package pos.logic;

import java.util.List;

public class Producto
{
    private String codigo;

    private String descripcion;
    private String unidad;
    private double precioUnitario;
    private int existencias;

    //Marcar el total de existencias vendidas por pruducto
    //para ayudar a los calculos de las ventas por categoria

    private int cantidadVendida;

    private Categoria categoria;

    private List<Linea> lineasVinculadas;

    public Producto(){

        this.codigo = "";
        this.descripcion = "";
        this.unidad = "";
        this.precioUnitario = 0;
        this.existencias = 0;
        this.categoria = new Categoria();
        this.cantidadVendida = 0;
    }
    public Producto(String cod, String desc, String uni, double precio, int exis, Categoria cat){

        this.codigo = cod;
        this.descripcion = desc;
        this.unidad = uni;
        this.precioUnitario = precio;
        this.existencias = exis;
        this.categoria = cat;
        this.cantidadVendida = 0;

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
//        this.categoria.setProductoVinculado(this);
    }

    public String toString(){

        return getDescripcion();
    }

    public List<Linea> getLineasVinculadas() {

        return lineasVinculadas;
    }

    public void setLineaVinculada(Linea linea) {

        this.lineasVinculadas.add(linea);

    }

}
