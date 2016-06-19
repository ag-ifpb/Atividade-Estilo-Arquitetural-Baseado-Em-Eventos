
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
public class LoaderNode2 {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress("localhost", 40997));
        socket.connect(new InetSocketAddress("localhost", 40999));
        
        for(int i=0; i< 10; i++){
            socket.getOutputStream().write(new String("Node 2\n").getBytes());
            socket.getOutputStream().flush();
            System.out.println("escrito");
            Thread.sleep(500);
        }

    }

}
