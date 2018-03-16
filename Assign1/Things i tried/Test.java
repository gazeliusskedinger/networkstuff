import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Test extends Protocol{
    public static void UDPclient(String[] args) throws IOException {

        ArrayList<String> msgList = new ArrayList<String>();

        if (args.length != 3) {
            System.err.printf("usage: %s server_name port\n", args[1]);
            System.exit(1);
        }

        // Checks all input
        try {
            chkInput(args);
        }
        catch (IllegalArgumentException e){

        }

        byte[] buf= new byte[Integer.parseInt(args[2])];

        /* Create socket */
        DatagramSocket socket= new DatagramSocket(null);

        /* Create local endpoint using bind() */
        SocketAddress localBindPoint= new InetSocketAddress(MYPORT);
        socket.bind(localBindPoint);

        /* Create remote endpoint */
        SocketAddress remoteBindPoint=
                new InetSocketAddress(args[0],
                        Integer.valueOf(args[1]));

        /* Create datagram packet for receiving echoed message */
        DatagramPacket receivePacket= new DatagramPacket(buf, buf.length);



        msgList = bufferMessageDivider(MSG, Integer.parseInt(args[2]));

        for(int i = 0; i< msgList.size();i++ ) {

            /* Create datagram packet for sending message */

            DatagramPacket sendPacket=
                    new DatagramPacket(msgList.get(i).getBytes(),
                            msgList.get(i).length(),
                            remoteBindPoint);

            /* Send and receive message*/
            socket.send(sendPacket);
            socket.receive(receivePacket);

            String receivedString=
                    new String(receivePacket.getData(),
                            receivePacket.getOffset(),
                            receivePacket.getLength());

            if (receivedString.compareTo(msgList.get(i)) == 0)
                System.out.printf("%d bytes sent and received\n", receivePacket.getLength());
            else
                System.out.printf("Sent and received msg not equal!\n");
        }

        /* Compare sent and received message */

        socket.close();
    }
}
