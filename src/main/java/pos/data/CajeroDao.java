package pos.data;

import pos.logic.Cajero;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CajeroDao {

    Database db;

    public CajeroDao() {
        db = Database.instance();
    }

    public void create(Cajero e) throws Exception {
        String sql = "insert into " +
                "Cajero " +
                "(id , nombre) " +
                "values(?,?)";

        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        stm.setString(2, e.getNombre());
        db.executeUpdate(stm);
    }

    public Cajero read(String id) throws Exception
    {
        String sql = "select " +
                "* " +
                "from  Cajero c " +
                "where c.id=?";

        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);

        if (rs.next())
        {
            Cajero r = from(rs, "c");

            return r;
        }
        else {
            throw new Exception("Cajero NO EXISTE");
        }
    }

    public void update(Cajero e) throws Exception {
        String sql = "update " +
                "Cajero " +
                "set nombre=?" +
                "where id=?";

        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNombre());
        stm.setString(2, e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Cajero NO EXISTE");
        }
    }

    public void delete(Cajero e) throws Exception
    {
        String sql = "delete " +
                "from Cajero " +
                "where id=?";

        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        int count = db.executeUpdate(stm);

        if (count == 0) {
            throw new Exception("Cajero NO EXISTE");
        }
    }

    public List<Cajero> search(Cajero e) throws Exception
    {
        List<Cajero> resultado = new ArrayList<Cajero>();
        String sql = "select * " +
                "from " +
                "Cajero c " +
                "where c.nombre like ?";

        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(stm);

        while (rs.next()) {
            Cajero r = from(rs, "c");
            resultado.add(r);
        }
        return resultado;
    }

    public Cajero from(ResultSet rs, String alias) throws Exception
    {
        Cajero e = new Cajero();
        e.setId(rs.getString(alias + ".id"));
        e.setNombre(rs.getString(alias + ".nombre"));
        return e;
    }
}
