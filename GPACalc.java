import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 *  GPACalc.java - a simple console based program.
 *  Will prompt the user for input and will calculate the user's Unweighted GPA based on the provided input.
 *  (I know it isn't very impressive, but I had a bunch of tests these past couple weeks and wasn't able to devote much time to this)
 *
 *  @author Eric Fernandes
 *  @since  March 8, 2024
 */

public class GPACalc {

    double[][] gradeSumPerSemester;     // contains the total points per semester
    double[][] numClassesPerSemester;   // contains the number of classes per semester
    double[][] gpaPerSemester;          // contains the GPA per semester

    double gpa9th, gpa10th, gpa11th, gpa12th, gpaTotal; // Cumulative GPAs
    String name; int grade;             // User information

    /**     Constructor - initializes fields    */
    public GPACalc() {
        gradeSumPerSemester = new double[4][2];
        numClassesPerSemester = new double[4][2];
        gpaPerSemester = new double[4][2];

        gpa9th = gpa10th = gpa11th = gpa12th = gpaTotal = 0;

        name = "Mr. Matador";
        grade = 12;
    }

    public static void main(String [] args) {
        GPACalc gc = new GPACalc();
        gc.getInput();
        gc.calcGPA();
        gc.printOutput();
    }

    /**
     *  Prints the output to terminal
     */
    public void printOutput() {

        System.out.println("\n--------------------------------------------------------------------------------------------------------");

        System.out.println("\nStudent Report for: " + name);
        System.out.println("Grade: " + grade + "\n");

        for (int year = 9; year <= grade; year++) {
            for (int sem = 1; sem <= 2; sem++) {

                String x = "GPA for " + year + "th Grade (Semester " + sem + "):";
                System.out.printf("%-35s%.4f\n", x, gpaPerSemester[year - 9][sem - 1]);
            }

            String x = "Cumulative GPA for " + year + "th Grade:";

            if (year == 9)
                System.out.printf("\n%-35s%.4f\n\n", x, gpa9th);
            else if (year == 10)
                System.out.printf("\n%-35s%.4f\n", x, gpa10th);
            else if (year == 11)
                System.out.printf("\n%-35s%.4f\n", x, gpa11th);
            else if (year == 12)
                System.out.printf("\n%-35s%.4f\n", x, gpa12th);
        }

        if (grade != 9) {
            String x = "---------------------- Cumulative GPA for grades 9-" + grade + ": ----------------------";
            System.out.printf("\n\n%-35s\n%.4f", x, gpaTotal);
        }
    }

    /**
     *  Gets input from the user
     */
    public void getInput() {

        System.out.println("\n--------------------------------------------------------------------------------------------------------");
        System.out.println("\nHello! You are running GPACalc.java, a program which will calculate your Unweighted GPA based your input.");

        name = Prompt.getString("\nPlease input your name");
        grade = Prompt.getInt("Please input your grade", 9, 12);

        for (int year = 9; year <= grade; year++) {
            for (int sem = 1; sem <= 2; sem++) {

                String collection = "";
                char temp;

                System.out.println("\n---------------------- " + year + "th Grade (Semester " + sem + "): ----------------------");

                for (int num = 0; num < 7; num++) {

                    numClassesPerSemester[year - 9][sem - 1]++;

                    String course = Prompt.getString("Please input the name of a course you took in " + year + "th grade, Semester " + sem);
                    temp = Prompt.getChar("Please input your grade for " + course + " (" + year + "th Grade, Semester " + sem + ")");
                    collection += "" + temp;

                    if (num < 6) {
                        char userChoice = Prompt.getChar("\nDo you have another course you would like to input for " + year + "th Grade, Semester " + sem + "? (Y for Yes, N for No)");
                        if (userChoice == 'N' || userChoice == 'n') {
                            num = 7;
                        }
                    }
                }

                gradeSumPerSemester[year - 9][sem - 1] = letterToNumGrades(collection);
            }
        }
    }

    /**
     *  Converts letter grades into points
     *  Ignores + and -
     *  A = 4
     *  B = 3
     *  C = 2
     *  D = 1
     *  @param grades   String containing letter grades
     *  @return         Total points
     */
    public int letterToNumGrades(String grades) {

        int result = 0;

        for (int pos = 0; pos < grades.length(); pos++) {

            if (grades.charAt(pos) == 'A' || grades.charAt(pos) == 'a') {
                result += 4;
            } else if (grades.charAt(pos) == 'B' || grades.charAt(pos) == 'b') {
                result += 3;
            } else if (grades.charAt(pos) == 'C' || grades.charAt(pos) == 'c') {
                result += 2;
            } else if (grades.charAt(pos) == 'D' || grades.charAt(pos) == 'd') {
                result += 1;
            }
        }

        return result;
    }

    /**
     *  Calculates each type of GPA
     *  Stores GPA in fields
     */
    public void calcGPA() {

        for (int row = 0; row < gpaPerSemester.length; row++) {
            for (int col = 0; col < gpaPerSemester[0].length; col++) {
                gpaPerSemester[row][col] = gradeSumPerSemester[row][col] / numClassesPerSemester[row][col];
            }

            if (row == 0) {
                gpa9th = (gpaPerSemester[row][0] + gpaPerSemester[row][1]) / 2;
            } else if (row == 1) {
                gpa10th = (gpaPerSemester[row][0] + gpaPerSemester[row][1]) / 2;
            } else if (row == 2) {
                gpa11th = (gpaPerSemester[row][0] + gpaPerSemester[row][1]) / 2;
            } else if (row == 3) {
                gpa12th = (gpaPerSemester[row][0] + gpaPerSemester[row][1]) / 2;
            }
        }

        gpaTotal = (gpa9th + gpa10th + gpa11th + gpa12th) / (grade - 8);
    }
}

/**
 *	Prompt.java - Uses BufferedReader.
 *	Provides utilities for user input.  This enhances the BufferedReader
 *	class so our programs can recover from "bad" input, and also provides
 *	a way to limit numerical input to a range of values.
 *
 *	The advantages of BufferedReader are speed, synchronization, and piping
 *	data in Linux.
 *
 *	@author	Eric Fernandes
 *	@since	September 7, 2023
 */

class Prompt {
    // BufferedReader variables
    private static InputStreamReader streamReader = new InputStreamReader(System.in);
    private static BufferedReader bufReader = new BufferedReader(streamReader);

    /**
     *	Prompts user for string of characters and returns the string.
     *	@param ask  The prompt line
     *	@return  	The string input
     */
    public static String getString (String ask)
    {
        System.out.print(ask + " -> ");
        String input = "";
        try {
            input = bufReader.readLine();
        }
        catch (IOException e) {
            System.err.println("ERROR: BufferedReader could not read line");
        }
        return input;
    }

    /**
     *	Prompts the user for a character and returns the character.
     *	@param ask  The prompt line
     *	@return  	The character input
     */
    public static char getChar (String ask)
    {
        char input = 'i';
        boolean found = false;
        while (!found) {
            String str = getString(ask);
            if (str.length() == 1 &&
                    (str.equalsIgnoreCase("A") || str.equalsIgnoreCase("B") ||
                            str.equalsIgnoreCase("C") || str.equalsIgnoreCase("D") ||
                            str.equalsIgnoreCase("F") || str.equalsIgnoreCase("Y") || str.equalsIgnoreCase("N"))) {
                found = true;
                input = str.charAt(0);
            }
        }
        return input;
    }

    /**
     *	Prompts the user for an integer and returns the integer.
     *	@param ask  The prompt line
     *	@return  	The integer input
     */
    public static int getInt (String ask)
    {
        int val = 0;
        boolean found = false;
        while (!found) {
            String str = getString(ask);
            try {
                val = Integer.parseInt(str);
                found = true;
            }
            catch (NumberFormatException e) {
                found = false;
            }
        }
        return val;
    }

    /**
     *	Prompts the user for an integer using a range of min to max,
     *	and returns the integer.
     *	@param ask  The prompt line
     *	@param min  The minimum integer accepted
     *	@param max  The maximum integer accepted
     *	@return  	The integer input
     */
    public static int getInt (String ask, int min, int max)
    {
        int val = 0;
        do {
            val = getInt(ask + " (" + min + ", " + max + ")");
        } while ((val < min) || (val > max));
        return val;
    }
}
