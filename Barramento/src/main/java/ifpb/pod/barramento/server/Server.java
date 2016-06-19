/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.barramento.server;

import ifpb.pod.barramento.topic.TopicManager;
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
    private TopicManager topicManager;
    
    public Server(){
        
        topicManager = new TopicManager();
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
                
                topicManager.registerSocketRequest(clientSocket);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
}
