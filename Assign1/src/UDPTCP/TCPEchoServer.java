package UDPTCP;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by delorian1986 on 2017-02-02.
 */

public class TCPEchoServer {

    /**
     *
     */
    private static final int MYPORT = 4950;
    private boolean serverOn = true;
    protected int bufsize;
    protected int port = 12000;
    protected final String MSG = "An Echo Message!";
    protected byte[] buf;

    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        TCPEchoServer TCPS = new TCPEchoServer();
        try {
            TCPS.TCPServer();
        }
        catch(IOException e){
            e.getStackTrace();
        }
    }

    /**
     *
     * @throws IOException
     */

    public void TCPServer() throws IOException {


        ServerSocket serv = new ServerSocket(MYPORT);
        System.out.println("running");
        // it checks for connections and creates a new thread for each
        while (true) {
            Socket clientSocket = serv.accept();
            ServerThread ct = new ServerThread(clientSocket);
            ct.start();
        }
    }

    /**
     *
     */

    private class ServerThread extends Thread{

        /**
         * Global Variables
         */

        private String clientSentence;
        private String capitalizedSentence;
        private byte[] buf;

        /**
         * Class declarations
         */

        Socket clientSocket;
        TCPShared ts = new TCPShared();

        //Constructor
        private ServerThread(Socket clientSocket){
            this.clientSocket = clientSocket;
        }

        /**
         * The thread run class
         */

        public void run(){
            System.out.println("connected clients ip address : "+clientSocket.getInetAddress());
            try{

                // receives
                buf = ts.readBytes(clientSocket);
                clientSentence = new String(buf);
                System.out.println("Received: " + clientSentence+ ". using Thread: " +Thread.currentThread().getId() );
                //makes it big
                capitalizedSentence = clientSentence.toUpperCase();
                buf = capitalizedSentence.getBytes();
                //sends it back
                ts.sendBytes(buf, clientSocket);

            }
            catch(IOException e){

                System.out.println(e.getStackTrace());

            }
        }
    }
}
