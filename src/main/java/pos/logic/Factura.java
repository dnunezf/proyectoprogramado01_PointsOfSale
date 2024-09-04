package pos.logic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Factura
{

    private int numeroDeFactura;
    private LocalDate fecha;
    private Cliente cliente;
    private Cajero cajero;
    private ArrayList<Linea> lineas;
    private int metodoDePago;

    public Factura(int numeroDeFactura, Cliente cliente, Cajero cajero, int metodoDePago) {

        this.numeroDeFactura = numeroDeFactura;
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.cajero = cajero;
        this.lineas = new ArrayList<Linea>();
        this.metodoDePago = metodoDePago;

    }

    public Factura(){


    }

    public int getNumeroDeFactura() {
        return numeroDeFactura;

    }
    public void setNumeroDeFactura(int numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
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

    public ArrayList<Linea> getLineas() {

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

        lineas.add(new Linea(lineas.size(),p,cantidad,this));

    }

    public void eliminarProducto(int indiceLinea) {
        if (indiceLinea >= 0 && indiceLinea < lineas.size()) {
            lineas.remove(indiceLinea);
            // Recalcular los índices de las líneas restantes
            for (int i = indiceLinea; i < lineas.size(); i++) {
                lineas.get(i).setNumeroDeLinea(i);
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

    public double calcularTotal(){

        double total = 0;

        for(Linea linea : lineas){

            total += linea.getProductoVendido().getPrecioUnitario() * linea.getCantidadVendida();

        }

        return total;

    }




}
