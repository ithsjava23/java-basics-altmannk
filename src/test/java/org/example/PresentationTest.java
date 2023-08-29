package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PresentationTest {

    PrintStream originalOut;
    ByteArrayOutputStream bos;

    @BeforeEach
    void setup() {
        originalOut = System.out;
        bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void createDiagram() {
        String input = "1\n530\n530\n530\n432\n450\n500\n334\n400\n430\n350\n380\n401\n335\n431\n335\n236\n235\n234\n150\n200\n139\n138\n137\n40\n5\ne\n";
        //530
        //432
        //334
        //236
        //138
        //40
        provideInput(input);
        App.main(null);
        String response = """
                530|  x  x  x                                                              \s
                   |  x  x  x  x  x  x                                                     \s
                   |  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x                          \s
                   |  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x                       \s
                   |  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x     \s
                 40|  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x
                   |------------------------------------------------------------------------
                   | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23
                """;
        assertThat(bos.toString()).contains(response);
    }

    @Test
    void createDiagram2() {
        String input = "1\n100\n99\n78\n77\n55\n54\n33\n32\n10\n9\n0\n-1\n-12\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n5\ne\n";
        //100
        //77
        //54
        //32
        //10
        //-12
        //Medel 22.25
        provideInput(input);
        App.main(null);
        String response = """
                100|  x                                                                    \s
                   |  x  x  x  x                                                           \s
                   |  x  x  x  x  x  x                                                     \s
                   |  x  x  x  x  x  x  x  x                                               \s
                   |  x  x  x  x  x  x  x  x  x                                            \s
                -12|  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x
                   |------------------------------------------------------------------------
                   | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23
                """;
        assertThat(bos.toString()).contains(response);
    }
}
