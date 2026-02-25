package com.example.jdbc_test.utils;

import java.util.Scanner;
import java.sql.Date;


public class InputUtils {

    static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static String inputString() {
        while (true) {
            try {
                String string = scanner.nextLine();
                return string;
            } catch (Exception e) {
                System.out.println("Invalid format, try again");
            }
        }
    }

    public static int inputInt() {
        while (true) {
            try {
                int number = scanner.nextInt();
                scanner.nextLine();
                return number;
            } catch (Exception e) {
                System.out.println("Invalid format, try again");
            }
        }
    }

    public static double inputDouble() {
        while (true) {
            try {
                double number = scanner.nextDouble();
                scanner.nextLine();
                return number;
            } catch (Exception e) {
                System.out.println("Invalid format, try again");
            }
        }
    }
    
     public static Date inputDate(){
        while (true) {
            try{
                String dateString = scanner.nextLine();
                Date date = Date.valueOf(dateString);
                return date;
            }
            catch (IllegalArgumentException e){
                System.out.println("Date format should be \"yyyy-[m]m-[d]d\", retry input");
            }
        }
    }

    public static boolean inputYN() {
        System.out.println("[y\\n]");
        while (true) {
            String answ = scanner.nextLine();
            answ = answ.toLowerCase();
            if (answ.equals("y"))
                return true;
            else if (answ.equals("n"))
                return false;
            else
                System.out.println("Wrong answer format");
        }
    }
}
