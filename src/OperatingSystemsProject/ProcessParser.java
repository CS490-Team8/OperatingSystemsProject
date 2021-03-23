package OperatingSystemsProject;

import java.util.*;
import java.io.*;

/**
 * ProcessParser is responsible for reading input file and generating an ArrayList of processes
 */
public class ProcessParser {
    //class members
    protected ArrayList<Process> parserBuffer = new ArrayList<Process>(); //ArrayList to store processes
    private String filePath; //file path for input file

    /**
     * Default constructor for ProcessParser.
     * Needs manual file path entry from the user
     */
    public ProcessParser(){
        //Asking the user to input the location of the process file they wish to load.
        Scanner fileSelect = new Scanner(System.in);
        System.out.println("Please enter the location of your process list text file.");
        this.filePath = fileSelect.nextLine();
        LoadProcessFile();
    }

    /**
     * Overload constructor for ProcessParser.
     * Allows file path to be passed in.
     * @param fileInputPath String containing file path of input file
     */
    public ProcessParser(String fileInputPath){
        this.filePath = fileInputPath;
        LoadProcessFile();
    }

    /**
     * This function reads the input file and parses the processes
     */
    private void LoadProcessFile(){
        //The file parser tries to find the file. If it finds it, it parses the contents of the file into an ArrayList of Proccesses
        try (Scanner s = new Scanner(new File(this.filePath))){
            //While the file has stuff in it still, make sure to create a Process object with the new stuff and place it in the ArrayList.
            while(s.hasNext()){
                String token = s.nextLine(); // The current line is loaded in as a String for processing.
                String[] arrSplit = token.split(", "); // The token String is then split up into an Array, leaving each data field for the Process as an element in the array.

                // The fields are given to temporary variables.
                int aTime = Integer.parseInt(arrSplit[0]);
                String pId = arrSplit[1];
                int sTime = Integer.parseInt(arrSplit[2]);
                int priority =  Integer.parseInt(arrSplit[3]);

                //The data fields from the line in the file are then put into a new Process object, which is then added to the array.
                parserBuffer.add(new Process (aTime, pId, sTime, priority));
            }
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Getter for the ArrayList of processes
     * @return ArrayList of processes
     */
    public ArrayList<Process> getProcesses() {
        return parserBuffer;
    }

    /**
     * Test method to verify functionality of ProcessParser
     * Currently, it checks to make sure that LoadProcessFile is functioning properly by printing the contents of the Process Arraylist it fills out.
     * @param args
     */
    public static void main(String[] args){
        // Creating a ProcessParser and loading a file.
        //ProcessParser test = new ProcessParser(); //manual entry of file path from user
        ProcessParser test = new ProcessParser("src/OperatingSystemsProject/input.txt"); //file path passed into constructor

        // Printing out the data of the Processes in the ArrayList.
        for(Process prop : test.parserBuffer){
            System.out.println(prop.toString());
        }
    }
}

