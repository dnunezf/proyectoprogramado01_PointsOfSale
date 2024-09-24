package pos.presentation.cajeros;

import pos.logic.Categoria;
import pos.logic.Estadistica;
import pos.logic.Factura;
import pos.presentation.AbstractTableModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Clase TableModel, modelo de tabla que gestiona una lista de categorías.
 * Especifica la estructura y datos de las columnas relacionadas con las categorías y fechas
 * Hereda de AbstractTableModel*/

public class DynamicTableModel extends AbstractTableModel<Categoria> implements javax.swing.table.TableModel {

    private List<LocalDate> fechas; // Lista de fechas (columnas)
    private Map<Categoria, Map<LocalDate, Estadistica>> datos; // Datos estadísticos por categoría y fecha

    public DynamicTableModel(List<Categoria> initialRows, Estadistica estadistica) {
        super(new int[estadistica.getDatesBetween().size()], initialRows);
        this.fechas = estadistica.getDatesBetween(); // Inicializa las fechas según la estadística
        this.datos = new HashMap<>(); // Mapa vacío para almacenar datos

        // Inicializa los datos de las categorías y fechas
        for (Categoria categoria : initialRows) {
            datos.put(categoria, new HashMap<>());
        }
    }

    // Método para agregar o actualizar una estadística en la tabla
    public void setEstadistica(Categoria categoria, LocalDate fecha, Estadistica estadistica) {
        if (datos.containsKey(categoria)) {
            datos.get(categoria).put(fecha, estadistica);
            fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
        }
    }

    @Override
    public int getRowCount() {
        return rows.size(); // Número de categorías (filas)
    }

    @Override
    public int getColumnCount() {
        return fechas.size(); // Número de fechas (columnas)
    }

    @Override
    public String getColumnName(int column) {
        return fechas.get(column).toString(); // Devuelve el nombre de la fecha para la columna
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria categoria = rows.get(rowIndex); // Obtiene la categoría correspondiente a la fila
        LocalDate fecha = fechas.get(columnIndex); // Obtiene la fecha correspondiente a la columna
        Estadistica estadistica = datos.get(categoria).get(fecha); // Obtiene la estadística de esa categoría y fecha
        return estadistica != null ? estadistica.getResultado(categoria,fecha) : null; // Retorna el resultado de la estadística o null si no existe
    }

    @Override
    protected Object getPropertyAt(Categoria categoria, int col) {
        LocalDate fecha = fechas.get(col); // Obtiene la fecha asociada a la columna
        Estadistica estadistica = datos.get(categoria).get(fecha); // Obtiene la estadística para esa categoría y fecha
        return estadistica != null ? estadistica.getResultado(categoria,fecha) : null; // Retorna el resultado o null si no existe
    }

    @Override
    protected void initColNames() {

    }

    // Método para agregar una nueva fecha (columna)
    public void addFecha(LocalDate fecha) {
        fechas.add(fecha); // Añade la nueva fecha
        for (Categoria categoria : rows) {
            datos.get(categoria).put(fecha, null); // Inicializa la nueva fecha para todas las categorías
        }
        fireTableStructureChanged(); // Notifica que la estructura de la tabla ha cambiado
    }


    // Método para agregar una nueva categoría (fila)
    public void addCategoria(Categoria categoria) {
        rows.add(categoria); // Añade la nueva categoría
        datos.put(categoria, new HashMap<>()); // Inicializa el mapa de estadísticas para esa categoría
        for (LocalDate fecha : fechas) {
            datos.get(categoria).put(fecha, null); // Inicializa las estadísticas para todas las fechas existentes
        }

        fireTableRowsInserted(rows.size() - 1, rows.size() - 1); // Notifica que se ha añadido una nueva fila
    }

    private boolean isDateInRange(LocalDate date, int startMonth, int startYear, int endMonth, int endYear) {
        // Crear fechas de inicio y fin
        LocalDate startDate = LocalDate.of(startYear, startMonth, 1);
        LocalDate endDate = LocalDate.of(endYear, endMonth, getLastDayOfMonth(endMonth, endYear));

        // Verificar si la fecha está dentro del rango
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }

    private int getLastDayOfMonth(int month, int year) {
        // Usar el método de LocalDate para obtener el último día del mes
        return LocalDate.of(year, month, 1).lengthOfMonth();
    }

}
