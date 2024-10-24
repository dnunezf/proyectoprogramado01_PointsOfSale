package pos.logic;

public class Linea
{
    private String numeroDeLinea;

    private Producto productoVendido;

    private int cantidadVendida;

    private Factura factura;

    private float descuento;
    private float descuentoCliente; // Nuevo atributo para el descuento del cliente

    public Linea()
    {
        // Inicialización por defecto
        this.numeroDeLinea = "";
        this.productoVendido = new Producto();
        this.cantidadVendida = 1;
        this.factura =  new Factura();
        this.descuento = 0;
        this.descuentoCliente = 0;
    }

    public Linea(Producto productoVendido, Integer numeroLinea) {
        this.numeroDeLinea = numeroLinea != null ? numeroLinea.toString() : "0";
        this.productoVendido = productoVendido;
        this.cantidadVendida = 1;
        this.factura = new Factura();
        this.descuento = 0;
        this.descuentoCliente = 0;
       // actualizaExistencia();
    }

    public Linea(Producto productoVendido, Factura factura) {
        this.factura = factura;
        Integer numeroDeLinea = this.factura.getLineas().size();
        this.numeroDeLinea = numeroDeLinea.toString();
        this.productoVendido = productoVendido;
        this.cantidadVendida = 1;
        this.descuento = 0;
        this.descuentoCliente = 0; // Inicializa el descuento del cliente
       // actualizaExistencia();
    }

    public Linea(Factura r) {
        this.factura = r;
    }

    public float getMontoPendiente(){

        return (float)this.productoVendido.getPrecioUnitario() * this.cantidadVendida;
    }


    public void actualizaExistencia() throws Exception {
        int nuevasExistencias = productoVendido.getExistencias() - cantidadVendida;
        if (nuevasExistencias >= 0) {
            productoVendido.setExistencias(nuevasExistencias);
            Service.getInstance().update(productoVendido);
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
        //actualizaExistencia();
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
