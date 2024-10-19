package pos.data;

import pos.logic.Factura;
import pos.logic.Cliente;
import pos.logic.Cajero;
import pos.logic.Linea;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao {
    Database db;

    public FacturaDao() {
        db = Database.instance();
    }

    public void create(Factura e) throws Exception {
        if (e.getCliente() == null || e.getCajero() == null) {
            throw new IllegalArgumentException("El cliente o cajero no pueden ser nulos.");
        }

        String sql = "INSERT INTO Factura (numeroDeFactura, fecha, cliente, cajero, metodoDePago) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, e.getNumeroDeFactura());
            stm.setDate(2, java.sql.Date.valueOf(e.getFecha()));
            stm.setString(3, e.getCliente().getId());
            stm.setString(4, e.getCajero().getId());
            stm.setInt(5, e.getMetodoDePago());

            db.executeUpdate(stm);  // Ejecutar la inserción

            // Insertar las líneas de la factura
            LineaDao lineaDao = new LineaDao();
            for (Linea linea : e.getLineas()) {
                linea.setFactura(e);  // Asignar la factura a cada línea
                lineaDao.create(linea);  // Crear la línea en la BD
            }
        } catch (SQLException ex) {
            throw new Exception("Error al crear la factura: " + ex.getMessage(), ex);
        }
    }

    public Factura read(String numeroDeFactura) throws Exception {
        String sql = "SELECT * FROM Factura f INNER JOIN Cliente c ON f.cliente = c.id INNER JOIN Cajero cj ON f.cajero = cj.id WHERE f.numeroDeFactura = ?";
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
        String sql = "UPDATE Factura SET fecha = ?, cliente = ?, cajero = ?, metodoDePago = ? WHERE numeroDeFactura = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setDate(1, java.sql.Date.valueOf(e.getFecha()));
        stm.setString(2, e.getCliente().getId());
        stm.setString(3, e.getCajero().getId());
        stm.setString(4, e.getNumeroDeFactura());
        stm.setInt(5, e.getMetodoDePago());
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

        String sql = "DELETE FROM Factura WHERE numeroDeFactura = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumeroDeFactura());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Factura NO EXISTE");
        }
    }

    public List<Factura> search(Factura e) throws Exception {
        List<Factura> resultado = new ArrayList<>();

        if (e.getCliente() == null || e.getCliente().getId() == null) {
            throw new IllegalArgumentException("El cliente o su ID es nulo.");
        }

        String sql = "SELECT * FROM Factura f "
                + "INNER JOIN Cliente c ON f.cliente = c.id "
                + "INNER JOIN Cajero cj ON f.cajero = cj.id "
                + "WHERE f.cliente = ?";

        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, e.getCliente().getId());

            try (ResultSet rs = db.executeQuery(stm)) {
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron facturas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return resultado;
                }

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
            }
        } catch (SQLException ex) {
            throw new Exception("Error en la consulta de facturas: " + ex.getMessage(), ex);
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
