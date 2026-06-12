package com.seguridad.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.seguridad.model.Producto;
import org.junit.jupiter.api.*;
import java.util.List;

class ProductoDaoTest {
    private static ProductoDao dao;

    @BeforeAll
    static void setup() {
        dao = new ProductoDao();
    }

    @Test
    void buscarProductos_retornaResultados() throws Exception {
        List<Producto> todos = dao.listarTodos();
        assertNotNull(todos);
        assertFalse(todos.isEmpty(), "Debe haber productos en la BD");

        List<Producto> encontrados = dao.buscar("Laptop");
        assertFalse(encontrados.isEmpty(), "Debe encontrar productos con 'Laptop'");
        assertTrue(encontrados.stream().anyMatch(p -> p.getNombre().contains("Laptop")));
    }
}
