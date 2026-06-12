package com.seguridad.servlet;

import com.seguridad.dao.ProductoDao;
import com.seguridad.util.InputValidator;
import com.seguridad.util.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductoServlet extends HttpServlet {
    private final ProductoDao productoDao = new ProductoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String buscar = req.getParameter("buscar");

            if (buscar != null && !buscar.isBlank()) {
                if (InputValidator.isSqlInjection(buscar)) {
                    JsonUtils.sendError(resp, 400, "Caracteres no permitidos en la búsqueda");
                    return;
                }
                JsonUtils.send(resp, productoDao.buscar(buscar));
            } else {
                JsonUtils.send(resp, productoDao.listarTodos());
            }
        } catch (Exception e) {
            JsonUtils.sendError(resp, 500, "Error al buscar productos");
        }
    }
}
