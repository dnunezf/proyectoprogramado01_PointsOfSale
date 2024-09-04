package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Linea
{
    @XmlID
    private int numeroDeLinea;

    @XmlIDREF
    private Producto productoVendido;

    private int cantidadVendida;

    @XmlIDREF
    private Factura factura;

    private float descuento;


    public Linea(){

    }

    public Linea(int numeroDeLinea, Producto productoVendido,int cantidadVendida, Factura factura) {
        this.numeroDeLinea = numeroDeLinea;
        this.productoVendido = productoVendido;
        this.cantidadVendida = cantidadVendida;
        this.factura = factura;
        this.descuento = factura.getCliente().getDescuento();
        actualizaExistencia();
    }

    public void actualizaExistencia(){
        productoVendido.setExistencias(productoVendido.getExistencias() - cantidadVendida);
    }

    public int getNumeroDeLinea() {
        return numeroDeLinea;
    }

    public void setNumeroDeLinea(int numeroDeLinea) {
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
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public String toString(){

        String s = "";

        s+= numeroDeLinea+"..........."+productoVendido.toString()+"............"+cantidadVendida;

        return s;

    }
}
