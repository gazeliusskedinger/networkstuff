import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class MainServer {
    public static final int BUFSIZE= 1024;
    public static final int MYPORT= 5000;

    public static void main(String[] args) throws IOException {
        byte[] buf= new byte[BUFSIZE];

        /* Create socket */
        DatagramSocket socket= new DatagramSocket(null);

        /* Create local bind point */
        SocketAddress localBindPoint= new InetSocketAddress(MYPORT);
        socket.bind(localBindPoint);
        boolean onOff = true;
        while (onOff) {
            /* Create datagram packet for receiving message */
            DatagramPacket receivePacket= new DatagramPacket(buf, buf.length);
            /* Receiving message */
            socket.receive(receivePacket);

            System.out.printf("Command request from %s", receivePacket.getAddress().getHostAddress());
            System.out.printf("Using port %d\n", receivePacket.getPort());
            String msg ="";

            DatagramPacket sendPacket;

            String input = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());

            int decide = Integer.parseInt(input);
            switch (decide){
                case 0:
                    msg = "Main server exiting";
                    System.out.println("Main Server Exiting!");

                    /* Create datagram packet for sending message */
                    sendPacket = new DatagramPacket(msg.getBytes(),
                            msg.length(),
                            receivePacket.getAddress(),
                            receivePacket.getPort());

                    /* Send message*/
                    socket.send(sendPacket);
                    onOff = false;
                    break;
                case 1:
                    System.out.println("Starting UDPEchoServer.");
                    msg = "Starting UDPEchoServer";
                    System.out.println("");

                    /* Create datagram packet for sending message */
                    sendPacket = new DatagramPacket(msg.getBytes(),
                            msg.length(),
                            receivePacket.getAddress(),
                            receivePacket.getPort());
                            UDPEchoServer ues = new UDPEchoServer(args);
                    /* Send message*/
                    socket.send(sendPacket);
                    break;
                case 2:
                    System.out.println("Starting UDP Client.");
                    break;
                case 3:
                    System.out.println("Stoping UDP Server.");
                    break;
                default:
                    /* Create datagram packet for sending message */
                    msg = "Something went wrong!!!!";
                    sendPacket = new DatagramPacket(msg.getBytes(),
                            msg.length(),
                            receivePacket.getAddress(),
                            receivePacket.getPort());

                    /* Send message*/
                    socket.send(sendPacket);
                    System.out.println("Wrong Input\nTry Again!");
            }
        }
        socket.close();
    }
}
