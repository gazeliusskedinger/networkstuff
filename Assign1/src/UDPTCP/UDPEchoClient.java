package UDPTCP;/*
  UDPEchoClient.java
  A simple echo client with no error handling
*/

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class UDPEchoClient extends Protocol {

	public static void main(String[] args) throws IOException {
		server(args);
    }

    private static void server(String[] args){

		/**
		 * Var Declarations
		 */

		byte[] buf;
		String receivedString;
		long aSec = 496800000;
		long delayTime;
		int msgSent = 0;
		long start;
		long stop;
		long time = 0;

		/**
		 * Class Declarations
		 */

		ArrayList<String> msgList = new ArrayList<String>();
		StopWatch sw = new StopWatch();
		DatagramSocket socket;
		SocketAddress localBindPoint = new InetSocketAddress(MYPORT);
		SocketAddress remoteBindPoint = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
		DatagramPacket receivePacket;
		DatagramPacket sendPacket;

		/**
		 * Error handeling
		 */

		try {
			args = sortArgs(args);
			socket = new DatagramSocket(null);
			socket.bind(localBindPoint);
			chkArgs(args);
			buf = new byte[Integer.parseInt(args[2])];
			msgList = bufferMessageDivider(LONG_MSG, Integer.parseInt(args[3]));
			delayTime = aSec/Integer.parseInt(args[3]);//msgList.size();
			sw.start();

			/**
			 * the "do while" is part of the VG assignement
			 */

			int i = 0;
			do {
				start = System.nanoTime();


				/**
				 * The "for" loop is the regular assignement
				 */

				//for (int i = 0; i < msgList.size(); i++) {

				/* Create datagram packet for sending and returning message */
				sendPacket = makeSend(msgList.get(i),remoteBindPoint);
				receivePacket = new DatagramPacket(buf, buf.length);
				/* Send and receive message*/
				socket.send(sendPacket);
				socket.receive(receivePacket);

				sleep(delayTime);

				receivedString = dataFromPacket(receivePacket);
				testMessage(msgList.get(i),receivedString);
				msgSent++;
				//}
				stop = System.nanoTime();
				time = time + (stop-start);
				i++;

			}while(time < aSec && i < msgList.size() );
			sw.stop();
			System.out.println("Tot msg: "+msgList.size()+"\nMsg's to go: "+(msgList.size()-msgSent));
			System.out.println(sw.getElapsedTime()+": Nanoseconds!!!");
			/* Compare sent and received message */

			socket.close();
		}
		catch(NoRouteToHostException e){
			System.err.println("No Route To Host!!!");
			System.exit(1);
		}
		catch (IllegalArgumentException e){
			e.printStackTrace();
			System.out.print("redo, do right!!!\n -h or --help for more info...");
			System.exit(1);
		}
		catch(SocketException e){

		}
		catch (IOException e){

		}
	}

    private static void chkArgs(String[] args) throws IllegalArgumentException{
		if (args.length < 2) {
			System.err.printf("usage: %s server_name port\n", args[1]);
			System.exit(1);
		}
		// Checks all input
			chkInput(args);
	}

	private static DatagramPacket makeReceive(byte[] buf){
		return new DatagramPacket(buf, buf.length);
	}

	private static DatagramPacket makeSend(String msg,SocketAddress sockAdd){
		return new DatagramPacket(msg.getBytes(),
				msg.length(),
				sockAdd);
	}

	private static  String dataFromPacket(DatagramPacket pack){
		return new String(pack.getData(),
				pack.getOffset(),
				pack.getLength());
	}

	private static void testMessage(String sent, String ret){
		if (ret.compareTo(sent) == 0)
			System.out.printf("%d bytes sent and received\n", ret.length());
		else
			System.out.printf("Sent and received msg not equal!\n");
	}
}

