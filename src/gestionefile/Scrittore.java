package gestionefile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian Ramacci
 */

public class Scrittore implements Runnable{

    String nomeFile;
    String messaggio;
    
    public Scrittore(String nomeFile, String messaggio){
        this.nomeFile = nomeFile;
        this.messaggio = messaggio;
    }
    
    @Override
    public void run() {
        scrivi();
    }
    /**
     * Scrive un file di testo usando la classe BufferedWriter
     */
    public void scrivi(){
        
        try(BufferedWriter br=new BufferedWriter(new FileWriter(nomeFile))) {
            //2) scrivo nel buffer
            br.write(messaggio);
            br.write("\n\r");
            //3) svuoto il buffer e salvo nel file i dati
            br.flush();         
        } catch (IOException ex) {
            Logger.getLogger(Scrittore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
