package HTMLServer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by delorian1986 on 2017-02-02.
 */
public class Server {
    // The thread handler
    public static void main(String[] args) {
        try {
            ServerSocket serversocket = new ServerSocket(8888);
            System.out.println("Server running on port: 8888");
            while(true){
                new ServerThread(serversocket.accept()).start(); //  CLIENT CONNECTED!
            }
        }
        catch(IOException e){
            e.getStackTrace();
        }
    }
    // for each connection create a thread
    static class ServerThread extends Thread{
        private Socket clientSocket;
        private BufferedReader bufreader;
        private OutputStream output;
        //Constructor
        public ServerThread(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;

            bufreader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = clientSocket.getOutputStream();
        }

        public void run(){
            StringBuilder head = new StringBuilder(), body = new StringBuilder();
            boolean stillhead = true;
            int contentlength = 0;
            // reads the request from client
            try {
                String read = bufreader.readLine();
                head.append(read+"\n");
                String[] type = read.split(" ");
                while(true){
                    read = bufreader.readLine();
                    if(read.equals("")) break;

                    head.append(read+"\n");
                }

                // switch for the type of request
                switch (type[0].toLowerCase()){
                    case "get":
                        System.out.println(head.toString());
                        System.out.println("get request " + type[1]);
                        // read files
                        // '/' -> www
                        String filepath = "www"+type[1];
                        // redirecting to index file
                        if(type[1].equals("/")){
                            filepath = "www/index.html";
                        }
                        // the 403 trigger
                        if(antiTriverseFileSystemBackwardsThing(filepath)){
                            System.out.println(filepath);
                            System.out.println("403");
                            System.out.println(antiTriverseFileSystemBackwardsThing(filepath));
                            output.write("403 Forbidden\r\n\r\n".getBytes());
                            break;
                        }
                        File file = new File(filepath);
                        // error handling for 403 code
                        if(type[1].startsWith("/secret") || type[1].startsWith("/..")){
                            String msg = "403";
                            output.write(String.format("%s 403 Forbidden\r\nContent-Length: %d\r\n\r\n%s", type[2], msg.length(), msg).getBytes());
                        }
                        // -.- 404 code
                        else if(!file.exists()){
                            String msg = "404 kalles kalas puffar";
                            output.write(String.format("%s 404 Not Found\r\nContent-Length: %d\r\n\r\n%s", type[2], msg.length(), msg).getBytes());
                            break;
                        }
                        // reading png
                        else if(file.isFile()){
                            String[] filetype = type[1].split("\\.");
                            String[] filename = type[1].split("/");
                            if(filetype.length == 2) {
                                if (filetype[1].equals("png")) {
                            /*BufferedImage bi = ImageIO.read(file);
                            WritableRaster wr = bi.getRaster();
                            DataBufferByte dbb = (DataBufferByte)wr.getDataBuffer();
                            byte[] image = dbb.getData();

                            output.write(String.format("%s 200 OK\r\nContent-Length: %d\r\n\r\n", type[2], image.length).getBytes());
                            System.out.println(image.length+"m");
                            output.write(image);*/

                                    ImageIO.write(ImageIO.read(file), "png", output);
                                    output.write(String.format("%s 200 OK\r\n\r\n", type[2]).getBytes());
                                }
                                else {
                                    String msg = "Hello World1111";
                                    msg = readHTMLFile(file);
                                    output.write(String.format("%s 200 OK\r\nContent-Length: %d\r\n\r\n%s", type[2], msg.length(), msg).getBytes());
                                }
                            }
                            //reading html
                            else {
                                String msg = "Hello World1111";
                                msg = readHTMLFile(file);
                                output.write(String.format("%s 200 OK\r\nContent-Length: %d\r\n\r\n%s", type[2], msg.length(), msg).getBytes());
                            }

                        }
                        else { // is folder
                            String msg = "<p>Directory</p>";
                            output.write(String.format("%s 200 OK\r\nContent-Length: %d\r\n\r\n%s", type[2], msg.length(), msg).getBytes());
                        }
                        break;

                    case "post": // vg-task
                        // read data and create file
                        break;

                    case "put": // vg-task
                        // read data and update stuff
                        break;

                    default:
                        //String msg = "Not ok";
                        //output.write(String.format("%s 501 \r\nContent-Length %\r\n\r\n%s", type[2], msg.length(), msg).getBytes());
                        // 501 Not implemented
                        break;

                }
            }
            // Error 500 status
            catch (IOException e){
                String msg = "500";
                try{
                    output.write(String.format("%s 500 Internal Server Error\r\nContent-Length: %d\r\n\r\n%s", "HTTP/1.1" , msg.length(), msg).getBytes());
                }
                catch(Exception f){
                    e.printStackTrace();
                }

            }


            //System.out.println(head.toString());
            // closing IO's
            try {
                clientSocket.close();
                output.close();
                bufreader.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
        // reads the html file
        private String readHTMLFile(File file){
            StringBuilder sb = new StringBuilder();
            String msg = "";
            try {
                FileInputStream fis = new FileInputStream(file);

                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                while ((msg = br.readLine()) != null) {
                    sb.append(msg);

                }
                msg = sb.toString();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            return msg;
        }
        // root folder checker
        private boolean antiTriverseFileSystemBackwardsThing(String filepath){
        /*File file = new File(filepath);
        File root = new File("www");

        //maybe a check for ../ ?
        int i = file.compareTo(root);
        try {
            String c = file.getCanonicalPath();
            String r = root.getAbsolutePath();
            if(c.startsWith(r)) return true;
            else return false;
        } catch (IOException e) {
            return false; // for te glory of safety

        }*/
            String[] directory = filepath.split("/");
            int val = 0;
            for(int i = 0; i < directory.length; i++){
                if(directory[i].equals("..")){
                    val--;
                }
                else{
                    val++;
                }
            }
            if(val < 0){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
