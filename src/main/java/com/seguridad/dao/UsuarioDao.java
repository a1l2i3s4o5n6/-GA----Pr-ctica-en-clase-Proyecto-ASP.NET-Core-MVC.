package com.seguridad.dao;

import com.seguridad.config.ConnectionFactory;
import com.seguridad.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT id, usuario, password_hash, nombre, rol, activo FROM usuarios WHERE usuario = ? AND activo = 1";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                    );
                    u.setPasswordHash(rs.getString("password_hash"));
                    return u;
                }
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, usuario, nombre, rol, activo FROM usuarios WHERE activo = 1 ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("nombre"),
                    rs.getString("rol"),
                    rs.getBoolean("activo")
                ));
            }
        }
        return lista;
    }

    public boolean crear(Usuario usuario, String passwordHash) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, password_hash, nombre, rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, passwordHash);
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getRol());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE usuarios SET activo = 0 WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
