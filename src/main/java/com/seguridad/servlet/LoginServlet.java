package com.seguridad.servlet;

import com.seguridad.dao.UsuarioDao;
import com.seguridad.util.InputValidator;
import com.seguridad.util.JsonUtils;
import com.seguridad.util.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String usuario = req.getParameter("usuario");
            String password = req.getParameter("password");

            if (usuario == null || password == null || usuario.isBlank() || password.isBlank()) {
                JsonUtils.sendError(resp, 400, "Usuario y contraseña son requeridos");
                return;
            }

            if (InputValidator.isSqlInjection(usuario)) {
                JsonUtils.sendError(resp, 400, "Caracteres no permitidos en el usuario");
                return;
            }

            var user = usuarioDao.buscarPorUsername(usuario);
            if (user == null || !PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
                JsonUtils.sendError(resp, 401, "Credenciales inválidas");
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("usuario", user.getUsuario());
            session.setAttribute("nombre", user.getNombre());
            session.setAttribute("rol", user.getRol());
            session.setAttribute("userId", user.getId());

            JsonUtils.send(resp, java.util.Map.of(
                "success", true,
                "nombre", user.getNombre(),
                "rol", user.getRol()
            ));
        } catch (Exception e) {
            JsonUtils.sendError(resp, 500, "Error interno del servidor");
        }
    }
}
