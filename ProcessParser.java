
import java.util.*;
import java.io.*;
public class ProcessParser {

    protected ArrayList<Process> parserBuffer = new ArrayList<Process>();

    public ProcessParser(){

    }
    //Default constructor for ProcessParser.
    public boolean LoadProcessFile(){

        //Asking the user to input the location of the process file they wish to load.
        // Place the test file in your src folder and try "src/parsertest.txt" to access it.
        Scanner fileSelect = new Scanner(System.in);
        System.out.println("Please enter the location of your process list text file.");
        String fileLocation = fileSelect.nextLine();

        //The file parser tries to find the file. If it finds it, it parses the contents of the file into an ArrayList of Proccesses and returns true.
        //If the file cannot be found, it returns false.
        try (Scanner s = new Scanner(new File(fileLocation))){

            //While the file has stuff in it still, make sure to create a Process object with the new stuff and place it in the ArrayList.
            while(s.hasNext()){

                // The current line is loaded in as a String for processing.
                String token = s.nextLine();

                // The token String is then split up into an Array, leaving each data field for the Process as an element in the array.
                String[] arrSplit = token.split(", ");

                // The fields are given to temporary variables.
                int aTime = Integer.parseInt(arrSplit[0]);
                String pId = arrSplit[1];
                int sTime = Integer.parseInt(arrSplit[2]);
                int priority =  Integer.parseInt(arrSplit[3]);

                //The data fields from the line in the file are then put into a new Process object, which is then added to the array.
                parserBuffer.add(new Process (aTime, pId, sTime, priority));

            }
            return true;
        }

        // If no file is found, then obviously the file cannot be loaded. So we return false.
        catch (FileNotFoundException e) {
            return false;
        }


    }

    // Here is a test main to verify functionality of ProcessParsr.
    // Currently, it checks to make sure that LoadProcessFile is functioning properly by printing the contents of the Process Arraylist it fills out.
    public static void main(String[] args){

        // Creating a ProcessParser and loading a file.
        ProcessParser test = new ProcessParser();
        boolean success = test.LoadProcessFile();

        // Print the result of LoadProcessFile. True if file found and loaded, false if otherwise.
        System.out.println(success);

        // Printing out the data of the Processes in the ArrayList.
        // If true, SHOULD print the same fields that are present in the text file.
        // For the provided parsertest.txt, it should be:
        /* Arrival Time: 0, Process Id: process a, Service Time: 10, Process Priority: 1

           Arrival Time: 5, Process Id: process b, Service Time: 14, Process Priority: 1

           Arrival Time: 6, Process Id: process c, Service Time: 4, Process Priority: 1 */

        for(Process prop : test.parserBuffer){
            System.out.println(prop.toString());
        }


    }

}


