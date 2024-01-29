/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionefile;

import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Cristian Ramacci
 */
public class Copiatore extends Thread {
    String nomeFileDaCopiare;
    String nomeFileFinale;
   
    
    public Copiatore(String nomeFileDaCopiare,String nomeFileFinale){
         this.nomeFileDaCopiare=nomeFileDaCopiare;
       this.nomeFileFinale=nomeFileFinale;
    }
    
    public String leggi(){
        StringBuilder sb= new StringBuilder();
        int i; 
         //1) apro il file
        try(FileReader  fr = new FileReader(nomeFileDaCopiare)) { 
            //2) leggo carattere per carattere e lo salvo sullo StringBuilder 
            while ((i=fr.read()) != -1)
               sb.append((char) i);
            System.out.print("\n\r");
            //3) chiudo il file
            fr.close();
        } catch (IOException ex) {
            System.err.println("Errore in lettura!");
        }
        return sb.toString();
    }
    
    public void copia(){
        //2)Scrivo il contenuto del file copiato
        Scrittore scrittore = new Scrittore(nomeFileFinale,this.leggi());
        Thread threadScrittore = new Thread(scrittore);
        threadScrittore.start();
         try {
            threadScrittore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore nel metodo join()");
        } 
    }
    

    @Override
    public void run(){
        copia();
    }
}
