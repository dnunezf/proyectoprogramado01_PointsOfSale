package pos.presentation.clientes;

import pos.logic.Cliente;
import pos.presentation.AbstractTableModel;

import java.util.List;

/*Clase TableModel, modelo de tabla que gestiona una lista de clientes.
* Especifica la estructura y datos de las columnas relacionadas con los clientes
* Hereda de AbstractTableModel*/

public class TableModel extends AbstractTableModel<Cliente> implements javax.swing.table.TableModel
{
    /*Constantes que representan las columnas de la tabla*/
    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;
    public static final int EMAIL = 3;
    public static final int DESCUENTO = 4;

    /*Constructor que inicializa la tabla con columnas y filas especificadas*/
    public TableModel(int[] cols, List<Cliente> rows)
    {
        super(cols, rows);
    }

    /*Obtiene el valor de la propiedad correspondiente a una columna
    * especifica, de un cliente en particular*/
    @Override
    protected Object getPropertyAt(Cliente cliente, int col)
    {
        switch (cols[col])
        {
            case ID: return cliente.getId();

            case NOMBRE: return cliente.getNombre();

            case TELEFONO: return cliente.getTelefono();

            case EMAIL: return cliente.getEmail();

            case DESCUENTO: return cliente.getDescuento();

            default: return "";
        }
    }

    /*Inicializa los nombres de las columnas del modelo de la tabla
    * Asocia cada columna con su nombre*/
    @Override
    protected void initColNames()
    {
        colNames = new String[5];

        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[TELEFONO] = "Telefono";
        colNames[EMAIL] = "Email";
        colNames[DESCUENTO] = "Descuento";
    }
}
