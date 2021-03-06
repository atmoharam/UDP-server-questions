import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    public static void main ( String[] args)  {
        try
        {
            //create a server socket UDP
            DatagramSocket ServerSocket = new DatagramSocket(2100);
            //create array to receive data from client
            byte [] requestBytes = new byte[4096];
            //create array to send response
            byte [] responseBytes;
            //inf loop
            while (true)
            {
                //take packet and put it to request array
                DatagramPacket ClientPacket = new DatagramPacket(requestBytes,requestBytes.length);
                //receive packet now
                ServerSocket.receive(ClientPacket);
                //make a string to put data in and to make our process easy and remove spaces after and before the string
                String Clientmsg = new String(ClientPacket.getData()).trim();
                //check the question
                switch (Clientmsg.toLowerCase()) {
                    case "weather": {
                        //make string include the weather
                        String weather = "Sunny Day";
                        //convert our string to the response array to send it to client
                        responseBytes = weather.getBytes();
                        //make a response packet to send it to Client / parameters to send ( array , array length , receiver IP , receiver port number)
                        DatagramPacket ServerPacket = new DatagramPacket(responseBytes, responseBytes.length, ClientPacket.getAddress(), ClientPacket.getPort());
                        ServerSocket.send(ServerPacket);
                        break;
                    }
                    case "pc ip address": {
                        //make string that generated by convert packet address to string
                        String ipAddress = (ClientPacket.getAddress()).toString();
                        //convert IP data to the response packet
                        responseBytes = ipAddress.getBytes();
                        //make a response packet to send it to Client / parameters to send ( array , array length , receiver IP , receiver port number)
                        DatagramPacket ServerPacket = new DatagramPacket(responseBytes, responseBytes.length, ClientPacket.getAddress(), ClientPacket.getPort());
                        ServerSocket.send(ServerPacket);
                        break;
                    }
                    case "current time": {
                        //make a new date format "dd/MM/yyyy HH:mm:ss"
                        SimpleDateFormat Time_with_date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        //get date from memory
                        Date date = new Date();
                        //convert date data to bytes to send it into response packet
                        responseBytes = Time_with_date.format(date).getBytes();
                        //make a response packet to send it to Client / parameters to send ( array , array length , receiver IP , receiver port number)
                        DatagramPacket ServerPacket = new DatagramPacket(responseBytes, responseBytes.length, ClientPacket.getAddress(), ClientPacket.getPort());
                        ServerSocket.send(ServerPacket);
                        break;
                    }
                }
                //delete data that  received from Client
                requestBytes = new byte[ 4096 ];
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
