/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.barramento.topic;

import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class TopicController implements Runnable {

    private List<Socket>    topicPublishers     = new ArrayList<>();
    private HashSet<Topic>  controlledTopics    = new LinkedHashSet<>();
    private Queue<byte[]>   topicBuffer         = new LinkedList<>();

    private Thread notificationThread;

    @Override
    public void run() {

        InputStream in;

        byte[] receivedMessage;

        while (existsPublisher()) {

            try {

                for (Iterator<Socket> it = topicPublishers.iterator(); it.hasNext();) {
                    
                    Socket topicPublisher = it.next();
                    in = topicPublisher.getInputStream();
                    if (in.available() > 0) {
                        receivedMessage = new byte[in.available()];
                        in.read(receivedMessage);

                        topicBuffer.offer(receivedMessage);

                        notifyTopics();
                    }
                }

                sleep(200);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }

    private void notifyTopics() throws InterruptedException {

        if (notificationThread == null || !notificationThread.isAlive()) {

            notificationThread = new Thread() {

                @Override
                public void run() {

                    Iterator<byte[]> itTopicBuffer = topicBuffer.iterator();

                    while (itTopicBuffer.hasNext()) {

                        byte[] message = itTopicBuffer.next();

                        for (Topic topic : controlledTopics) {
                            try {
                                topic.notifyNodes(message);
                            } catch (IOException ex) {
                                Logger.getLogger(TopicController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        itTopicBuffer.remove();
                    }
                }

            };
            notificationThread.start();

        } else {
            notificationThread.join();
        }

    }

    public void addPublisherNode(Socket publisherNode) {

        if (!existsPublisher()) {
            topicPublishers.add(publisherNode);
            new Thread(this).start();
        } else {
            topicPublishers.add(publisherNode);
        }

    }

    public void registerNode(Socket socketToRegister, String topicType) {

        for (Topic topic : controlledTopics) {

            if (topicType.equals(topic.getTopicType())) {
                topic.registerNode(socketToRegister);
            }
        }

    }

    private boolean existsPublisher() {

        boolean response = false;

        Iterator<Socket> itPublishers = topicPublishers.iterator();

        Socket publisher;

        while (itPublishers.hasNext()) {

            publisher = itPublishers.next();

            if (publisher == null || publisher.isClosed()) {
                itPublishers.remove();
            } else {
                response = true;
            }

        }

        return response;
    }

    public void addTopic(Topic topic) {
        controlledTopics.add(topic);
    }

}
