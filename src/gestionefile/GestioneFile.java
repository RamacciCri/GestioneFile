package gestionefile;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian Ramacci
 */
public class GestioneFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //1)LETTURA
        //inizio leggendo il file
        Lettore lettore = new Lettore("user.json");
        lettore.start();
        try {
            lettore.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestioneFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        //2)ELABORAZIONE
        //creo le variabili per salvare l'username e la password
        String username = null;
        String password = null;
        //permetto all'utente di inserire username e password
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Inserisci l'username");
            username = br.readLine().toUpperCase();  // Per comodità metto tutto maiuscolo
            System.out.println("Inserisci la password");
             password = br.readLine().toUpperCase(); // Per comodità metto tutto maiuscolo
        } catch (IOException e) {
            System.err.println("Errore durante la lettura dell'input: " + e.getMessage());
        }
        
        ArrayList<Vigenere> quadranti = new ArrayList<Vigenere>(); 
        //creo la matrice che mi servirà a cifrare la password
        Matrice matrix=new Matrice("TPSIT");
    
        Vigenere quadrante_1=new Vigenere(0,12,0,12,matrix);
        quadranti.add(quadrante_1);
    
        Vigenere quadrante_2=new Vigenere(0,12,12,26,matrix);
        quadranti.add(quadrante_2);
    
        Vigenere quadrante_3=new Vigenere(12,26,0,12,matrix);
        quadranti.add(quadrante_3);
    
        Vigenere quadrante_4=new Vigenere(12,26,12,26,matrix);
        quadranti.add(quadrante_4);

        //cifro la password
        for(Vigenere v:quadranti){
           Thread t= new Thread(v);
           t.start();
           try {
             t.join();
           }catch (InterruptedException ex) {
               System.err.println("Errore metodo join");
           }
        }  
        //salvo in una variabile la password cifrata
        String passwordCifrata = matrix.cifra(password);
        
        //3) SCRITTURA
        //richiamo il metodo scrittore per scrivere in un file output l'username e la password cifrata
        Scrittore scrittore = new Scrittore("output.csv", username + ";" + passwordCifrata);
        Thread threadScrittore = new Thread(scrittore);
        threadScrittore.start();
        try {
            threadScrittore.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestioneFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //4) COPIATURA
        //richiamo il metodo copiatore per copiare il contenuto del file in un altro 
        Copiatore copiatore = new Copiatore("output.csv", "copia.csv");
        copiatore.start();
        try {
            copiatore.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestioneFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
