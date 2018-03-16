import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.io.File;
import java.util.Scanner;

/**
 * The modified UDP Client
 */

public class MainClient extends Protocol{

    public static void main(String[] args) throws IOException {
        byte[] buf= new byte[BUFSIZE];
        if (args.length != 2) {
            System.err.printf("usage: %s server_name port\n", args[1]);
            System.exit(1);
        }
        //chkInput(args);
        menu(args, buf);

        /* Compare sent and received message */

    }

    /**
     * Menu
     */

    private static void menu(String[] args, byte[]buf) throws IOException{

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
        DatagramPacket sendPacket;
        String receivedString;
        String input;
        String msg;
        String menuGuide = readMessage("Menu_guide");
        Scanner sc = new Scanner(System.in);
        System.out.println(readMessage("intro_title"));
        do {
            System.out.print(menuGuide);
            input = sc.nextLine();
            switch (input){
                case "0":
                    System.out.println("Exiting!");
                    msg = "0";

                    /* Create datagram packet for sending message */
                    sendPacket = new DatagramPacket(msg.getBytes(),
                                    msg.length(),
                                    remoteBindPoint);

                    /* Send and receive message*/
                    socket.send(sendPacket);
                    socket.receive(receivePacket);

                    receivedString=
                            new String(receivePacket.getData(),
                                    receivePacket.getOffset(),
                                    receivePacket.getLength());
                    System.out.println(receivedString);

                    break;
                case "1":
                    System.out.println("Starting UDPEchoServer.");
                    msg = "1";
                    /* Create datagram packet for sending message */
                    sendPacket = new DatagramPacket(msg.getBytes(),
                                    msg.length(),
                                    remoteBindPoint);

                    /* Send and receive message*/
                    socket.send(sendPacket);
                    socket.receive(receivePacket);

                    receivedString=
                            new String(receivePacket.getData(),
                                    receivePacket.getOffset(),
                                    receivePacket.getLength());
                    System.out.println(receivedString);

                    break;
                case "2":
                    System.out.println("Starting UDP Client.");
                    break;
                case "3":
                    System.out.println("Stoping UDP Server.");

                    /* Create remote endpoint */
                    SocketAddress remoteBindPoint2=
                            new InetSocketAddress(args[0],
                                    Integer.valueOf("4950"));
                    msg = "Kill";
                    /* Create datagram packet for sending message */
                    sendPacket = new DatagramPacket(msg.getBytes(),
                            msg.length(),
                            remoteBindPoint2);

                    /* Send and receive message*/
                    socket.send(sendPacket);
                    socket.receive(receivePacket);

                    receivedString=
                            new String(receivePacket.getData(),
                                    receivePacket.getOffset(),
                                    receivePacket.getLength());
                    System.out.println(receivedString);
                    break;
                default:
                    System.out.println("Wrong Input\nTry Again!");
            }



        }while(!input.equals("0") );
        socket.close();
        sc.close();
    }

    /**
     * The title reader
     */
    private static String readMessage(String filepath){
        String menu = "";
        File file = new File(filepath);
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                menu = menu+in.nextLine()+"\n";
            }
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return menu;
    }



}
