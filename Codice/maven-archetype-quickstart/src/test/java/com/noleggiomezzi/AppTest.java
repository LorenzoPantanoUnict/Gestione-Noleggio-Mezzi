package com.noleggiomezzi;
import org.junit.jupiter.api.Test;

// 2. Le asserzioni (assertEquals, assertTrue, ecc.)
// NOTA: "static" serve per usare direttamente assertEquals() senza scrivere Assertions.assertEquals()
import static org.junit.jupiter.api.Assertions.*;

// 3. Il ciclo di vita (se ti serve @BeforeEach per il setup)
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
