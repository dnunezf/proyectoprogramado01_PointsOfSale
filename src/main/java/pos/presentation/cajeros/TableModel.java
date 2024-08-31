package pos.presentation.cajeros;

import pos.logic.Cajero;
import pos.presentation.AbstractTableModel;

import java.util.List;

/*Clase TableModel, modelo de tabla que gestiona una lista de cajeros.
 * Especifica la estructura y datos de las columnas relacionadas con los cajeros.
 * Hereda de AbstractTableModel.*/
public class TableModel extends AbstractTableModel<Cajero> implements javax.swing.table.TableModel
{
    /*Constantes que representan las columnas de la tabla*/
    public static final int ID = 0;
    public static final int NOMBRE = 1;

    /*Constructor que inicializa la tabla con columnas y filas especificadas*/
    public TableModel(int[] cols, List<Cajero> rows)
    {
        super(cols, rows);
    }

    /*Obtiene el valor de la propiedad correspondiente a una columna
     * específica, de un cajero en particular*/
    @Override
    protected Object getPropertyAt(Cajero cajero, int col)
    {
        switch (cols[col])
        {
            case ID: return cajero.getId();
            case NOMBRE: return cajero.getNombre();
            default: return "";
        }
    }

    /*Inicializa los nombres de las columnas del modelo de la tabla
     * Asocia cada columna con su nombre*/
    @Override
    protected void initColNames()
    {
        colNames = new String[2];  // Solo dos columnas

        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
    }
}
