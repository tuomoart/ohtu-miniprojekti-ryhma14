/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.cli;

import java.util.List;
import java.util.Scanner;
import library.domain.*;
/**
 *
 * @author hiira
 */
public class Cli {
    private Scanner scanner = new Scanner(System.in);
    private Logic logic = new Logic();
    
    public void start() {
        System.out.println("");
        System.out.println("Tervetuloa lukuvinkkikirjastoon!");
        
        while (true) {
            System.out.println("");
            System.out.println("Toiminnot:");            
            System.out.println("");
            System.out.println("1 - lisää uusi lukuvinkki");
            System.out.println("2 - listaa kaikki lukuvinkit");
            System.out.println("-1 - poistu");
            System.out.println("");
            
            System.out.println("Valitse komento (1,2,-1): ");
            
            // read user input
            String input = scanner.nextLine();
            System.out.println("");
            
            if (Integer.valueOf(input) == -1) {
                //CASE -1: stop the program
                break;
                
            } else if (Integer.valueOf(input) == 1) { 
                //CASE 1: "Lisää uusi lukuvinkki"
                System.out.println("Valitse lisättävä vinkkityyppi (1-?):");
                System.out.println("1 - Kirja");
                System.out.println("");
                
                //read user input and add tip
                int typeOfTip = Integer.valueOf(scanner.nextLine());
                addTip(typeOfTip);
                
            } else if (Integer.valueOf(input) == 2) {
                //CASE 2: "Listaa kaikki lukuvinkit"
                printBooks();
                
            } else {
                System.out.println("Tuntematon komento.");
                System.out.println("");
            }
        }
        
    }
    
    public void addTip(int typeOfTip) {
        if (typeOfTip == 1) {
            // BOOK
            addBook();
        }  
    }
    
    public void addBook() {
        System.out.println("");
            System.out.println("Anna kirjan tiedot (jos jokin ominaisuus ei ole tiedossa, paina enteriä sen kodalla):");
            System.out.println("");
            
            System.out.println("Anna nimeke: ");
            String nameOfBook = scanner.nextLine();
            
            System.out.println("Anna kirjailija: ");
            String author = scanner.nextLine();
            
            System.out.println("Anna kirjan julkaisuvuosi: ");
            String year = scanner.nextLine();
            
            System.out.println("Anna kirjan sivumäärä: ");
            String pages = scanner.nextLine();
            
            System.out.println("Anna ISBN-tunniste: ");
            String isbn = scanner.nextLine();
            
            
            //add book
            String message = logic.addBook(nameOfBook, author, year, pages, isbn);
            
            System.out.println("\n" + message);
    }
    
    public void printBooks() {
        List<Book> books = logic.getBooks();
        
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
}
