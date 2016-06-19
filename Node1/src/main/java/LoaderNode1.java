
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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
public class LoaderNode1 {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

        InetAddress inetAddress = InetAddress.getLocalHost();

        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress("localhost", 40998));
        socket.connect(new InetSocketAddress("localhost", 40999));

        for (int i = 0; i < 10; i++) {
            socket.getOutputStream().write(new String("Node 1\n").getBytes());
            socket.getOutputStream().flush();
            System.out.println("escrito");
            Thread.sleep(500);
        }

    }

}
