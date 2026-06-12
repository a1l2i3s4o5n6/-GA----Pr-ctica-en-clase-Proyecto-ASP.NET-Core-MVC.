package com.seguridad.servlet;

import com.seguridad.dao.UsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.util.InputValidator;
import com.seguridad.util.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String usuario = req.getParameter("usuario");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String nombre = req.getParameter("nombre");
        String rol = req.getParameter("rol");

        Map<String, String> errors = new HashMap<>();

        if (usuario == null || usuario.isBlank())
            errors.put("usuario", "El usuario es requerido");
        else if (InputValidator.isSqlInjection(usuario))
            errors.put("usuario", "Caracteres no permitidos en el usuario");

        if (password == null || password.length() < 6)
            errors.put("password", "La contraseña debe tener al menos 6 caracteres");

        if (confirmPassword == null || !confirmPassword.equals(password))
            errors.put("confirmPassword", "Las contraseñas no coinciden");

        if (nombre == null || nombre.isBlank())
            errors.put("nombre", "El nombre es requerido");

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("oldInput", Map.of(
                "usuario", usuario != null ? usuario : "",
                "nombre", nombre != null ? nombre : "",
                "rol", rol != null ? rol : "user"
            ));
            req.getRequestDispatcher("register.html").forward(req, resp);
            return;
        }

        try {
            Usuario u = new Usuario();
            u.setUsuario(usuario.trim());
            u.setNombre(nombre.trim());
            u.setRol(rol != null && rol.equals("admin") ? "admin" : "user");

            String hash = PasswordUtils.hashPassword(password);
            boolean creado = usuarioDao.crear(u, hash);

            if (creado) {
                resp.sendRedirect("index.html?registro=exitoso");
            } else {
                errors.put("general", "Error al crear usuario. Intente nuevamente.");
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("register.html").forward(req, resp);
            }
        } catch (Exception e) {
            errors.put("general", "Error interno del servidor");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("register.html").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.html").forward(req, resp);
    }
}
