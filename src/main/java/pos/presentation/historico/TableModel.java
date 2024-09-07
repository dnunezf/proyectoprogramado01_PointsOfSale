package pos.presentation.historico;

import pos.logic.*;
import pos.presentation.AbstractTableModel;

import java.util.List;

/*Clase TableModel, modelo de tabla que gestiona una lista de facturas asociadas al cliente y cajero.
 * Especifica la estructura y datos de las columnas relacionadas con datos de la factura
 * Hereda de AbstractTableModel*/

public class TableModel extends AbstractTableModel<Factura> implements javax.swing.table.TableModel
{
    /*Constantes que representan las columnas de la tabla*/
    public static final int NUMERO = 0;
    public static final int CLIENTE = 1;
    public static final int CAJERO = 2;
    public static final int FECHA = 3;
    public static final int IMPORTE = 4;

    /*Constructor que inicializa la tabla con columnas y filas especificadas*/
    public TableModel(int[] cols, List<Factura> rows)
    {
        super(cols, rows);
    }

    /*Obtiene el valor de la propiedad correspondiente a una columna
     * especifica, de datos de una factura en particular*/
    @Override
    protected Object getPropertyAt(Factura factura, int col)
    {
        switch (cols[col])
        {
            case NUMERO: return factura.getNumeroDeFactura();

            case CLIENTE: return factura.getCliente().getNombre();

            case CAJERO: return factura.getCajero().getNombre();

            case FECHA: return factura.getFecha().toString(); // Asume que `toString()` da una representación adecuada de la fecha

            case IMPORTE: return factura.calcularTotal(); // Total calculado para la factura

            default: return "";
        }
    }

    /*Inicializa los nombres de las columnas del modelo de la tabla
     * Asocia cada columna con su nombre*/
    @Override
    protected void initColNames()
    {
        colNames = new String[5];

        colNames[NUMERO] = "Número";
        colNames[CLIENTE] = "Cliente";
        colNames[CAJERO] = "Cajero";
        colNames[FECHA] = "Fecha";
        colNames[IMPORTE] = "Importe";
    }
}
