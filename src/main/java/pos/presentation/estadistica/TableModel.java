package pos.presentation.estadistica;

import pos.presentation.AbstractTableModel;
import pos.logic.Categoria;

import java.util.List;

public class TableModel extends AbstractTableModel<Categoria> implements javax.swing.table.TableModel {
    /* Constantes que representan las columnas fijas */
    public static final int CATEGORIA = 0;

    private int month1, year1, month2, year2; // Rango de fechas seleccionado

    // Constructor que inicializa la tabla con columnas y filas especificadas
    public TableModel(List<Categoria> rows) {
        super(null, rows);  // Las columnas se inicializan en null y luego se establecen dinámicamente
        initColNames(0);    // Inicializar con 0 columnas de fechas
    }

    // Método que actualiza el rango de fechas y ajusta las columnas dinámicamente
    public void updateDateRange(int month1, int year1, int month2, int year2) {
        this.month1 = month1;
        this.year1 = year1;
        this.month2 = month2;
        this.year2 = year2;

        // Calcular la cantidad de meses entre las fechas seleccionadas
        int numberOfMonths = calculateNumberOfMonths(month1, year1, month2, year2);
        cols = new int[numberOfMonths + 1]; // +1 porque siempre tiene la columna "Categoria"
        cols[CATEGORIA] = CATEGORIA;

        // Actualizar los nombres de las columnas
        initColNames(numberOfMonths);

        // Notificar que la estructura de la tabla cambió
        fireTableStructureChanged();
    }

    // Inicializa los nombres de las columnas del modelo de la tabla
    protected void initColNames(int numberOfMonths) {
        colNames = new String[numberOfMonths + 1]; // Siempre tiene la columna "Categoria"
        colNames[CATEGORIA] = "Categoria";

        int currentMonth = month1;
        int currentYear = year1;

        // Generar nombres para las columnas de fechas
        for (int i = 1; i <= numberOfMonths; i++) {
            colNames[i] = getMonthName(currentMonth) + " " + currentYear;
            currentMonth++;
            if (currentMonth > 12) { // Si el mes pasa de diciembre, avanzamos al siguiente año
                currentMonth = 1;
                currentYear++;
            }
        }
    }

    // Obtiene el valor de la propiedad correspondiente a una columna específica de una categoría
    @Override
    protected Object getPropertyAt(Categoria categoria, int col) {
        if (col == CATEGORIA) {
            return categoria.getTipo();
        } else if (col >= 1 && col < cols.length) {
            // Devuelve valores simulados para las fechas, en tu caso puedes extraer datos reales
            return "Valor " + colNames[col];
        }
        return "";
    }

    @Override
    protected void initColNames() {

    }

    // Método que calcula la cantidad de meses entre dos fechas
    private int calculateNumberOfMonths(int month1, int year1, int month2, int year2) {
        return (month2 - month1 + 1) + ((year2 - year1) * 12);
    }

    // Retorna el nombre del mes dado su número
    private String getMonthName(int month) {
        String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return monthNames[month - 1];
    }

    // Retorna el número de columnas (siempre una para "Categoria" más las fechas)
    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    // Retorna el número de filas (tantas como categorías)
    @Override
    public int getRowCount() {
        return rows.size();
    }

    // Retorna el nombre de la columna en una posición específica
    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    // Retorna el valor de una celda en la fila y columna especificada
    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        Categoria categoria = rows.get(rowIndex);
        return getPropertyAt(categoria, colIndex);
    }
}
