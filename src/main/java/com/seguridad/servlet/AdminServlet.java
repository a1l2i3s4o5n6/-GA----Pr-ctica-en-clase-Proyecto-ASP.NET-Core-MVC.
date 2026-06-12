package com.seguridad.servlet;

import com.seguridad.dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }
        String rol = (String) session.getAttribute("rol");
        if (!"admin".equals(rol)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard.html");
            return;
        }
        try {
            req.setAttribute("usuarios", usuarioDao.listar());
        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar usuarios: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
    }
}
