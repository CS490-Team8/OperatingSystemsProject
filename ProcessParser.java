
import java.util.*;
import java.io.*;
public class ProcessParser {

    protected ArrayList<Process> parserBuffer = new ArrayList<Process>();

    public ProcessParser(){

    }
    //Default constructor for ProcessParser.
    public void LoadProcessFile(){
        try (Scanner s = new Scanner(new File("src/parsertest.txt"))){
            //s.useDelimiter("\\s*,\\s*");

            while(s.hasNext()){

                String token = s.nextLine();
                System.out.println(token);

                String[] arrSplit = token.split(", ");

                int aTime = Integer.parseInt(arrSplit[0]);
                String pId = arrSplit[1];
                int sTime = Integer.parseInt(arrSplit[2]);
                int priority =  Integer.parseInt(arrSplit[3]);



                parserBuffer.add(new Process (aTime, pId, sTime, priority));
                for(Process prop : parserBuffer){
                    System.out.println(prop.getProcess_Id() + " " + prop.getProcess_Priority());
                }

            }
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args){
        ProcessParser test = new ProcessParser();
        test.LoadProcessFile();
        for(Process prop : test.parserBuffer){
            System.out.println(prop.getProcess_Id() + " " + prop.getProcess_Priority());
        }

    }

}


