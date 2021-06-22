public class PCB {
    int programID;
    State programState;
    int programCounter;
    int[] boundaries;
public PCB(int programID,State programState,int programCounter,int[] boundaries){
    this.programID=programID;
    this.programCounter=programCounter;
    this.programState=programState;
    this.boundaries=boundaries;
}
public String toString(){
    return "ID: "+programID+" State: "+programState+" PC: "+programCounter+ " Min: "+boundaries[0]+" Max: "+boundaries[1];

}
}
