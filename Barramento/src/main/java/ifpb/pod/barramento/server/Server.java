/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.barramento.server;

import ifpb.pod.barramento.topic.Topic;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class Server {
    
    private ServerSocket serverSocket;
    private Topic topic1;
    private Topic topic2;
    private Topic topic3;
    
    public Server(){
        
        topic1 = new Topic();
        topic2 = new Topic();
        topic3 = new Topic();
    }
    
    public void turnOn() throws IOException {
        
        serverSocket = new ServerSocket();
        
        serverSocket.bind(new InetSocketAddress("localhost", 40999));
        
        startServerListenner();
    }
    
    ;
    
    public void turnOff() throws IOException {
        
        serverSocket.close();
    }
    
    public void restart() throws IOException {
        
        turnOff();
        turnOn();
    }
    
    private void startServerListenner() {
        
        while (!serverSocket.isClosed()) {
            
            try {
                
                Socket clientSocket = serverSocket.accept();
                
                switch (clientSocket.getPort()) {
                    
                    case 40998: {
                        topic1.setPublisherNode(clientSocket);
                    }
                    break;
                    case 40997: {
                        topic3.setPublisherNode(clientSocket);
                        topic2.setPublisherNode(clientSocket);
                    }
                    break;
                    case 40996: {
                        topic1.registerNode(clientSocket);
                        topic3.registerNode(clientSocket);
                    }
                    break;
                    case 40995: {
                        topic1.registerNode(clientSocket);
                        topic2.registerNode(clientSocket);
                    }
                    break;
                    
                }
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
}
