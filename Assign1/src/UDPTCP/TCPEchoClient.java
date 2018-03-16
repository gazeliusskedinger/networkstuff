package UDPTCP;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class TCPEchoClient extends Protocol{

    public static void main(String[] args) {
        /**
         * a for loop for testing the threads.
         */
        for(int i = 0; i<3; i++){
            ClientThread ct = new ClientThread(args);
            ct.start();
        }
    }

    /**
     * The only reason i made a threaded client is to be able to test that
     * the threaded server works
     */

    private static class ClientThread extends Thread{

        private String[] args;
        private byte[] buf;

        ArrayList<String> msgList = new ArrayList<String>();

        public ClientThread(String[] args){
            super();
            this.args = args;
        }

        /**
         * Run function
         */

        public void run(){
            String rxMsg;
            for(int i = 0; i < Integer.parseInt(args[2]);i++){
            try{
                args = sortArgs(args);
                chkInput(args);
                Socket clientSocket = new Socket(args[0],Integer.parseInt(args[1]));
                TCPShared ts = new TCPShared();

                buf = MSG.getBytes();
                sleep(1000/Integer.parseInt(args[2]));
                ts.sendBytes(buf,clientSocket);
                buf = ts.readBytes(clientSocket);
                rxMsg = new String(buf);

                System.out.println("INT : "+i+"FROM SERVER: "+ rxMsg);
                clientSocket.close();

            }
            catch(IllegalArgumentException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            }
        }
    }

    private void chkArgs(String[] args){

    }

}
