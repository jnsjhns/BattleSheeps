package at.ac.fhcampuswien;

import org.junit.jupiter.api.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(2)
public class AppTest {

    @BeforeAll
    public static void init() {
        System.out.println("Testing Übung 8");
    }

    @AfterAll
    public static void finish() {
        System.out.println("Finished Testing Übung 8");
    }

    @Test
    public void testClassCadastre_Constructor() {
        try {
            // check correct constructor
            assertEquals(1,
                    Arrays.stream(Cadastre.class.getConstructors()).filter(constructor
                            -> constructor.toString().equals("public at.ac.fhcampuswien.Cadastre(int,java.lang.String,int,int,java.time.LocalDate)")).count(),
                    "Constructor (int,java.lang.String,int,int,java.time.LocalDate) missing.");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e);
            fail();
        }
    }

}
