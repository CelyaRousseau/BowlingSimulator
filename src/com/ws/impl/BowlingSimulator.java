package com.ws.impl;

import com.ws.itf.IBowlingSimulator;

/**
 * Created by Akronys on 01/02/2015.
 */
public class BowlingSimulator implements IBowlingSimulator {


    public int[] getScores() {
        int[] scoring = null;

        System.out.println("Beginner");
        for (int i = 0; i < 100; i++) {
            scoring = toThrow("Beginner");
            System.out.println("Score tour 1 : " + scoring[0]
                    + "Score tour 2 : " + scoring[1]);
        }
        System.out.println("Casual");
        for (int i = 0; i < 100; i++) {
            scoring = toThrow("Casual");
            System.out.println("Score tour 1 : " + scoring[0]
                    + "Score tour 2 : " + scoring[1]);
        }
        System.out.println("Advanced");
        for (int i = 0; i < 100; i++) {
            scoring = toThrow("Advanced");
            System.out.println("Score tour 1 : " + scoring[0]
                    + "Score tour 2 : " + scoring[1]);
        }
        return scoring;
    }

    /**
     * @param type String
     */
    public static int[] toThrow(String type) {
        int[] scoring = new int[2];

        // First Lancer
        scoring[0] = profileThrowing(type, 10);

        if (isStrike(scoring[0])) {
            scoring[1] = 0;
        } else {
            // Second Lancer
            int remainingKeels = 10 - scoring[0];
            scoring[1] = randomInRange(0, remainingKeels);
        }
        return scoring;

    }

    /**
     * @param fallenKeels int
     * @return isStrike Boolean
     */
    public static boolean isStrike(int fallenKeels) {
        if (fallenKeels == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param keelsNumber int
     * @param type        String
     * @return isStrike Boolean
     */
    public static int profileThrowing(String type, int keelsNumber) {
        double alea;
        int remainingKeels;

        switch (type) {
            case "Beginner":
                alea = Math.random() * keelsNumber;
                if (alea <= 1) {
                    remainingKeels = randomInRange(9, 10);
                } else if (alea <= 3) {
                    remainingKeels = randomInRange(6, 8);

                } else {
                    remainingKeels = randomInRange(0, 5);
                }
                break;
            case "Casual":
                alea = Math.random() * keelsNumber;
                if (alea <= 2) {
                    remainingKeels = randomInRange(9, 10);
                } else if (alea <= 3) {
                    remainingKeels = randomInRange(0, 3);

                } else {
                    remainingKeels = randomInRange(4, 8);
                }
                break;
            case "Advanced":
                alea = Math.random() * keelsNumber;
                if (alea <= 2) {
                    remainingKeels = randomInRange(0, 6);
                } else {
                    remainingKeels = randomInRange(7, 10);
                }
                break;
            default:
                System.err.println("The profil given is unrecognized");
                return 0;
        }
        return remainingKeels;
    }

    /**
     * @param min int
     * @param max int
     * @return remainingKeels int
     */
    public static int randomInRange(int min, int max) {
        int remainingKeels = min + (int) (Math.random() * ((max - min) + 1));
        return remainingKeels;
    }

}
