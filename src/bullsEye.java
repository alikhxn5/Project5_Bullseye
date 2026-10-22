/*
Name: Alikhan Makhamadaliev
Date: April 23, 2025
Class: CSC1120 Section 1
Pledge: I have neither given nor received unauthorized aid on this program.
Description: This program is a simulation of a game of darts between two players. It reads coordinates of dart throws
from a file then computes the score based of where the darts land on a dartboard, respecting the limit on amount of dart
throws per player, and then determines whether there is a winner or the round ended in a tie.
Input: Consists of a text file containing the coordinates of where the darts land for player 1 and 2 with each player
getting 3 dart throws per round with player 1 going first and player 2 going second.
Output: Each round (dataset) results in the program printing a line that lets the user know the result of said round.
Sidenote: System.err.println is a cool feature
Disclosure of AI utilization: I had a hard time reworking my code to run with any given amount of datasets (rounds)
because I had initially set the program up to only accept 2 rounds for some reason and I quickly came to realize how
blind I was being through the help of AI.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class bullsEye {

    public static void main(String[] args) {
        try {
            File file = new File("coordinates1.txt");
            Scanner scanner = new Scanner(file);

            int roundNum = 0;

            while (scanner.hasNextLine()) {

                //reads 12 doubles for one dataset
                double[] coordinates = new double[12];
                boolean validRound = readRound(scanner, coordinates);
                processRound(coordinates,validRound, roundNum);

                roundNum++;

            }

            if(roundNum == 1){
                System.err.println("No valid rounds were found in the input file");
            }

        scanner.close();
        }catch (FileNotFoundException e){
            System.err.println("Error: coordinates.txt file not found");
        }
    }


    /*
    This function reads 12 double values for a single round from the scanner
    Parameters: scanner, which is the scanner object. round which is the array that stores the coordinates
    Returns: true if the round was legible and read correctly, false in every other scenario
     */
    private static boolean readRound(Scanner scanner, double[] round){
        for (int i = 0; i < 12; i++) {
            if (scanner.hasNextDouble()) {
                round[i] = scanner.nextDouble();
            } else {
                return false;
            }
        }
        return true;
    }


/*
This function processes a round by computing the scores and printing the results
Parameters: coordinates- the array of 12 doubles for the round. validRound- if the round is valid, roundNum- the current
round number.
Returns: nothing
 */
    private static void processRound(double[] coordinates, boolean validRound, int roundNum){
        if (validRound) {
            int p1Score = 0;
            int p2Score = 0;

            // player 1's three darts
            for (int i = 0; i < 6; i += 2) {
                double x = coordinates[i];
                double y = coordinates[i + 1];
                p1Score += score(x, y);
            }

            // player 2's three darts
            for (int i = 6; i < 12; i += 2) {
                double x = coordinates[i];
                double y = coordinates[i + 1];
                p2Score += score(x, y);
            }

            //determines tie/winner & prints result
            if (p1Score > p2Score) {
                System.out.println("SCORE: " + p1Score + " to " + p2Score + ", PLAYER 1 WINS.");
            } else if (p2Score > p1Score) {
                System.out.println("SCORE: " + p1Score + " to " + p2Score + ", PLAYER 2 WINS.");
            } else {
                System.out.println("SCORE: " + p1Score + " to " + p2Score + ", TIE.");
            }
        }else{
            System.err.println("Round " + roundNum + " has an invalid input");
        }
    }

    /*
    This function computes the score for a dart thrown at the coordinates(x,y) and awards a higher score if the dart
    lands on a boundary.
    Parameters: x, which represents the X-coordinate of the dart. y, which represents the y-coordinate of the dart.
    Returns: an integer representing the score based off of where the dart lands on the board
    */
    public static int score(double x, double y){

        //calculates distance from the origin
        double radius = Math.sqrt(x * x + y* y);

        //Check which ring the dart lands in
        if (radius <= 3.0){ //Bullseye
            return 100;
        }else if (radius <= 6.0){ //between 3 and 6
            return 80;
        }else if (radius <= 9.0){ //between 6 and 9
            return 60;
        }else if (radius <= 12.0){ //between 9 and 12
            return 40;
        }else if (radius <= 15.0){ //between 12 and 15
            return 20;
        }else{ // in the woods
            return 0;
        }


    }
}
