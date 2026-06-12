package com.seguridad.servlet;

import com.seguridad.dao.UsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.util.JsonUtils;
import com.seguridad.util.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsuarioServlet extends HttpServlet {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            JsonUtils.send(resp, usuarioDao.listar());
        } catch (Exception e) {
            JsonUtils.sendError(resp, 500, "Error al listar usuarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String usuario = req.getParameter("usuario");
            String password = req.getParameter("password");
            String nombre = req.getParameter("nombre");
            String rol = req.getParameter("rol");

            if (usuario == null || password == null || nombre == null) {
                JsonUtils.sendError(resp, 400, "Todos los campos son requeridos");
                return;
            }

            Usuario u = new Usuario();
            u.setUsuario(usuario);
            u.setNombre(nombre);
            u.setRol(rol != null ? rol : "user");

            String hash = PasswordUtils.hashPassword(password);
            boolean creado = usuarioDao.crear(u, hash);

            if (creado) {
                JsonUtils.send(resp, java.util.Map.of("success", true, "message", "Usuario creado"));
            } else {
                JsonUtils.sendError(resp, 500, "Error al crear usuario");
            }
        } catch (Exception e) {
            JsonUtils.sendError(resp, 500, "Error al crear usuario");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            if (idStr == null) {
                JsonUtils.sendError(resp, 400, "ID requerido");
                return;
            }
            boolean eliminado = usuarioDao.eliminar(Integer.parseInt(idStr));
            if (eliminado) {
                JsonUtils.send(resp, java.util.Map.of("success", true, "message", "Usuario eliminado"));
            } else {
                JsonUtils.sendError(resp, 404, "Usuario no encontrado");
            }
        } catch (Exception e) {
            JsonUtils.sendError(resp, 500, "Error al eliminar usuario");
        }
    }
}
