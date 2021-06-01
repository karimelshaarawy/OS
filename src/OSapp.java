import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class OSapp {
    Memory memory;

    public OSapp(){
        memory=new Memory();
    }

    public static void interpreter(String filePath){
    //reading file line by line *******
        Scanner myFile =readFile(filePath);
        while (myFile.hasNextLine()) {
            String data = myFile.nextLine();
            String[] tokens =data.split(" ");

            // Handling different command cases
            switch (tokens[0]){
                case "print":       ;break;


                case "assign":      ;break;


                case "add":   ; break;

                case "writeFile":   ;break;

                case "readFile":    ;break;


                default:System.out.println("WRONG COMMAND");







            }









        }
        myFile.close();
    }




  public static Scanner readFile (String filePath){

      File myObj = new File("filename.txt");
      Scanner myReader = null;
      try {
           myReader = new Scanner(myObj);
      }catch (FileNotFoundException e){
          System.out.println(e.getMessage());
      }

      // The Scanner should be closed after reading the file

      return myReader;
  }

  public static void writeFile (String filePath,String data){
      try {
          FileWriter myWriter = new FileWriter(filePath);
          myWriter.write(data);
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
      }
  }

  public static int add(Hashtable<String,Integer> integers,String x, String y){
        int x_value =integers.get(x);
        int y_value =integers.get(y);
        return x_value+y_value;
  }



}
