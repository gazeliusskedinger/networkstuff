package lab1;

/**
 * Created by delorian1986 on 2017-02-02.
 */
public class ClientMain {

    public static void main(String[] args) {
        // for creating and testing more threads
        for(int i = 0; i < 1; i++){
            ClientThread client = new ClientThread(args);
            client.start();
        }
    }
    private class ClientThread extends Thread {
        String[] args;

        public ClientThread(String[] args){
            super();
            this.args = args;
        }

        public void run(){

            try {
                ProtocolClient client;
                client = new TCPEchoClient(args);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


