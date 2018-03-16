package lab1;

import java.io.*;
import java.net.*;

/**
 * Created by delorian1986 on 2017-02-04.
 */

public class TCPEchoClient extends ProtocolClient{

    public TCPEchoClient(String args[])throws Exception{

            //the checks of the inputs
            exitIfWrongInput(args);
            client(args);

    }

    /**
     *
     * @param args
     * @throws Exception
     */

    private void client(String args[]) throws Exception
    {
        for(int i = 0; i < Integer.parseInt(args[3]); i++) {
            String modifiedSentence;
            Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
            Extras ex = new Extras();

            buf = MSG.getBytes();
            Thread.sleep(1000/Integer.parseInt(args[3]));

            // sends
            ex.sendBytes(buf, clientSocket);

            //receives
            buf = ex.readBytes(clientSocket);

            //makes msg back to printable
            modifiedSentence = new String(buf);
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        }
    }
}