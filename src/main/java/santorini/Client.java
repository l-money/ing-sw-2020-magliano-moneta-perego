package santorini;

/**
 * @author Magliano
 */

import santorini.model.Table;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {


        NetworkHandlerClient nhc = new NetworkHandlerClient("string adress", "String name");
        Thread thread = new Thread(nhc);
        thread.start();


    }

}
