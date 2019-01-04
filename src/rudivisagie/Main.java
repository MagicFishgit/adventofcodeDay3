package rudivisagie;

import java.util.*;
import  java.io.*;

public class Main {

    public static void main(String[] args) {

        getOverlap();

    }

    private static void getOverlap(){

        //Variables
        int id = 0;
        int top = 0;
        int left = 0;
        int width = 0;
        int height = 0;
        int overlapCount = 0;
        int[] fabricWidth = new int[1000];
        int[] fabricHeight = new int[1000];
        int[][] fabric = new int[1000][1000];
        int nullConflictCount = 0;
        int noConflictFoundID = 0;
        ArrayList<Integer> nullConflictArray = new ArrayList<>();

        //Get the input.
        ArrayList<String> claims = loadFile();

        //Iterate each item of data and map out the claims on the fabric array.
        for (String item: claims) {

            try{
                String[] fabricSplit = item.split("[#@,:x]+");

                id = Integer.parseInt(fabricSplit[1].trim());
                left = Integer.parseInt(fabricSplit[2].trim());
                top = Integer.parseInt(fabricSplit[3].trim());
                width = Integer.parseInt(fabricSplit[4].trim());
                height = Integer.parseInt(fabricSplit[5].trim());
            }
            catch (Exception e){
                System.out.println(e);
            }

            for(int i = 0; i < width; i++){

                for(int j = 0; j < height; j++){

                    if (fabric[top + j][left + i] == 0){
                        fabric[top + j][left + i] = id;
                        nullConflictCount++;
                    }else{

                        if (nullConflictArray.contains(fabric[top + j][left + i])){
                           int value = nullConflictArray.indexOf(fabric[top + j][left + i]);
                           nullConflictArray.remove(value);
                        }

                        fabric[top + j][left + i] = -1;
                    }

                }

            }

            if (nullConflictCount == (width*height)){
                nullConflictArray.add(id);
                nullConflictCount = 0;
            }else {
                nullConflictCount = 0;
            }

       }

        //Get the inches that overlap.
        for (int i = 0; i < 1000; i++){
            for(int j = 0; j <1000; j++){
                if (fabric[i][j] == -1){
                    overlapCount++;
                }
            }
        }

        System.out.println(overlapCount);
        System.out.println(nullConflictArray.get(0));
    }

    public static ArrayList loadFile(){

        File file = new File("Day3Data");
        ArrayList<String> claims = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);

            while(input.hasNext()){
                claims.add(input.nextLine());
            }

            input.close();

        }
        catch(FileNotFoundException e){
            System.err.format("Sorry, the file you are trying to access does not exist.\n");
        }
        catch(Exception e){
            System.err.format("An error has occurred, please contact your friendly developer for help.");
        }
        return claims;
    }
}
