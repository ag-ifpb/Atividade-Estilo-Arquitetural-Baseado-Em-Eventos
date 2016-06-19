
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class LoaderNode4 {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

//        Socket socket = new Socket("localhost", 40999);//, inetAddress, 40995);
//        Socket socket = new Socket("localhost", 40999);
        Socket socket = new Socket();
        socket.setReuseAddress(true);
//        socket.bind(new InetSocketAddress("192.168.2.108", 40995));
        socket.bind(new InetSocketAddress("localhost", 40995));

        socket.connect(new InetSocketAddress("localhost", 40999));

        byte[] receivedMessage = new byte[1024];

        for (int i = 0; i < 100000; i++) {
            socket.getInputStream().read(receivedMessage);

            System.out.println(new String(receivedMessage));

            Thread.sleep(1000);
        }

    }

}
