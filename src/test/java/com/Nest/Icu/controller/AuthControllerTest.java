package com.Nest.Icu.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.Nest.Icu.model.AuthRequest;
import org.junit.jupiter.api.Test;

class AuthControllerTest {
    /**
     * Method under test: {@link AuthController#generateToken(AuthRequest)}
     */
    @Test
    void testGenerateToken() throws Exception {


        AuthController authController = new AuthController();
        assertThrows(Exception.class, () -> authController.generateToken(new AuthRequest("janedoe", "iloveyou")));
    }

    /**
     * Method under test: {@link AuthController#generateToken(AuthRequest)}
     */
    @Test
    void testGenerateToken2() throws Exception {

        assertThrows(Exception.class, () -> (new AuthController()).generateToken(null));
    }

    /**
     * Method under test: {@link AuthController#generateToken(AuthRequest)}
     */
    @Test
    void testGenerateToken3() throws Exception {


        AuthController authController = new AuthController();
        AuthRequest authRequest = mock(AuthRequest.class);
        when(authRequest.getPassword()).thenReturn("iloveyou");
        when(authRequest.getUserName()).thenReturn("janedoe");
        assertThrows(Exception.class, () -> authController.generateToken(authRequest));
        verify(authRequest).getPassword();
        verify(authRequest).getUserName();
    }
}

