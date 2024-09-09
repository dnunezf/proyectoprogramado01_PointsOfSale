package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Linea
{
    @XmlID
    private String numeroDeLinea;

    @XmlIDREF
    private Producto productoVendido;

    private int cantidadVendida;

    @XmlIDREF
    private Factura factura;



    public Linea(){

    }
    public Linea (Producto productoVendido, Integer numeroLinea){

        this.numeroDeLinea = numeroLinea.toString();
        this.productoVendido = productoVendido;
        this.cantidadVendida = 1;
        this.factura = null;
       // numeroDeLinea = "0";
        actualizaExistencia();
    }

    public Linea(Producto productoVendido, Factura factura) {
        this.factura = factura;
        Integer numeroDeLinea = this.factura.getLineas().size();
        this.numeroDeLinea = numeroDeLinea.toString();
        this.productoVendido = productoVendido;
        this.cantidadVendida = 1;
        actualizaExistencia();
    }

    public void actualizaExistencia(){
        productoVendido.setExistencias(productoVendido.getExistencias() - cantidadVendida);
    }

    public String getNumeroDeLinea() {
        return numeroDeLinea;
    }

    public void setNumeroDeLinea(String numeroDeLinea) {
        this.numeroDeLinea = numeroDeLinea;
    }

    public Producto getProductoVendido() {
        return productoVendido;
    }

    public void setProductoVendido(Producto productoVendido) {
        this.productoVendido = productoVendido;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public void setCantidadVendida(int cantidadVendida) {

        this.cantidadVendida = cantidadVendida;
    }

    public int getCantidadVendida() {

        return cantidadVendida;
    }

    public double getImporte(){
        return cantidadVendida * productoVendido.getPrecioUnitario();
    }

    public double getNeto(){
        return productoVendido.getPrecioUnitario() - (productoVendido.getPrecioUnitario() * getDescuento());
    }

    public float getDescuento(){
        if (this.factura != null && this.factura.getCliente() != null) {
            return this.factura.getCliente().getDescuento();
        }
        return 0; // O algún valor por defecto
    }

    public void setDescuento(float descuento) {
        this.factura.getCliente().setDescuento(descuento);
    }

    public String toString(){

        String s = "";

        s+= numeroDeLinea+"..........."+productoVendido.toString()+"............"+cantidadVendida;

        return s;

    }
}
