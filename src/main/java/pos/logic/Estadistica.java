package pos.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estadistica {

    private LocalDate inicial;
    private LocalDate finalizacion;
    private List<Categoria> categorias;

    public Estadistica(LocalDate inicial, LocalDate finalizacion) {

        this.inicial = inicial;
        this.finalizacion = finalizacion;

    }

    public Estadistica() {

        this(null, null);

    }

    public LocalDate getInicial() {

        return inicial;

    }


    public void setInicial(LocalDate inicial) {


        this.inicial = inicial;
    }


    public LocalDate getFinalizacion() {


        return finalizacion;
    }


    public void setFinalizacion(LocalDate finalizacion) {


        this.finalizacion = finalizacion;
    }


    public List<Categoria> getCategorias() {


        return categorias;
    }

    public void setCategoria(List<Categoria> categoria) {

        this.categorias = categoria;
    }

    public List<LocalDate> getDatesBetween() {
        List<LocalDate> dateList = new ArrayList<>();

        // Asegurarse de que la fecha de inicio sea anterior a la fecha de finalización
        if (this.inicial.isAfter(this.finalizacion)) {
            return dateList; // Retorna una lista vacía si las fechas están en orden incorrecto
        }

        // Iterar desde la fecha de inicio hasta la fecha de finalización
        for (LocalDate date = this.inicial; !date.isAfter(this.finalizacion); date = date.plusDays(1)) {
            dateList.add(date);
        }

        return dateList;
    }

}
