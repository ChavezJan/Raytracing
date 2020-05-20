/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.tools;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class Authenticator {
    /**
     * here check that it does not put any character
     * @param option
     * @return Valid -> True -> let you continue / False -> don't let you continue // it depend in the input of the user
     */
    public static int authLet(int option) {

        int menu = 0;
        boolean Valid = false;

        do {

            System.out.println("Make your choice..");
            Scanner scan = new Scanner(System.in);

            try {

                menu = scan.nextInt();
                Valid = true;


            } catch (InputMismatchException ime) {

                System.out.println("Please, input a number");

                Valid = false;
            }

        } while (!Valid);

        return menu;

    }

    /**
     * here check that the input is one of the requested options
     * @param menu
     * @param option
     * @return Valid -> True-> let you continue / False-> dont let you continue // it depend in the input of the user
     */
    public static boolean authNum(int menu, int option) {

        boolean Valid = false;

        if (menu > option || menu < 1) {

            System.out.println("Please, select one of the " + option + " options");
            Valid = false;

        } else {

            Valid = true;

        }

        return !Valid;

    }
}
