package lab1;

import java.io.*;
import java.net.Socket;

/**
 * Created by delorian1986 on 2017-02-12.
 */
public class Extras {
    public Extras(){

    }
    // send's  bytes(only byte array and socket as input)
    public void sendBytes(byte[] myByteArray, Socket socket) throws IOException {
        sendBytes(myByteArray, 0, myByteArray.length, socket);
    }

    // Send's specific bytes of an array (byte arr, start, stop, socket)
    public void sendBytes(byte[] myByteArray, int start, int len, Socket socket) throws IOException {
        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        //sends the length of the array
        dos.writeInt(len);
        if (len > 0) {
            // and then sends the message
            dos.write(myByteArray, start, len);
        }
    }
    //Reads the bytes
    public byte[] readBytes(Socket socket) throws IOException {

        InputStream in = socket.getInputStream();
        DataInputStream dis = new DataInputStream(in);
        // receives the length
        int len = dis.readInt();
        byte[] data = new byte[len];
        if (len > 0) {
            // receives the message
            dis.read(data);
        }
        return data;
    }
}
