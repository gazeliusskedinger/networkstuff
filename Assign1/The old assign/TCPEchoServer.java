package lab1;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by delorian1986 on 2017-02-02.
 */
public class TCPEchoServer {
    private static final int MYPORT = 4950;
    private boolean serverOn = true;

    public static void main(String[] args) {
        TCPEchoServer TCPS = new TCPEchoServer();
        try {
            TCPS.TCPServer();
        }
        catch(IOException e){
            e.getStackTrace();
        }
    }

    public void TCPServer() throws IOException {


        ServerSocket serv = new ServerSocket(MYPORT);
        System.out.println("running");
        // it checks for connections and creates a new thread for each
        while (serverOn) {
            Socket clientSocket = serv.accept();
            ServerThread ct = new ServerThread(clientSocket);
            ct.start();

            // made a turn of function that it never reaches just for the socket.close
            // TODO make a server side function to turn it off...

            if(!serverOn){
                clientSocket.close();
            }
        }
    }
    // the thread
    private class ServerThread extends Thread{
        Socket clientSocket;
        String clientSentence;
        String capitalizedSentence;
        byte[] buf;
        //Constructor
        private ServerThread(Socket clientSocket){
            this.clientSocket = clientSocket;
        }

        public void run(){
            System.out.println("connected clients ip address : "+clientSocket.getInetAddress());
            try{
                Extras ex = new Extras();
                // receives
                buf = ex.readBytes(clientSocket);
                clientSentence = new String(buf);
                System.out.println("Received: " + clientSentence);
                //makes it big
                capitalizedSentence = clientSentence.toUpperCase();
                buf = capitalizedSentence.getBytes();
                //sends it back
                ex.sendBytes(buf, clientSocket);

            }
            catch(IOException e){

                System.out.println(e.getStackTrace());

            }
        }
    }
}
