import java.io.*;
import java.net.*;


public class RecepteurDeFichierTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null; 
        Socket clientSocket = null;
        BufferedReader in = null;
        String nomFichier = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        int port = Integer.parseInt(args[0]);
        try {
            serverSocket = new ServerSocket(port); // création socket serveur
        } catch (IOException e) {
            System.err.println("Impossible d'écouter sur le port: " + port + "."); // gestion erreur
            System.exit(1); // fermeture du programme
        }
        System.out.println("Serveur à l'écoute sur le port: " + port + "."); // affichage du port d'écoute
        try {
            clientSocket = serverSocket.accept(); // attente connexion client
        } catch (IOException e) { // gestion erreur
            System.err.println("Accept a échoué.");
            System.exit(1);
        }
        out = new PrintWriter(clientSocket.getOutputStream(), true); // création buffer de sortie
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // création buffer d'entrée
        String inputLine; // initialisation message reçu
        while ((inputLine = in.readLine()) != null) { // tant que le message reçu n'est pas "fin"
            System.out.println("Reception du fichier: " + inputLine); // affichage message reçu
            nomFichier = inputLine; // lecture message reçu
            try {
                fos = new FileOutputStream(nomFichier); // création du fichier
                bos = new BufferedOutputStream(fos); // création buffer de sortie
            } catch (FileNotFoundException e) {
                System.err.println("Fichier non trouvé: " + nomFichier); // gestion erreur
                System.exit(1); // fermeture du programme
            }
            while ((inputLine = in.readLine()) != null) { // tant que le message reçu n'est pas "fin"
                System.out.println("Reception du fichier: " + inputLine); // affichage message reçu
                inputLine = inputLine + System.getProperty("line.separator"); // ajout du retour à la ligne
                bos.write(inputLine.getBytes()); // écriture dans le fichier
            }
            bos.close(); // fermeture buffer de sortie
            fos.close(); // fermeture fichier
            clientSocket.close(); // fermeture socket
            serverSocket.close(); // fermeture socket serveur
        }
    }
}

// Lancé avec : javac RecepteurDeFichierTCP.java && java RecepteurDeFichierTCP 1234
