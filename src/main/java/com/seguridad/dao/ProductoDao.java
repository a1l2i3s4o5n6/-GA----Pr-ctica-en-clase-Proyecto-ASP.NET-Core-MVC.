package com.seguridad.dao;

import com.seguridad.config.ConnectionFactory;
import com.seguridad.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    public List<Producto> buscar(String termino) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, categoria, stock, activo " +
                     "FROM productos WHERE activo = 1 AND " +
                     "(nombre LIKE ? OR descripcion LIKE ? OR categoria LIKE ?) " +
                     "ORDER BY nombre";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + (termino != null ? termino : "") + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getInt("stock"),
                        rs.getBoolean("activo")
                    ));
                }
            }
        }
        return lista;
    }

    public List<Producto> listarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, categoria, stock, activo " +
                     "FROM productos WHERE activo = 1 ORDER BY nombre";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getString("categoria"),
                    rs.getInt("stock"),
                    rs.getBoolean("activo")
                ));
            }
        }
        return lista;
    }
}
