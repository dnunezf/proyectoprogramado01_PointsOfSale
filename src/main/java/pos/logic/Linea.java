package pos.logic;

public class Linea {

    private int numeroDeLinea;
    private int cantidadVendida;
    private Producto productoVendido;

    public Linea(int numeroDeLinea, int cantidadVendida, Producto productoVendido) {

        this.numeroDeLinea = numeroDeLinea;
        this.cantidadVendida = cantidadVendida;
        this.productoVendido = productoVendido;
    }

    public int getNumeroDeLinea() {
        return numeroDeLinea;
    }
    public void setNumeroDeLinea(int numeroDeLinea) {

        this.numeroDeLinea = numeroDeLinea;
    }
    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Producto getProductoVendido() {
        return productoVendido;
    }

    public void setProductoVendido(Producto productoVendido) {
        this.productoVendido = productoVendido;
    }

    public String toString(){

        String s = "";

        s+= numeroDeLinea+"..........."+productoVendido.toString()+"............"+cantidadVendida;

        return s;

    }
}
