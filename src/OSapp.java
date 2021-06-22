import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;

public class OSapp {
    Memory memory;

    public OSapp() {
        memory = new Memory();
    }
public void pcbIntializer(String filePath,Vector<int[]>boundaries){
        int pcbID=Integer.parseInt(getProgramName(filePath));
        PCB pcb=new PCB(pcbID,State.NEW,boundaries.get(pcbID-1)[0],boundaries.get(pcbID-1));
        memory.assignPCB(pcb);
}
public void stateChanger(PCB pcb){
        for(int i=0;i<10;i++) {
            if(pcb.equals(memory.MemoryArray[i])) {
                if (pcb.programState.equals(State.NEW)) {
                    pcb.programState = State.READY;
                }
                else {
                    if (pcb.programState.equals(State.READY)) {
                        pcb.programState = State.RUNNING;
                    }
                    else{
                        if(pcb.programState.equals(State.RUNNING)){
                            pcb.programState= State.READY;
                        }
                    }
                }
            }
        }
}
public String getProgramName(String filePath){
        String[] splitter=filePath.split("/");
        String[] splitter2=splitter[2].split(" ");
        return splitter2[1].charAt(0)+"";

}
public void programAssign(String[] filePath){

    for (int i = 0; i < filePath.length; i++) {
        Scanner myFile = readFile(filePath[i]);
        while (myFile.hasNextLine()) {
            String data = myFile.nextLine();
            for (int j = 30; j < 50; j++) {
                if (memory.MemoryArray[j] == null) {
                    memory.MemoryArray[j] = data;
                    break;
                }
            }
        }
        for (int j = 30; j < 50; j++) {
            if (memory.MemoryArray[j] == null) {
                memory.MemoryArray[j] ="endProgram";
                break;
            }

        }
    }
    Vector<int[]> boundaries=new Vector<>();
    boolean start=false;
    int[] minMax=new int[2];
    for (int j = 30; j < 50; j++) {
        if (memory.MemoryArray[j] !=null) {
            if(start==false){
                minMax[0]=j;
                start=true;
            }
        }
        if(memory.MemoryArray[j] =="endProgram"){
            minMax[1]=j-1;
            boundaries.add(new int[]{minMax[0],minMax[1]});
            start=false;
        }
    }
    for (int i = 0; i < filePath.length; i++) {
        pcbIntializer(filePath[i],boundaries);
    }


}
    public int countPrograms(){
        int counter=0;
        for (int i = 30; i < memory.MemoryArray.length; i++) {
            if (memory.MemoryArray[i]=="endProgram"){
                counter++;
            }
        }
        return counter;
    }
    public int switchProgramChecker(int noProgramsChecker,int noPrograms){
        if(noProgramsChecker==noPrograms-1){
            noProgramsChecker=0;
        }
        else{
            noProgramsChecker++;
        }
        return noProgramsChecker;
    }

    public void scheduler(String[] filePath) throws OSException {
        programAssign(filePath);
        for (int i = 0; i < 10; i++) {
            if(memory.MemoryArray[i]!=null){
                stateChanger((PCB)memory.MemoryArray[i]);
            }
        }
        int noPrograms = countPrograms();
        int programLines = 0;
        for (int j = 30; j < 50; j++) {
                if (memory.MemoryArray[j] != "endProgram" && memory.MemoryArray[j] !=null) {
                    programLines++;
                }
            }
        int noProgramsChecker = 0;
        int instructions = 0;
        boolean state=false;
        while(programLines!=0) {
            if(instructions==0) {
            }else{
                if(instructions%2==0){
                    stateChanger(((PCB) (memory.MemoryArray[noProgramsChecker])));
                    noProgramsChecker=switchProgramChecker(noProgramsChecker,noPrograms);
                    state=false;
                }
            }
                if (memory.MemoryArray[((PCB) (memory.MemoryArray[noProgramsChecker])).programCounter] == "endProgram") {
                    ((PCB) (memory.MemoryArray[noProgramsChecker])).programState=State.FINISHED;
                    instructions++;
                } else {
                    if (memory.MemoryArray[((PCB) (memory.MemoryArray[noProgramsChecker])).programCounter] != null) {
                        if(state==false){
                            stateChanger(((PCB) (memory.MemoryArray[noProgramsChecker])));
                            state=true;
                        }
                        interpreter(memory.MemoryArray[((PCB) (memory.MemoryArray[noProgramsChecker])).programCounter].toString(), filePath[noProgramsChecker]);
                        ((PCB) (memory.MemoryArray[noProgramsChecker])).programCounter++;
                        instructions++;
                        programLines--;

                    }
                }
            }
        ((PCB) (memory.MemoryArray[noProgramsChecker])).programState=State.FINISHED;
        }

    public void interpreter(String data,String filePath) throws OSException {
        //reading file line by line *******
        //Scanner myFile = readFile(filePath);
        //while (myFile.hasNextLine()) {
           // String data = myFile.nextLine();
            String[] tokens = data.split(" ");
            //for(int i=0;i<tokens.length;i++)
            //System.out.println(tokens[i]);

            boolean isInput=false;
            String input="";
            if (tokens.length>2) {
                if (tokens[2].equals("input")) {
                    isInput = true;
                    Scanner sc = new Scanner(System.in);
                    input = sc.nextLine();

                }
            }
            // Handling different command cases
            switch (tokens[0]) {
                case "print":
                    boolean variableChecker=false;
                    for(int i=10;i<20;i++){
                        if((tokens[1]+getProgramName(filePath)).equals(memory.MemoryArray[i])){
                            System.out.print(memory.MemoryArray[i+10]);
                            variableChecker=true;
                            break;
                        }
                    }
                    if(variableChecker==false){
                        System.out.println(tokens[1]);
                    }
                    break;


                case "assign":
                    tokens[1]=tokens[1]+getProgramName(filePath);
                    if(tokens[2].equals("readFile")){
                        tokens[3]=tokens[3]+getProgramName(filePath);
                    }
                    else{
                        tokens[2]=tokens[2]+getProgramName(filePath);
                    }
                    if(isInput){
                        for(int i=10;i<memory.MemoryArray.length;i++){
                            if(memory.MemoryArray[i]==null){
                                memory.MemoryArray[i] = tokens[1];
                                memory.MemoryArray[i+10] = input;
                                break;
                            }
                        }
                    }
                    else{
                        String fileText ="";
                        String readData="";
                       for(int i=10;i<20;i++){
                           if(tokens[3].equals(memory.MemoryArray[i])){
                               readData=(String)memory.MemoryArray[i+10];
                               break;
                           }
                       }
                        Scanner temp = readFile(readData);
                        while ((temp.hasNextLine())){
                            String  datatemp =temp.nextLine();
                            fileText=fileText +datatemp+"\n";
                        }
                        for (int i = 10; i < 20; i++) {
                            if(memory.MemoryArray[i]==null){
                                memory.MemoryArray[i]=tokens[1];
                                memory.MemoryArray[i+10]=fileText;
                                break;

                            }
                        }

                    }

                    break;


                case "add":
                    tokens[1]=tokens[1]+getProgramName(filePath);
                    tokens[2]+=getProgramName(filePath);
                    for(int i=10;i<20;i++){
                        if(tokens[1].equals(memory.MemoryArray[i])){
                            memory.MemoryArray[i+10]=add(tokens[1],tokens[2],memory.MemoryArray);
                            break;
                        }
                    }
                    break;

                case "writeFile":
                    tokens[1]=tokens[1]+getProgramName(filePath);
                    tokens[2]+=getProgramName(filePath);
                    String writeData="";
                    String dataPath="";
                    for (int i = 10; i < 20; i++) {
                        if(tokens[2].equals(memory.MemoryArray[i])){
                            writeData=(String) memory.MemoryArray[i+10];
                            break;
                        }
                    }
                    for (int i = 10; i <20 ; i++) {
                        if(tokens[1].equals(memory.MemoryArray[i])){
                            dataPath=(String)memory.MemoryArray[i+10];
                            break;
                        }
                    }
                    writeFile(dataPath,writeData);
                    break;

                default:
                    System.out.println("WRONG COMMAND");
            }


       // }
        //myFile.close();
    }


    public static Scanner readFile(String filePath) {

        File myObj = new File(filePath);
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
            FileWriter myWriter = new FileWriter(filePath,false);
            myWriter.write(data);
            myWriter.flush();
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int add(String x, String y,Object[] memoryArray) {
        int x_Value=0;
        int y_Value=3;
        for (int i = 10; i < 20; i++) {
            if(x.equals(memoryArray[i])){
                x=memoryArray[i+10].toString();
                x_Value=Integer.parseInt(x);
                break;
            }
        }
        for (int i = 10; i < 20; i++) {
            if(y.equals(memoryArray[i])){
                y=memoryArray[i+10].toString();
                y_Value=Integer.parseInt(y);
            }
        }
        return x_Value + y_Value;
    }

    public static void main (String[] args) throws OSException {
        OSapp app =new OSapp();
        String[] filePath={"src/data/Program 1.txt","src/data/Program 2.txt","src/data/Program 3.txt"};
        //app.programAssign(filePath);
        app.scheduler(filePath);
      // for (int i = 0; i < app.memory.MemoryArray.length; i++) {
           // System.out.println(app.memory.MemoryArray[i]);
      //  }

        //app.interpreter("src/data/Program 1.txt");
        /*for (int i = 0; i < app.memory.MemoryArray.length; i++) {
            System.out.println(app.memory.MemoryArray[i]);
        }*/

       // System.out.println(app.getProgramName("src/data/Program 3.txt"));

    }


}
