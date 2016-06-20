package ifpb.pod.node3;

import java.io.IOException;
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
public class LoaderNode3 {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

        Socket socket = new Socket();
        socket.setReuseAddress(true);

        socket.bind(new InetSocketAddress("localhost", 40996));

        socket.connect(new InetSocketAddress("localhost", 40999));

        byte[] receivedMessage;

        while (socket.isConnected()) {

            if (socket.getInputStream().available() > 0) {
                
                receivedMessage = new byte[socket.getInputStream().available()];
                
                socket.getInputStream().read(receivedMessage);
                
                System.out.print(new String(receivedMessage));
            }

            Thread.sleep(500);
        }

    }

}
