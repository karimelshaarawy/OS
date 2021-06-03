import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class OSapp {
    Memory memory;

    public OSapp() {
        memory = new Memory();
    }

    public void interpreter(String filePath) throws OSException {
        //reading file line by line *******
        Scanner myFile = readFile(filePath);
        while (myFile.hasNextLine()) {
            String data = myFile.nextLine();
            String[] tokens = data.split(" ");
            boolean isInput=false;
            String input="";
            if(tokens[2].equals("input")){
                isInput=true;
                Scanner sc=new Scanner(System.in);
                input = sc.nextLine();

            }

            // Handling different command cases
            switch (tokens[0]) {
                case "print":
                    if (memory.integers.containsKey(tokens[1]))
                        System.out.println("" + (memory.integers.get(tokens[1])));
                    else if (memory.strings.containsKey(tokens[1]))
                        System.out.println(memory.strings.get(tokens[1]));
                    else
                        throw new OSException("there is no such a value");


                    ;
                    break;


                case "assign":if(isInput){
                    if(isNumeric(input))
                        memory.integers.put(tokens[1],Integer.parseInt(input));
                    else
                        memory.strings.put(tokens[1],input);
                }
                else if (tokens[2].equals("readFile")){
                    String fileText ="";

                    Scanner temp = readFile(memory.strings.get(tokens[3]));
                    while ((temp.hasNextLine())){
                      String  datatemp =temp.nextLine();
                        fileText=fileText +"/n"+datatemp;
                    }
                    memory.strings.put(tokens[1],fileText);



                }else{
                    if(memory.strings.containsKey(tokens[2]))
                        memory.strings.put(tokens[1],memory.strings.get(tokens[2]));
                    else
                        memory.integers.put(tokens[1],memory.integers.get(tokens[2]));
                }

                    break;


                case "add":memory.integers.put(tokens[1],add(memory.integers,tokens[1],tokens[2]));


                    break;

                case "writeFile":if(memory.strings.containsKey(tokens[2]))
                                 writeFile(tokens[1],memory.strings.get(tokens[2]));
                else
                    writeFile(tokens[1],memory.integers.get(tokens[2]).toString());

                    break;



                default:
                    System.out.println("WRONG COMMAND");


            }


        }
        myFile.close();
    }


    public static Scanner readFile(String filePath) {

        File myObj = new File("filename.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // The Scanner should be closed after reading the file

        return myReader;
    }

    public static void writeFile(String filePath, String data) {
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

    public static int add(Hashtable<String, Integer> integers, String x, String y) {
        int x_value = integers.get(x);
        int y_value = integers.get(y);
        return x_value + y_value;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int n =Integer.parseInt(strNum) ;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

    }
}
