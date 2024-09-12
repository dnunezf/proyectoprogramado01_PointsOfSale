package pos.presentation.facturar;

import pos.logic.Factura;
import pos.logic.Linea;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Linea> implements javax.swing.table.TableModel
{
    List<Linea> lineas;

    /*Constantes que representan las columnas de la tabla*/
    public static final int CODIGO = 0;
    public static final int ARTICULO = 1;
    public static final int CATEGORIA = 2;
    public static final int CANTIDAD = 3;
    public static final int PRECIO = 4;
    public static final int DESCUENTO = 5;
    public static final int NETO = 6;
    public static final int IMPORTE = 7;

    /*Constructor que inicializa la tabla con columnas y filas especificadas*/
    public TableModel(int[] cols, List<Linea> rows) {
        super(cols, rows);
        this.lineas = lineas;
    }


    /*Obtiene el valor de la propiedad correspondiente a una columna
     * especifica, de un cliente en particular*/
    @Override
    protected Object getPropertyAt(Linea linea, int col)
    {
        switch (cols[col])
        {
            case CODIGO: return linea.getProductoVendido().getCodigo();

            case ARTICULO: return linea.getProductoVendido().getDescripcion();

            case CATEGORIA: return linea.getProductoVendido().getCategoria().getTipo();

            case CANTIDAD: return linea.getCantidadVendida();

            case PRECIO: return linea.getProductoVendido().getPrecioUnitario();

            case DESCUENTO: return linea.getDescuentoTotal(); // Muestra el descuento aplicado

            case NETO: return linea.getNeto();

            case IMPORTE: return linea.getImporte();

            default: return "";
        }
    }

    /*Inicializa los nombres de las columnas del modelo de la tabla
     * Asocia cada columna con su nombre*/
    @Override
    protected void initColNames()
    {
        colNames = new String[8];

        colNames[CODIGO] = "Codigo";
        colNames[ARTICULO] = "Articulo";
        colNames[CATEGORIA] = "Categoria";
        colNames[CANTIDAD] = "Cantidad";
        colNames[PRECIO] = "Precio";
        colNames[DESCUENTO] = "Descuento";
        colNames[NETO] = "Neto";
        colNames[IMPORTE] = "Importe";
    }
}
