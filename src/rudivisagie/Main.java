package rudivisagie;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        //I would have created 2 separate methods to return the answers of the two parts in this puzzle
        //but as I discovered that Part 2 could be calculated simultaneously with Part 1 in-stead of
        // having to rerun the code separately for Part 2. This results in much quicker runtime but be assured
        //in a larger project everything would be made modular as to promote code re-usability.
        calculatePart1and2();

    }

    /**
     * This method maps out each claim and then calculates the ammount of spaces/inches that overlap and prints
     * result.
     * This method works out which claim is not in conflic/overlap with any other and prints result.
     */
    private static void calculatePart1and2() {

        //Variables
        int id = 0;
        int top = 0;
        int left = 0;
        int width = 0;
        int height = 0;
        int overlapCount = 0; //Holds the value of material that overlap.
        int[][] fabric = new int[1000][1000]; // 2D array to map out fabric.
        int nullConflictCount = 0;// Counter that helps to determine wether a claim results in a conflict/overlap.

        //Array that holds the IDs of a claim that does not overlap.
        ArrayList<Integer> nullConflictArray = new ArrayList<>();

        //Get the input.
        ArrayList<String> claims = loadFile();

        //Iterate each item of data and map out the claims on the fabric array.
        for (String item : claims) {

            try {
                String[] fabricSplit = item.split("[#@,:x]+");

                id = Integer.parseInt(fabricSplit[1].trim());
                left = Integer.parseInt(fabricSplit[2].trim());
                top = Integer.parseInt(fabricSplit[3].trim());
                width = Integer.parseInt(fabricSplit[4].trim());
                height = Integer.parseInt(fabricSplit[5].trim());
            } catch (Exception e) {
                System.out.println("An error has occurred while handling data from file" + e);
            }

            for (int i = 0; i < width; i++) {

                for (int j = 0; j < height; j++) {

                    if (fabric[top + j][left + i] == 0) {
                        fabric[top + j][left + i] = id;
                        nullConflictCount++; //Increments if the block does not overlap with some other claim.
                    } else {

                        //If a conflict is found and it already exists within the array as a previous
                        // non conflict/overlap id then remove it from the aray.
                        if (nullConflictArray.contains(fabric[top + j][left + i])) {
                            int value = nullConflictArray.indexOf(fabric[top + j][left + i]);
                            nullConflictArray.remove(value);
                        }

                        fabric[top + j][left + i] = -1;
                    }

                }

            }

            //If the null conflict count is = to the space the claim takes then no conflict was found and the ID is
            //added to the null conflict array.
            if (nullConflictCount == (width * height)) {
                nullConflictArray.add(id);
                nullConflictCount = 0;
            } else {
                nullConflictCount = 0;
            }

        }

        //Get the total inches that overlap.
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (fabric[i][j] == -1) {
                    overlapCount++;
                }
            }
        }

        //Output
        System.out.println("Day 3 Part 1 answer: " + overlapCount);
        System.out.println("Day 3 Part 2 answer: " + nullConflictArray.get(0));
    }

    /**
     * This method loads the file into an ArrayList.
     *
     * @return Returns ArrayList object with data from file loaded.
     */
    private static ArrayList loadFile() {

        File file = new File("Day3Data");
        ArrayList<String> claims = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);

            while (input.hasNext()) {
                claims.add(input.nextLine());
            }

            input.close();

        } catch (FileNotFoundException e) {
            System.err.format("Sorry, the file you are trying to access does not exist.\n");
        } catch (Exception e) {
            System.err.format("An error has occurred, please contact your friendly developer for help.");
        }
        return claims;
    }
}
