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


    private float descuento;
    private float descuentoCliente; // Nuevo atributo para el descuento del cliente

    public Linea()
    {
        // Inicialización por defecto
        this.numeroDeLinea = "";
        this.productoVendido = null;
        this.cantidadVendida = 1;
        this.factura = null;
        this.descuento = 0;
        this.descuentoCliente = 0;
    }

    public Linea(Producto productoVendido, Integer numeroLinea) {
        this.numeroDeLinea = numeroLinea != null ? numeroLinea.toString() : "0";
        this.productoVendido = productoVendido;
        this.cantidadVendida = 1;
        this.factura = null;
        this.descuento = 0;
        this.descuentoCliente = 0;
        actualizaExistencia();
    }

    public Linea(Producto productoVendido, Factura factura) {
        this.factura = factura;
        Integer numeroDeLinea = this.factura.getLineas().size();
        this.numeroDeLinea = numeroDeLinea.toString();
        this.productoVendido = productoVendido;
        this.cantidadVendida = 1;
        this.descuento = 0;
        this.descuentoCliente = 0; // Inicializa el descuento del cliente
        actualizaExistencia();
    }

    public float getMontoPendiente(){

        return (float)this.productoVendido.getPrecioUnitario() * this.cantidadVendida;
    }


    public void actualizaExistencia() {
        int nuevasExistencias = productoVendido.getExistencias() - cantidadVendida;
        if (nuevasExistencias >= 0) {
            productoVendido.setExistencias(nuevasExistencias);
        } else {
            throw new IllegalArgumentException("No hay suficientes existencias para la cantidad vendida");
        }
    }

    public String getNumeroDeLinea() {
        return numeroDeLinea;
    }

    public Categoria getCategoriaProducto(){return productoVendido.getCategoria();}

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
        if (cantidadVendida <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser mayor que 0");
        }
        this.cantidadVendida = cantidadVendida;
        actualizaExistencia();
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public double getImporte() {
        return cantidadVendida * getNeto();
    }

    public double getNeto() {
        return getProductoVendido().getPrecioUnitario() - (getProductoVendido().getPrecioUnitario() * getDescuentoTotal()/100);
    }

    public float getDescuentoCliente() {
        return descuentoCliente;
    }

    public float getDescuento(){return descuento;}

    public float getDescuentoTotal() {
        return descuento+descuentoCliente;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public void setDescuentoCliente(float descuentoCliente) {
        this.descuentoCliente = descuentoCliente;
    }

    public String toString() {
        return numeroDeLinea + "..........." + productoVendido.toString() + "............" + cantidadVendida;
    }
}
