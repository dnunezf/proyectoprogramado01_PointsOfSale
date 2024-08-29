package pos.presentation;

import pos.logic.Cliente;
import java.util.List;

/*Clase AbstractTableModel, permite manejar la representacion y manipulacion de datos en componentes JTable*/

public abstract class AbstractTableModel<E> extends javax.swing.table.AbstractTableModel implements javax.swing.table.TableModel
{
    /*Lista que almacena las filas de datos del JTable*/
    protected List<E> rows;
    /*Arreglo que representa los indices de las columnas a mostrar en la tabla*/
    protected int[] cols;
    /*Arreglo que almacena los nombres de las columnas de la tabla*/
    protected String[] colNames;

    /*Constructor, inicializa el modelo de la tabla con las columnas y filas proporcionadas*/
    public AbstractTableModel(int[] cols, List<E> rows)
    {
        this.cols = cols;
        this.rows = rows;
        initColNames();
    }

    /*Devuelve el numero de columnas de la tabla*/
    public int getColumnCount()
    {
        return cols.length;
    }

    /*Devuelve el nombre de la columna especificada*/
    public String getColumnName(int col)
    {
        return colNames[cols[col]];
    }

    /*Devuelve la clase de datos de la columna especificada*/
    public Class<?> getColumnClass(int col)
    {
        switch (cols[col])
        {
            default: return super.getColumnClass(col);
        }
    }

    /*Devuelve el numero de filas en la tabla*/
    public int getRowCount()
    {
        return rows.size();
    }

    /*Devuelve el valor de la celda en la posicion especificada*/
    public Object getValueAt(int row, int col)
    {
        E e = rows.get(row);

        return getPropertyAt(e, col);
    }

    /*Obtiene el valor de la propiedad de un objeto, en una columna especifica*/
    protected abstract Object getPropertyAt(E e, int col);

    /*Devuelve el objeto en la fila especificada*/
    public E getRowAt(int row)
    {
        return rows.get(row);
    }

    /*Inicializa los nombres de las columnas de la tabla*/
    protected abstract void initColNames();
}
