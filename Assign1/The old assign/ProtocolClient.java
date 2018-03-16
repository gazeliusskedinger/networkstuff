package lab1;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by delorian1986 on 2017-01-26.
 */
public abstract class ProtocolClient{

    protected int bufsize;
    protected int port = 12000;
    protected final String MSG = "An Echo Message!";
    protected String[] args;
    protected byte[] buf;
    protected DatagramSocket socket;
    protected SocketAddress localBindPoint;
    protected SocketAddress remoteBindPoint;
    protected final int SECOND = 1000000000;

    public void setBufSize(String bufsize){
        if(bufsize == "0") {
            this.bufsize = 1024;
        }
        else{
            this.bufsize = Integer.parseInt(bufsize);
        }
    }

    // checking bufsize
    private boolean checkBufSize(String bufsize){
        /*if(checkForNumbers(bufsize)){
            return true;
        }

        // im not sure if buf size is suppose to be between different values
        // so i commented out it to show that it is here just in case.
        int b = Integer.parseInt(bufsize);
        if(b < 0 || b > 1024){
            return true;
        }
        else{
            setBufSize(bufsize);
        }
        return false;*/

        try {
            int b = Integer.parseInt(bufsize);
            boolean bufvalid = !(b > 0 && b <= 1024);
            if(!bufvalid) this.bufsize = b;

            return bufvalid;
        }
        catch (Exception e){
            return true;
        }
    }

    // checks ip for
    private boolean ipCheck(String ip){

        String[] toNumArray = ip.split("\\.");
        int ipIntByte;

        // check that the ip is the right length
        if(toNumArray.length == 4 ){
            for(int i = 0; i < toNumArray.length; i++){
                if(checkForNumbers(toNumArray[i])){
                    return true;
                }
                ipIntByte = Integer.parseInt(toNumArray[i]);
                // check if ip is in the right range
                if(ipIntByte < 0){
                    return true;
                }
                if(ipIntByte >255){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    // checks if port number is within the right range
    private boolean portCheck(String port){
        if(checkForNumbers(port)){
            return true;
        }
        int p = Integer.parseInt(port);
        if(p < 0){
            return true;
        }
        if(p > 65535){
            return true;
        }
        return false;
    }
    private boolean nrOfMesseges(String nrofmess){
        if(checkForNumbers(nrofmess)){
            return true;
        }
        int nr = Integer.parseInt(nrofmess);
        if(nr < 0){
            return true;
        }
        return false;
    }
    // a combined check for all above
    private boolean checkInputValid(String[] args){
        if(args.length != 4 ){
            return true;
        }
        if(ipCheck(args[0])){
            return true;
        }
        if(portCheck(args[1])){
            return true;
        }
        if(nrOfMesseges(args[3])){
            return true;
        }
        if(checkBufSize(args[2])){
            return true;
        }
        // needs to be uncommented for the UDPServer
        /*if(chkDVSMsgLen(MSG)){
            return true;
        }*/
        return false;// if all args valid.
    }

    //Checks input for letters or other specials
    private boolean checkForNumbers(String arr){
        for(int i = 0; i < arr.length();i++){
            //compares to ascii table
            if(((int)arr.charAt(i) < 48) || (int)arr.charAt(i) > 57 ){
                return true;
            }
        }
        return false;
    }
    //checks that the message is within the desired buffer size
    private boolean chkDVSMsgLen(String msg){
        if(msg.length() > bufsize){
            return true;
        }
        return false;
    }

    // An quick exit if wrong input was detected in that case it exits without exceptions.
    protected void exitIfWrongInput(String[] args ){

        if (checkInputValid(args)) {
            System.out.println("Please enter the arguments in ONLY integers and check that\nthe IP is" +
                            "in the format #.#.#.# and between 0 and 255, and the PORT nr \nto be " +
                            "between 1 and 65535.\n" +
                            "Another good thing to check is that the buffersize is within \nwhe msg " +
                            "size, the only reason you're seeing this message is that \n" +
                            "something of this was wrong so i ask you to please try again!");
            System.exit(1);
        }
        if(checkInputValid(args)){
            System.exit(1);
        }
        else{
            System.out.println("Run Successful");
        }
    }
    // set up the socket, bytes to send.
    protected void initialServerSetupStuff(String[] args) throws IOException{
        buf = new byte[bufsize];
        socket = new DatagramSocket(null);
        localBindPoint = new InetSocketAddress(port);
        socket.bind(localBindPoint);
        remoteBindPoint = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
    }
    // sleep funktion
    protected void sleep(long nano){
        //long testtime = System.nanoTime();
        long start;
        long stop;
        long time = 0;
        long times = 0;
        long totalTime = 0;
        while (totalTime < nano){
            start = System.nanoTime();
            times = times + times++;
            stop = System.nanoTime();
            time = stop-start;
            totalTime = totalTime + time;
        }
        //long stoptime = System.nanoTime();
        //System.out.printf("extradelaytime:%d \n",stoptime-testtime);
    }



//just a test class to test functions in this class...
    public static void main(String[] args) {

        String[] str = {"123", "1b2", ":98","/54","1 7", "300"};
        for(int i = 0; i < str.length; i++){

        }
    }
}
