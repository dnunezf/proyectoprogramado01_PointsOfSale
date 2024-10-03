package pos.data;

import pos.logic.Categoria;
import pos.logic.Cliente;
import pos.logic.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    Database db;

    public CategoriaDao() {
        db = Database.instance();
    }

    public List<Categoria> search(Categoria e) throws Exception {
        List<Categoria> resultado = new ArrayList<Categoria>();
        String sql = "select * " +
                "from " +
                "Categoria t " +
                "where t.nombre like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getTipo() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Categoria r= from(rs, "t");
            resultado.add(r);
        }
        return resultado;
    }

    public Categoria from(ResultSet rs, String alias) throws Exception {
        Categoria e = new Categoria();
        e.setId(rs.getString(alias + ".id"));
        e.setTipo(rs.getString(alias + ".nombre"));
        return e;
    }

    public Categoria read(String id) throws Exception {
        String sql = "select " +
                "* " +
                "from  Categoria t " +
                "where t.tipo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);

        if (rs.next()) {

            return from(rs, "t");
        } else {
            throw new Exception("Categoria NO EXISTE");
        }
    }

    public void delete(Categoria e) throws Exception {
        String sql = "delete " +
                "from Categoria " +
                "where tipo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Categoria NO EXISTE");
        }
    }
}
