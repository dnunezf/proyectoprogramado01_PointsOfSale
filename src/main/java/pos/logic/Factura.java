package pos.logic;

import java.util.ArrayList;

public class Factura
{
//
//    private int numeroDeFactura;
//    private Fecha fecha;
//    private Cliente cliente;
//    private Cajero cajero;
//    private ArrayList<Linea> lineas;
//    private int metodoDePago;
//
//    public Factura(int numeroDeFactura, Fecha fecha, Cliente cliente, Cajero cajero, int metodoDePago) {
//
//        this.numeroDeFactura = numeroDeFactura;
//        this.fecha = fecha;
//        this.cliente = cliente;
//        this.cajero = cajero;
//        this.lineas = new ArrayList<Linea>();
//        this.metodoDePago = metodoDePago;
//
//    }
//
//    public int getNumeroDeFactura() {
//        return numeroDeFactura;
//
//    }
//    public void setNumeroDeFactura(int numeroDeFactura) {
//        this.numeroDeFactura = numeroDeFactura;
//    }
//
//    public Fecha getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(Fecha fecha) {
//        this.fecha = fecha;
//    }
//
//    public Cliente getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Cliente cliente) {
//
//        this.cliente = cliente;
//    }
//
//    public Cajero getCajero() {
//
//        return cajero;
//    }
//
//    public void setCajero(Cajero cajero) {
//
//        this.cajero = cajero;
//    }
//
//    public ArrayList<Linea> getLineas() {
//
//        return lineas;
//    }
//
//    public void setLineas(ArrayList<Linea> lineas) {
//
//        this.lineas = lineas;
//    }
//
//    public int getMetodoDePago() {
//
//        return metodoDePago;
//    }
//
//    public void setMetodoDePago(int metodoDePago) {
//
//        this.metodoDePago = metodoDePago;
//    }
//
//    //Metodos Propios de la Factura
//
//    public void agregarProducto(Producto p,int cantidad){
//
//        lineas.add(new Linea(lineas.size(),cantidad,p));
//
//    }
//
//    public double calcularTotal(){
//
//        double total = 0;
//
//        for(Linea linea : lineas){
//
//            total += linea.getProductoVendido().getPrecioUnitario() * linea.getCantidadVendida();
//
//        }
//
//        return total;
//
//    }
//
//    //public double procesarPago(){}
//    //public void generarPDF(){}


}
