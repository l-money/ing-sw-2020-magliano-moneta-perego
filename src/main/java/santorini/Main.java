package santorini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] a) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String address = null, name = null;
        try {
            System.out.print("Inserisci indirizzo server: ");
            address = br.readLine();
            System.out.print("Inserisci il tuo nome: ");
            name = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        View v = new View();
        NetworkHandlerClient handlerClient = new NetworkHandlerClient(address, name, v);
        v.setHandlerClient(handlerClient);
        new Thread(handlerClient).start();
    }
}