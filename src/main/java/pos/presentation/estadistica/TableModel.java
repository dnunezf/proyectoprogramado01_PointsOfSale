package pos.presentation.estadistica;

import pos.presentation.AbstractTableModel;
import pos.logic.Categoria;

import java.beans.FeatureDescriptor;
import java.util.List;

public class TableModel extends AbstractTableModel<Categoria> implements javax.swing.table.TableModel {
    /*Constantes que representan las columnas de la tabla*/
    public static final int CATEGORIA = 0;
    public static final int FECHA1 = 1;
    public static final int FECHA2 = 2;
    public static final int FECHA3 = 3;

    /*Constructor que inicializa la tabla con columnas y filas especificadas*/
    public TableModel(int[] cols, List<Categoria> rows)
    {
        super(cols, rows);
    }

    /*Obtiene el valor de la propiedad correspondiente a una columna
     * especifica, de un cliente en particular*/
    @Override
    protected Object getPropertyAt(Categoria categoria, int col)
    {
        switch (cols[col])
        {
          case CATEGORIA: return categoria.getTipo();

          case FECHA1: return 0;

          case FECHA2: return 1;

          case FECHA3: return 2;

            default: return "";
        }
    }

    /*Inicializa los nombres de las columnas del modelo de la tabla
     * Asocia cada columna con su nombre*/
    @Override
    protected void initColNames()
    {
        colNames = new String[4];

        colNames[CATEGORIA] = "Categoria";
        colNames[FECHA1] = "";
        colNames[FECHA2] = "";
        colNames[FECHA3] = "";

    }
}
