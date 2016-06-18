/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.barramento.topic;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.content.text.plain;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class Topic extends Thread {

    private Socket publisherNode;
    private final Queue<byte[]> topicBuffer;
    private final List<Socket> registeredNodes;

    public Topic() {
        super();

        topicBuffer = new LinkedList<>();
        registeredNodes = new ArrayList<>();
    }

    public Topic(Socket publisherNode) {

        super();

        topicBuffer = new LinkedList<>();
        registeredNodes = new ArrayList<>();
        this.publisherNode = publisherNode;
    }

    @Override
    public void run() {

        try {

            InputStream inPublisherNode = publisherNode.getInputStream();

            byte[] receivedMessage;

            while (publisherNode != null && publisherNode.isConnected()) {

                if (inPublisherNode.available() > 0) {

                    receivedMessage = new byte[inPublisherNode.available()];

                    topicBuffer.offer(receivedMessage);

                    notifyNodes();
                    
                    System.out.println("notifiquei com isso: "+new String(receivedMessage));
                }

                sleep(100);
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Topic.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void notifyNodes() throws IOException {

        Iterator<byte[]> itTopicBuffer = topicBuffer.iterator();
        Iterator<Socket> itRegisteredNodes = registeredNodes.iterator();

        while (itTopicBuffer.hasNext()) {

            byte[] currentMessage = itTopicBuffer.next();

            while (itRegisteredNodes.hasNext()) {

                Socket currentRegisteredNode = itRegisteredNodes.next();

                if (currentRegisteredNode == null || currentRegisteredNode.isClosed()) {
                    itRegisteredNodes.remove();
                } else {
                    currentRegisteredNode.getOutputStream().write(currentMessage);
                }

                itRegisteredNodes.remove();

            }

        }

    }

    public void registerNode(Socket node) {
        registeredNodes.add(node);
    }

    public Socket getPublisherNode() {
        return publisherNode;
    }

    public void setPublisherNode(Socket publisherNode) throws IOException {
        
        if(publisherNode != null){
            publisherNode.close();
        }
        
        this.publisherNode = publisherNode;
        
        this.start();
        
    }
    
}
