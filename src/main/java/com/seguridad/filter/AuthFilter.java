package com.seguridad.filter;

import com.seguridad.util.JsonUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
    String path = req.getRequestURI();

     if (path.contains("/app/") || path.endsWith(".html")) {
        resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
        JsonUtils.sendError(resp, 401, "No autorizado. Debe iniciar sesión.");
        }
    return;
      }

        String path = req.getRequestURI();
        if (path.contains("/api/usuarios")) {
            String rol = (String) session.getAttribute("rol");
            if (!"admin".equals(rol)) {
                JsonUtils.sendError(resp, 403, "Acceso denegado. Se requiere rol de administrador.");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
