package pos.data;

import pos.logic.Categoria;

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
                "Categoria c " +
                "where c.tipo like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getTipo() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Categoria r= from(rs, "c");
            resultado.add(r);
        }
        return resultado;
    }

    public Categoria from(ResultSet rs, String alias) throws Exception {
        Categoria e = new Categoria();
        e.setId(rs.getString(alias + ".id"));
        e.setTipo(rs.getString(alias + ".tipo"));
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
