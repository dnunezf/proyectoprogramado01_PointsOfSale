package pos.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

/*Clase diseñada para utilizarse en el contexto de serialización y deserialización XML*/
@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria
{
    /*Atributo tipo, único. Este campo se utiliza como clave primaria en el XML*/
    @XmlID
    private String tipo;

    public Categoria() {
    }

    public Categoria(String tipo) {

        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {

        this.tipo = tipo;
    }

    public String toString() {

        return this.getTipo();
    }
}
