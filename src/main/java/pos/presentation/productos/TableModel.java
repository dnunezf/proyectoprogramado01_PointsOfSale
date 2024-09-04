package pos.presentation.productos;

import pos.logic.Producto;
import pos.presentation.AbstractTableModel;

import java.util.List;

/*Clase TableModel, modelo de tabla que gestiona una lista de productos.
 * Especifica la estructura y datos de las columnas relacionadas con los productos
 * Hereda de AbstractTableModel*/

public class TableModel extends AbstractTableModel<Producto> implements javax.swing.table.TableModel
{
    /*Constantes que representan las columnas de la tabla*/
    public static final int CODIGO = 0;
    public static final int DESCRIPCION = 1;
    public static final int UNIDAD = 2;
    public static final int PRECIO = 3;
    public static final int CATEGORIA = 4;
    public static final int EXISTENCIAS = 5;

    /*Constructor que inicializa la tabla con columnas y filas especificadas*/
    public TableModel(int[] cols, List<Producto> rows)
    {
        super(cols, rows);
    }

    /*Obtiene el valor de la propiedad correspondiente a una columna
     * especifica, de un producto en particular*/
    @Override
    protected Object getPropertyAt(Producto producto, int col)
    {
        switch (cols[col])
        {
            case CODIGO: return producto.getCodigo();

            case DESCRIPCION: return producto.getDescripcion();

            case UNIDAD: return producto.getUnidad();

            case PRECIO: return producto.getPrecioUnitario();

            case CATEGORIA: return producto.getCategoria();

            case EXISTENCIAS: return producto.getExistencias();

            default: return "";
        }
    }

    /*Inicializa los nombres de las columnas del modelo de la tabla
     * Asocia cada columna con su nombre*/
    @Override
    protected void initColNames()
    {
        colNames = new String[6];

        colNames[CODIGO] = "Codigo";
        colNames[DESCRIPCION] = "Descripcion";
        colNames[UNIDAD] = "Unidad";
        colNames[PRECIO] = "Precio";
        colNames[CATEGORIA] = "Categoría";
        colNames[EXISTENCIAS] = "Existencias";
    }
}
