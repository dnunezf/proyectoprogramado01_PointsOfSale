package pos.data;

import pos.logic.Factura;
import pos.logic.Cliente;
import pos.logic.Cajero;
import pos.logic.Linea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao {
    Database db;

    public FacturaDao() {
        db = Database.instance();
    }

    public void create(Factura e) throws Exception {
        String sql = "insert into Factura (numeroDeFactura, fecha, cliente, cajero) values (?, ?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumeroDeFactura());
        stm.setDate(2, java.sql.Date.valueOf(e.getFecha()));
        stm.setString(3, e.getCliente().getId());
        stm.setString(4, e.getCajero().getId());
        db.executeUpdate(stm);

        // Insertar las líneas de la factura
        LineaDao lineaDao = new LineaDao();
        for (Linea linea : e.getLineas()) {
            linea.setFactura(e);  // Asignar la factura a cada línea
            lineaDao.create(linea);
        }
    }

    public Factura read(String numeroDeFactura) throws Exception {
        String sql = "select * from Factura f inner join Cliente c on f.cliente = c.id inner join Cajero cj on f.cajero = cj.id where f.numeroDeFactura = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, numeroDeFactura);
        ResultSet rs = db.executeQuery(stm);
        ClienteDao clienteDao = new ClienteDao();
        CajeroDao cajeroDao = new CajeroDao();
        if (rs.next()) {
            Factura r = from(rs, "f");
            r.setCliente(clienteDao.from(rs, "c"));
            r.setCajero(cajeroDao.from(rs, "cj"));

            // Leer las líneas de la factura
            LineaDao lineaDao = new LineaDao();
            List<Linea> lineas = lineaDao.search(new Linea(null, r));
            r.setLineas(lineas);

            return r;
        } else {
            throw new Exception("Factura NO EXISTE");
        }
    }

    public void update(Factura e) throws Exception {
        String sql = "update Factura set fecha = ?, cliente = ?, cajero = ? where numeroDeFactura = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setDate(1, java.sql.Date.valueOf(e.getFecha()));
        stm.setString(2, e.getCliente().getId());
        stm.setString(3, e.getCajero().getId());
        stm.setString(5, e.getNumeroDeFactura());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Factura NO EXISTE");
        }

        // Actualizar las líneas de la factura
        LineaDao lineaDao = new LineaDao();
        for (Linea linea : e.getLineas()) {
            linea.setFactura(e);
            lineaDao.update(linea);
        }
    }

    public void delete(Factura e) throws Exception {
        // Primero eliminar las líneas de la factura
        LineaDao lineaDao = new LineaDao();
        for (Linea linea : e.getLineas()) {
            lineaDao.delete(linea);
        }

        String sql = "delete from Factura where numeroDeFactura = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumeroDeFactura());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Factura NO EXISTE");
        }
    }

    public List<Factura> search(Factura e) throws Exception {
        List<Factura> resultado = new ArrayList<>();
        String sql = "select * from Factura f inner join Cliente c on f.cliente = c.id inner join Cajero cj on f.cajero = cj.id where f.cliente = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCliente().getId());
        ResultSet rs = db.executeQuery(stm);
        ClienteDao clienteDao = new ClienteDao();
        CajeroDao cajeroDao = new CajeroDao();
        while (rs.next()) {
            Factura r = from(rs, "f");
            r.setCliente(clienteDao.from(rs, "c"));
            r.setCajero(cajeroDao.from(rs, "cj"));

            // Leer las líneas de la factura
            LineaDao lineaDao = new LineaDao();
            List<Linea> lineas = lineaDao.search(new Linea(null, r));
            r.setLineas(lineas);

            resultado.add(r);
        }
        return resultado;
    }

    public Factura from(ResultSet rs, String alias) throws Exception {
        Factura e = new Factura();
        e.setNumeroDeFactura(rs.getString(alias + ".numeroDeFactura"));
        e.setFecha(rs.getDate(alias + ".fecha").toLocalDate());
        return e;
    }
}
