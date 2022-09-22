package com.Nest.Icu.modelOT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GreetingController.class})
@ExtendWith(SpringExtension.class)
class GreetingControllerTest {
    @Autowired
    private GreetingController greetingController;

    /**
     * Method under test: {@link GreetingController#greeting(HelloMessage)}
     */
    @Test
    void testGreeting() throws Exception {
        assertEquals("Hello, Name!", greetingController.greeting(new HelloMessage("Name")).getContent());
    }



    /**
     * Method under test: {@link GreetingController#greeting(HelloMessage)}
     */
    @Test
    void testGreeting4() throws Exception {
        HelloMessage helloMessage = mock(HelloMessage.class);
        when(helloMessage.getName()).thenReturn("Name");
        assertEquals("Hello, Name!", greetingController.greeting(helloMessage).getContent());
        verify(helloMessage).getName();
    }
}

