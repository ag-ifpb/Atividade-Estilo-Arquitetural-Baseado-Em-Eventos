/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.barramento.topic;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class Topic {

    private List<Socket> registeredNodes = new ArrayList<>();

    private final String topicType;

    public Topic(String topicType) {

        this.topicType = topicType;
    }

    public void notifyNodes(byte[] message) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("Messagem from: ");
        sb.append(topicType);
        sb.append(":\n ");
        sb.append(new String(message));

        message = sb.toString().getBytes();

        Iterator<Socket> itRegisteredNodes = registeredNodes.iterator();

        while (itRegisteredNodes.hasNext()) {

            Socket currentRegisteredNode = itRegisteredNodes.next();

            try {
                currentRegisteredNode.getOutputStream().write(message);
                currentRegisteredNode.getOutputStream().flush();
            } catch (Exception ex) {
                itRegisteredNodes.remove();
            }

        }

    }

    public void registerNode(Socket node) {
        registeredNodes.add(node);
    }

    public String getTopicType() {
        return topicType;
    }

}
