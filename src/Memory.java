import java.util.Hashtable;

public class Memory {
    Object [] MemoryArray;
    public Memory(){
        MemoryArray=new Object[50];
    }
    public void assignPCB(PCB pcb){
        boolean isFull=true;
        for(int i=0;i<10;i++){
            if(MemoryArray[i]==null){
                MemoryArray[i]=pcb;
                isFull=false;
                break;
            }
        }
        if(isFull){
            MemoryArray[9]=pcb;
        }
    }
    public void assignVariable(String variableName,Object value){
        boolean isFull=true;
        for(int i=10;i<MemoryArray.length-10;i++){
        if(MemoryArray[i]==null){
            MemoryArray[i]=variableName;
            MemoryArray[i+10]=value;
            isFull=false;
            break;
        }
        }
        if(isFull){
            MemoryArray[19]=variableName;
            MemoryArray[29]=value;
        }
    }

}
