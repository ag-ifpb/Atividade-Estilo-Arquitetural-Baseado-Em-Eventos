/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pod.barramento.topic;

import ifpb.pod.barramento.Enums.TopicTypes;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class TopicManager {

    private final TopicController topicController1;
    private final TopicController topicController2And3;
    
    public TopicManager(){
        
        topicController1        = new TopicController();
        topicController2And3    = new TopicController();
        
        topicController1.addTopic(new Topic(TopicTypes.TOPICO_1.name()));
        
        topicController2And3.addTopic(new Topic(TopicTypes.TOPICO_2.name()));
        topicController2And3.addTopic(new Topic(TopicTypes.TOPICO_3.name()));
    }

    public void registerSocketRequest(Socket socketRequest) throws IOException {

        switch (socketRequest.getPort()) {

            case 40998: {
                topicController1.addPublisherNode(socketRequest);
            }
            break;
            case 40997: {
                topicController2And3.addPublisherNode(socketRequest);
                topicController2And3.addPublisherNode(socketRequest);
            }
            break;
            case 40996: {
                topicController1.registerNode(socketRequest, TopicTypes.TOPICO_1.name());
                topicController2And3.registerNode(socketRequest, TopicTypes.TOPICO_3.name());
            }
            break;
            case 40995: {
                topicController1.registerNode(socketRequest, TopicTypes.TOPICO_1.name());
                topicController2And3.registerNode(socketRequest, TopicTypes.TOPICO_2.name());
            }
            break;

        }
    }

}
