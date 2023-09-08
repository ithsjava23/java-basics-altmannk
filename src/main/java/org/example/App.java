package org.example;

import java.util.Locale;
import java.util.Scanner;

public class App {


    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner input = new Scanner(System.in);

        int[] electricityPrice = new int[24]; //An array for the users input values of 'elpriser'
        String[] timeInterval = {"00-01", "01-02", "02-03", "03-04", "04-05", "05-06", "06-07", "07-08", "08-09",
                "09-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20",
                "20-21", "21-22", "22-23", "23-24"}; //An array printing the time-stamps

        while (true) {
            //calling the menu design
            printsMenu();

            String userMenuChoice = input.nextLine();

            // depending on users input of menu choice.
            selectMenu(userMenuChoice, input, electricityPrice, timeInterval);

            if (userMenuChoice.equals("e") || userMenuChoice.equals("E"))
                break;

        }

    }


    // Menu design
    public static void printsMenu() {
        System.out.print("Elpriser\n");
        System.out.print("========\n");
        System.out.print("1. Inmatning\n");
        System.out.print("2. Min, Max och Medel\n");
        System.out.print("3. Sortera\n");
        System.out.print("4. Bästa Laddningstid (4h)\n");
        System.out.print("e. Avsluta\n");
    }

    // Switch method connected to other methods depending on the users input for menu choice
    public static void selectMenu(String menuChoice, Scanner input, int[] electricityPrice, String[] timeInterval) {
        switch (menuChoice) {
            case "1" -> inputElectricityPrice(input, electricityPrice, timeInterval);
            case "2" -> printsMinMaxAvg(electricityPrice, timeInterval);
            case "3" -> printsPricesSorted(electricityPrice, timeInterval);
            case "4" -> cheapest4Hours(electricityPrice, timeInterval);
        }
    }
    // For loop method for input values of electricity prices linked to time
    public static void inputElectricityPrice(Scanner input, int[] electricityPrice, String[] timeInterval) {
        // user input
        for (int i = 0; i < electricityPrice.length ; i++) {
            System.out.print(timeInterval[i] + ": ");
            electricityPrice[i] = Integer.parseInt(input.nextLine());
        }
    }

    public static void bubbleSortAlgorithm(int[] electricityPrice, String[] timeInterval) {
        // this sorts the electricity price lowest to highest with the time interval connected to it.
        int temp;
        String temp2;
        for (int i = 0; i < electricityPrice.length - 1; i++)
            for (int j = 0; j < electricityPrice.length - i - 1; j++)
                if (electricityPrice[j] < electricityPrice[j + 1]) {
                    temp = electricityPrice[j];
                    electricityPrice[j] = electricityPrice[j + 1];
                    electricityPrice[j + 1] = temp;

                    temp2 = timeInterval[j];
                    timeInterval[j] = timeInterval[j + 1];
                    timeInterval[j + 1] = temp2;
                }
    }

    public static float calcAvgOfElectricityPrice(int[] electricityPrice) {
        int sum = 0;
        for (int i = 0; i < electricityPrice.length; i++) {
            sum += electricityPrice[i];
        }
        return (float) sum / electricityPrice.length;
    }

    public static void printsMinMaxAvg(int[] electricityPrice, String[] timeInterval) {
        int max = electricityPrice[0];
        int min = electricityPrice[0];
        String time = timeInterval[0];
        String time2 = timeInterval[0];
        for (int i = 0; i < electricityPrice.length; i++) {
            if (electricityPrice[i] > max) {
                max = electricityPrice[i];
                time = timeInterval[i];
            }
            if (electricityPrice[i] < min) {
                min = electricityPrice[i];
                time2 = timeInterval[i];
            }
        }

        float avg = calcAvgOfElectricityPrice(electricityPrice);

        System.out.print("Lägsta pris: " + time2 + ", " + min + " öre/kWh\n");
        System.out.print("Högsta pris: " + time + ", " + max + " öre/kWh\n");
        System.out.printf("Medelpris: %.2f öre/kWh\n", avg);
    }
    // case 3
    public static void printsPricesSorted(int[] electricityPrice, String[] timeInterval) {
        bubbleSortAlgorithm(electricityPrice,timeInterval);
        for (int i = 0; i < timeInterval.length; i++) {
            System.out.print(timeInterval[i] + " " + electricityPrice[i] + " öre\n");
        }
    }

    public static void cheapest4Hours(int[] electricityPrice, String[] timeInterval) {
        float minSum = Integer.MAX_VALUE;
        int count = 0;
        int tempSum;
        for (int i = 0; i <= electricityPrice.length - 4; i++) {
            tempSum = electricityPrice[i] + electricityPrice[i + 1] +
                    electricityPrice[i + 2] + electricityPrice[i + 3];

            if (tempSum < minSum) {
                minSum = tempSum;
                count = i;
            }
        }

        float avgSum = minSum / 4;

        System.out.print("Påbörja laddning klockan " + timeInterval[count].substring(0, 2) + "\n");
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", avgSum);
    }

}

