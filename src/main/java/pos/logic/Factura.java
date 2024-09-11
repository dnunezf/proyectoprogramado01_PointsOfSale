package pos.logic;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
@XmlAccessorType(XmlAccessType.FIELD)
public class Factura
{

    /*Atributo tipo, único. Este campo se utiliza como clave primaria en el XML*/
    @XmlID
    private String numeroDeFactura;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate fecha;

    @XmlIDREF
    private Cliente cliente;

    @XmlIDREF
    private Cajero cajero;

    @XmlIDREF
    @XmlElementWrapper(name = "lineas")
    @XmlElement(name = "linea")
    private List<Linea> lineas;

    private int metodoDePago;

    public static final int EFECTIVO = 0;
    public static final int TARJETA = 1;
    public static final int CHEQUE = 2;
    public static final int SINPE = 3;
    public static final int VARIADO = 4;

    public Factura(String numeroDeFactura, Cliente cliente, Cajero cajero, List<Linea> lineas ) {

        this.numeroDeFactura = numeroDeFactura;
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.cajero = cajero;
        this.lineas = lineas;
        this.metodoDePago = 4;

    }

    public Factura()
    {

    }

    public String getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(String numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;
    }

    public Cajero getCajero() {

        return cajero;
    }

    public void setCajero(Cajero cajero) {

        this.cajero = cajero;
    }

    public List<Linea> getLineas() {

        return lineas;
    }

    public void setLineas(ArrayList<Linea> lineas) {

        this.lineas = lineas;
    }

    public int getMetodoDePago() {

        return metodoDePago;
    }

    public void setMetodoDePago(int metodoDePago) {

        this.metodoDePago = metodoDePago;
    }

    //Metodos Propios de la Factura

    public void agregarProducto(Producto p,int cantidad){

        Integer tam = lineas.size();

        lineas.add(new Linea(p,this){

            @Override
            public void setCantidadVendida(int cantidadVendida) {
                super.setCantidadVendida(cantidadVendida);
            }
        });

    }

    public void eliminarProducto(int indiceLinea) {
        if (indiceLinea >= 0 && indiceLinea < lineas.size()) {
            lineas.remove(indiceLinea);
            // Recalcular los índices de las líneas restantes
            for (Integer i = indiceLinea; i < lineas.size(); i++) {
                lineas.get(i).setNumeroDeLinea(i.toString());
            }
        }
    }

    public int obtenerCantidadTotalProductos() {
        int cantidadTotal = 0;
        for (Linea linea : lineas) {
            cantidadTotal += linea.getCantidadVendida();
        }
        return cantidadTotal;
    }

    public void vaciarFactura() {
        lineas.clear();
    }

    public double calcularTotal()
    {
        double total = 0;

        for(Linea linea : lineas)
        {
            total += linea.getProductoVendido().getPrecioUnitario() * linea.getCantidadVendida();
        }

        return total;
    }
}
