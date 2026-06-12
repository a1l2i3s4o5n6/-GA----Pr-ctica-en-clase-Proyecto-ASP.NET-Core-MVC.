package com.seguridad.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    @Test
    void detectaSqlInjection() {
        assertTrue(InputValidator.isSqlInjection("' OR 1=1 --"));
        assertTrue(InputValidator.isSqlInjection("admin'--"));
        assertTrue(InputValidator.isSqlInjection("' UNION SELECT * FROM usuarios --"));
        assertTrue(InputValidator.isSqlInjection("DROP TABLE usuarios"));
        assertTrue(InputValidator.isSqlInjection("'; EXEC xp_cmdshell --"));
        assertFalse(InputValidator.isSqlInjection("admin"));
        assertFalse(InputValidator.isSqlInjection("usuario_normal"));
        assertFalse(InputValidator.isSqlInjection("José Pérez"));
    }
}
