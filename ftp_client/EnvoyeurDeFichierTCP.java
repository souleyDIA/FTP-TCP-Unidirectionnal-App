import java.io.*;
import java.net.Socket;

public class EnvoyeurDeFichierTCP {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: javac EnvoyerDeFichierTCP.java && java EnvoyerDeFichierTCP <adresse IP> <port> <chemin vers le fichier>"); // affichage de l'utilisation
            return;
        }
        String ip = args[0]; // récupération de l'adresse IP
        int port = Integer.parseInt(args[1]); // récupération du port
        String cheminFichier = args[2]; // récupération du chemin vers le fichier
        File fichier = new File(cheminFichier); // création d'un objet File pour le fichier
        if (!fichier.exists()) { // si le fichier n'existe pas
            System.out.println("Le fichier n'existe pas" + cheminFichier); // affichage d'un message d'erreur
            return;
        }
        try (Socket socket = new Socket(ip, port); // création d'une socket pour la connexion
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); // création d'un DataOutputStream pour envoyer le fichier
             DataInputStream dis = new DataInputStream(new FileInputStream(fichier))) { // création d'un DataInputStream pour lire le fichier
            dos.writeBytes(fichier.getName() + " \n"); // Envoi du nom du fichier
            int octetLu; // initialisation d'un octet lu
            int nbOctets = 0; // initialisation du nombre d'octets
            
            while ((octetLu = dis.read()) != -1) { // tant qu'il y a des octets à lire
                dos.write(octetLu); // envoi de l'octet lu
                nbOctets++; // incrémentation du nombre d'octets
            }
            System.out.println("Fichier envoyé : " + fichier.getName() + " (" + nbOctets + " octets)"); // affichage du fichier envoyé
        } catch (IOException e) { // gestion des erreurs
            System.out.println("Aucun serveur TCP n'est joignable");
        }
    }
}

// javac EnvoyeurDeFichierTCP.java && java EnvoyeurDeFichierTCP 127.0.0.1 4444 ./file.txt