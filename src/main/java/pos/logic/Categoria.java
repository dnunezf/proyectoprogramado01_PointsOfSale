package pos.logic;

public class Categoria {

    private String tipo;

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
