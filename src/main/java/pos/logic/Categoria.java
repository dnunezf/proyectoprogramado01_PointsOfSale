package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria
{
    /*Atributo tipo, único. Este campo se utiliza como clave primaria en el XML*/
    @XmlID

    private String id;

    private String tipo;

    @XmlIDREF
    private List<Producto> productosVinculados;

    public Categoria() {
    }

    public Categoria(String tipo) {

        this.id = null;
        this.tipo = tipo;
        this.productosVinculados = new ArrayList<Producto>();
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {

        this.tipo = tipo;
    }

    public List<Producto> getProductoVinculado() {

        return productosVinculados;
    }

    public boolean setProductoVinculado(Producto productoVinculado) {

        if(!productosVinculados.contains(productoVinculado)) {

            this.productosVinculados.add(productoVinculado);

            return true;
        }

        else{

            return false;
        }
    }

    public String toString() {

        return this.getTipo();
    }

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;
    }

    public double totalPorFecha(LocalDate fecha){

        double total = 0;

        for(Producto producto : productosVinculados){

            for(Linea linea : producto.getLineasVinculadas())

                if(fecha.getYear() == linea.getFactura().getFecha().getYear() && fecha.getMonth() == linea.getFactura().getFecha().getMonth()){

                    total += producto.getPrecioUnitario() * linea.getCantidadVendida();

                }

        }

        return total;
    }

}
