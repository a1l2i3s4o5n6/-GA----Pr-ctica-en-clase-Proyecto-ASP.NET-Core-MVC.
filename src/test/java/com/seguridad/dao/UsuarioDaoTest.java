package com.seguridad.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.seguridad.model.Usuario;
import org.junit.jupiter.api.*;

class UsuarioDaoTest {
    private static UsuarioDao dao;

    @BeforeAll
    static void setup() {
        dao = new UsuarioDao();
    }

    @Test
    void buscarPorUsername_encuentraAdmin() throws Exception {
        Usuario user = dao.buscarPorUsername("admin");
        assertNotNull(user, "El usuario 'admin' debe existir en la BD");
        assertEquals("admin", user.getUsuario());
        assertEquals("Administrador", user.getNombre());
        assertEquals("admin", user.getRol());
        assertTrue(user.isActivo());
    }
}
