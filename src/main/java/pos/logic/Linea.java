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


    public Linea(){

    }

    public Linea(int numeroDeLinea, Producto productoVendido,int cantidadVendida, Factura factura) {
        this.numeroDeLinea = numeroDeLinea;
        this.productoVendido = productoVendido;
        this.cantidadVendida = cantidadVendida;
        this.factura = factura;
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

    public String toString(){

        String s = "";

        s+= numeroDeLinea+"..........."+productoVendido.toString()+"............"+cantidadVendida;

        return s;

    }
}
