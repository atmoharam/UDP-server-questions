import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main ( String[] args)  {
        try {
            //make a client socket no parameters it will choose a random port
            DatagramSocket Clientsocket = new DatagramSocket();
            //array to receive data from server
            byte [] responseBytes = new byte[4096];
            //array to send data to server
            byte [] requestBytes;
            Scanner Input = new Scanner(System.in);
            //loop to send more than one msg
            while (true) {
                System.out.println("if you need to ask server about your ip write this: pc ip address");
                System.out.println("if you need to ask server about weather write this: weather");
                System.out.println("if you need to ask server about time write this: current time");
                //string that will send to server
                String question = Input.nextLine();
                //convert string into request array
                requestBytes = question.getBytes();
                //check the input string if it = "close" it will close the program
                if(question.toLowerCase().equals("close") ||question.toLowerCase().equals("exit") )
                {
                    System.out.println("connection is closed");
                    Clientsocket.close();
                    break;
                }
                //packet that send to server with ip and port = 2100 (we use localhost because the two sides of program will run the same device
                DatagramPacket myclientpacket = new DatagramPacket(requestBytes, requestBytes.length, InetAddress.getByName("localhost"), 2100);
                Clientsocket.send(myclientpacket);
                //packet received from server ( server replay )
                DatagramPacket Serverpacket = new DatagramPacket(responseBytes, responseBytes.length);
                Clientsocket.receive(Serverpacket);
                //convert the data to string
                String Server_replay = new String(Serverpacket.getData());
                System.out.println( Server_replay);
                //delete data that  received from server
                responseBytes = new byte[ 4096 ];

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
