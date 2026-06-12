package com.seguridad.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PasswordUtilsTest {

    @Test
    void bcryptHashAndVerify() {
        String password = "testPassword123!";
        String hash = PasswordUtils.hashPassword(password);
        assertNotNull(hash);
        assertTrue(hash.startsWith("$2a$"));
        assertTrue(PasswordUtils.verifyPassword(password, hash));
        assertFalse(PasswordUtils.verifyPassword("wrongPassword", hash));
    }
}
