package pos.logic;

import java.util.Arrays;

public class CategoriaConVentas {
    private Categoria categoria;
    private Double[] ventas;

    public CategoriaConVentas(Categoria categoria, Double[] ventas) {
        this.categoria = categoria;
        this.ventas = ventas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Double[] getVentas() {
        return ventas;
    }

    @Override
    public String toString() {
        return "Categoria: " + categoria.getTipo() + ", Ventas: " + Arrays.toString(ventas);
    }
}
