package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    PrintStream originalOut;
    ByteArrayOutputStream bos;

    @BeforeEach
    void setup(){
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
    void exitWithLowerCaseE() {
        String menu = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """;
        String menuvg = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Visualisering
                e. Avsluta
                """;
        provideInput("e\n");
        App.main(null);
        assertThat(bos.toString()).containsAnyOf(menu,menuvg);
    }
    @Test
    void exitWithUpperCaseE() {
        String menu = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """;
        String menuvg = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Visualisering
                e. Avsluta
                """;
        provideInput("E\n");
        App.main(null);
        assertThat(bos.toString()).containsAnyOf(menu,menuvg);
    }

    @Test
    void addingPricesThenCalculatingMinMaxAverage() {
        String input = "1\n100\n10\n1\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n2\ne\n";
        provideInput(input);
        App.main(null);
        String response = """
                Lägsta pris: 02-03, 1 öre/kWh
                Högsta pris: 00-01, 100 öre/kWh
                Medelpris: 13,38 öre/kWh
                """;
        assertThat(bos.toString()).contains(response);
    }

    @Test
    void multipleRuns() {
        String input = "1\n100\n10\n1\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n2\n1\n102\n10\n-2\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n2\ne\n";
        provideInput(input);
        App.main(null);
        String response = """
                Lägsta pris: 02-03, 1 öre/kWh
                Högsta pris: 00-01, 100 öre/kWh
                Medelpris: 13,38 öre/kWh
                """;
        String response2 = """
                Lägsta pris: 02-03, -2 öre/kWh
                Högsta pris: 00-01, 102 öre/kWh
                Medelpris: 13,33 öre/kWh
                """;
        assertThat(bos.toString()).contains(response, response2);
    }



    @Test
    void addingPricesWithNegativeValuesThenCalculatingMinMaxAverage() {
        String input = "1\n-10\n10\n-10\n10\n-10\n10\n-10\n10\n-10\n10\n-10\n12\n-10\n10\n-10\n10\n-10\n10\n-10\n10\n-10\n10\n-12\n10\n2\ne\n";
        provideInput(input);
        App.main(null);
        String response = """
                Lägsta pris: 22-23, -12 öre/kWh
                Högsta pris: 11-12, 12 öre/kWh
                Medelpris: 0,00 öre/kWh
                """;
        assertThat(bos.toString()).contains(response);
    }

    @Test
    void printPricesSorted() {
        String input = "1\n20\n30\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n40\n3\ne\n";
        provideInput(input);
        App.main(null);
        String response = """
                23-24 40 öre
                01-02 30 öre
                00-01 20 öre
                02-03 10 öre
                """;
        assertThat(bos.toString()).contains(response);
    }

    @Test
    void printPricesSorted2() {
        String input = "1\n40\n10\n20\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n3\ne\n";
        provideInput(input);
        App.main(null);
        String response = """
                00-01 40 öre
                02-03 20 öre
                01-02 10 öre
                """;
        assertThat(bos.toString()).contains(response);
    }

    @Test
    void cheapest4Hours() {
        String input = "1\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n1\n2\n3\n4\n10\n4\ne\n";
        provideInput(input);
        App.main(null);
        String response = """
                Påbörja laddning klockan 19
                Medelpris 4h: 2,5 öre/kWh
                """;
        assertThat(bos.toString()).contains(response);
    }
}
