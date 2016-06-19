/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loader;

import ifpb.pod.barramento.server.Server;
import java.io.IOException;

/**
 *
 * @author Victor Hugo <victor.hugo.origins@gmail.com>
 */
public class LoaderBarramento {
    
    public static void main(String[] args) throws IOException {
        
        Server server = new Server();
        
        server.turnOn();
    }
    
}
