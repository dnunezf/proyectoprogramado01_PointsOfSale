package pos.presentation.clientes;

import pos.logic.Cliente;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Cliente> implements javax.swing.table.TableModel
{


    public TableModel(int[] cols, List<Cliente> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropertyAt(Cliente cliente, int col) {
        return null;
    }

    @Override
    protected void initColNames() {

    }
}
