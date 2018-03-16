package UDPTCP;

import java.util.ArrayList;

public abstract class Protocol {

    protected static final int BUFSIZE = 1024;
    protected static final int MYPORT = 0;
    protected final int SECOND = 1000000000;



    protected static final String MSG = "An Echo Message!An Echo Message!An Echo Message!";
    protected static final String LONG_MSG = "An Echo Message!An Echo Message!An Echo Message!" +
            "An Echo Message!An Echo Message!An Echo Message!An Echo Message!" +
            "An Echo Message!An Echo Message!An Echo Message!An Echo Message!" +
            "An Echo Message!An Echo Message!An Echo Message!An Echo Message!";


    /**
     * Checks the input value.
     * @param args
     * @throws IllegalArgumentException
     */

    protected static void chkInput(String[] args)throws IllegalArgumentException {
        int a = args.length/2;
        if(args[0].equals("-h") || args[0].equals("--help")){
            printHelp();
        }
        else {
            chkIp(args[0]);
            chkPort(args[1]);
            chkBuf(args[2]);
            chkRate(args[3]);
        }
    }

    /**
     * Makes a standard argument array so options can be inserted in different orders
     * @return
     */

    protected static String[] sortArgs(String[] args)  {

        // A standard arg array
        String[] argSorted = new String[4];

        // sets standard values to args if not
        String buf = String.valueOf(BUFSIZE);
        String rate = String.valueOf(10);
        if (args.length > 2) {
            for (int i = 2; i < (args.length); i++) {
                if (args[i].equals("-B")) {
                    buf = args[i+1];
                    i++;
                } else if (args[i].equals("-R")) {
                    rate = args[i+1];
                    i++;
                }
            }
        }

        argSorted[0] = args[0];
        argSorted[1] = args[1];
        argSorted[2] = buf;
        argSorted[3] = rate;

        return argSorted;
    }



    /**
     * Checks ip for error
     * @param ip
     * @throws IllegalArgumentException
     */

    private static void chkIp(String ip)throws IllegalArgumentException {
        String[] ipByte = ip.split("\\.");

        // check that it is four numbers divided by dots.

        if(ipByte.length < 4 || ipByte.length > 4){
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < ipByte.length; i++){

            //chk for letters
            if(chkNumerical(ipByte[i])){
                throw new IllegalArgumentException();
            }
            //chk for range
            if(chkRange(Integer.parseInt(ipByte[i]), 255,0)){
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Check port input valid
     * @param port
     * @throws IllegalArgumentException
     */

    private static void chkPort(String port)throws IllegalArgumentException{

        //chk for unwanted characters
        if(chkNumerical(port)){
            throw new IllegalArgumentException();
        }

        //chk for range
        if(chkRange(Integer.parseInt(port),65535,1)){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Check bufsize input valid
     * @param bufSize
     * @throws IllegalArgumentException
     */

    private static void chkBuf(String bufSize)throws IllegalArgumentException{

        //chk for unwanted characters
        if(chkNumerical(bufSize)){
            throw new IllegalArgumentException();
        }

        //chk for range
        if(chkRange(Integer.parseInt(bufSize),BUFSIZE,1)){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks rate input valid
     * @param rate
     * @throws IllegalArgumentException
     */

    private static void chkRate(String rate)throws IllegalArgumentException{
        if(chkNumerical(rate)){
            throw new IllegalArgumentException();
        }
        if(chkRange(Integer.parseInt(rate), BUFSIZE, 1)){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Two reoccuring checks for all arguments
     */

    /**
     * A function checking the range of a value
     * @param value
     * @param top
     * @param bottom
     * @return
     */

    private static boolean chkRange(int value, int top, int bottom){
        if(value <= top && value >= bottom){
            return false;
        }
        return true;
    }

    /**
     * Checks that inputed arguments are numbers
     * @param input
     * @return
     */

    private static boolean chkNumerical(String input){
        for(int i = 0; i < input.length(); i++){
            if(((int)input.charAt(i) < 48) || (int)input.charAt(i) > 57 ){
                return true;
            }
        }
        return false;
    }

    /**
     * Other help functions
     */

    /**
     * Divides the message up to a buffered array for making the VG assigne ment easier.
     *
     * @param msg
     * @param buff
     * @return
     */

    protected static ArrayList<String> bufferMessageDivider(String msg, int buff){

        ArrayList<String> message = new ArrayList<String>();

        int start = 0;
        int stop = buff;
        int even = 1;

        if(msg.length()%buff == 0){
            even = 0;
        }

        //System.out.println(msg.length()%buff);
        //System.out.println(msg.length()/buff+even);
        for(int i = 0; i < msg.length()/buff+even; i++){
            if(i == (msg.length()/buff)){
                stop -= (buff - msg.length()%buff);
            }
            //System.out.println(i+" "+start+" "+stop+" "+(i == (msg.length()/buff))+" "+(msg.length()%buff != 0));
            //System.out.println(message.size()+" "+ msg.substring(start,stop));
            message.add(msg.substring(start,stop));
            start += buff;
            stop += buff;
        }
        return message;
    }

    /**
     * A sleep method between each packet.
     * @param nano
     */

    protected static void sleep(long nano){
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

    /**
     * Prints the helpmenu.
     */

    private static void printHelp() {
        System.out.println("     UDP/TCP help");
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+");
        System.out.println("Syntax: java UDPEchoServer [IP-ADDRESS] [PORT] [OPTIONS]..." +
                "\n -B [n] : buffersize. maximum = "+BUFSIZE +
                "\n -R [n] : Transmission rate int P/Sec.");
    }

    /**
     * error checking classes
     * @param args
     */

    protected static void argPrint(String[] args){
        for(int i = 0; i < args.length; i++){
            System.out.println(args[i]);
        }

    }
    protected static void listPrint(ArrayList<String> list){
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }

}
