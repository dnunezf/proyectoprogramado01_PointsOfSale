package pos.data;

import pos.logic.Linea;
import pos.logic.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LineaDao {
    Database db;

    public LineaDao() {
        db = Database.instance();
    }

    public void create(Linea e) throws Exception {
        String sql = "INSERT INTO Linea (numeroDeLinea, productoVendido, cantidadVendida, factura, descuento, descuentoCliente) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumeroDeLinea());
        stm.setString(2, e.getProductoVendido().getCodigo());
        stm.setInt(3, e.getCantidadVendida());
        stm.setString(4, e.getFactura().getNumeroDeFactura());
        stm.setFloat(5, e.getDescuento());
        stm.setFloat(6, e.getDescuentoCliente());
        db.executeUpdate(stm);
    }

    public Linea read(String numeroDeLinea) throws Exception {
        String sql = "SELECT * FROM Linea t INNER JOIN Producto p ON t.productoVendido = p.codigo WHERE t.numeroDeLinea = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, numeroDeLinea);
        ResultSet rs = db.executeQuery(stm);
        ProductoDao productoDao = new ProductoDao();
        if (rs.next()) {
            Linea r = from(rs, "t");
            r.setProductoVendido(productoDao.from(rs, "p"));
            return r;
        } else {
            throw new Exception("Linea NO EXISTE");
        }
    }

    public void update(Linea e) throws Exception {
        String sql = "UPDATE Linea SET productoVendido = ?, cantidadVendida = ?, factura = ?, descuento = ?, descuentoCliente = ? WHERE numeroDeLinea = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getProductoVendido().getCodigo());
        stm.setInt(2, e.getCantidadVendida());
        stm.setString(3, e.getFactura().getNumeroDeFactura());
        stm.setFloat(4, e.getDescuento());
        stm.setFloat(5, e.getDescuentoCliente());
        stm.setString(6, e.getNumeroDeLinea());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Linea NO EXISTE");
        }
    }

    public void delete(Linea e) throws Exception {
        String sql = "DELETE FROM Linea WHERE numeroDeLinea = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumeroDeLinea());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Linea NO EXISTE");
        }
    }

    public List<Linea> search(Linea e) throws Exception {
        List<Linea> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Linea t INNER JOIN Producto p ON t.productoVendido = p.codigo WHERE t.factura = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getFactura().getNumeroDeFactura());
        ResultSet rs = db.executeQuery(stm);
        ProductoDao productoDao = new ProductoDao();
        while (rs.next()) {
            Linea r = from(rs, "t");
            r.setProductoVendido(productoDao.from(rs, "p"));
            resultado.add(r);
        }
        return resultado;
    }

    public Linea from(ResultSet rs, String alias) throws Exception {
        Linea e = new Linea();
        e.setNumeroDeLinea(rs.getString(alias + ".numeroDeLinea"));
        e.setCantidadVendida(rs.getInt(alias + ".cantidadVendida"));
        e.setDescuento(rs.getFloat(alias + ".descuento"));
        e.setDescuentoCliente(rs.getFloat(alias + ".descuentoCliente"));
        return e;
    }
}
