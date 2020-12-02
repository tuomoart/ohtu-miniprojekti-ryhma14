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
public class RealCli implements Cli {
    private Scanner scanner = new Scanner(System.in);
    private Logic logic = new Logic();
    
    @Override
    public void start() {
        System.out.println("");
        System.out.println("Tervetuloa lukuvinkkikirjastoon!");
        while (true) {
            String input = readCommand();
            if (input.equals("-1")) {
                //CASE -1: stop the program
                break;
            } else if (input.equals("1")) { 
                //CASE 1: "Lisää uusi lukuvinkki"
                //read user input and add tip
                int typeOfTip = readTypeOfTip();
                addTip(typeOfTip);
            } else if (input.equals("2")) {
                //CASE 2: "Listaa kaikki lukuvinkit"
                printBooks();
            } else {
                System.out.println("Tuntematon komento.");
                System.out.println("");
            }
        }
    }

    @Override
    public String readCommand() {
        printCommands();
        String input = scanner.nextLine();
        System.out.println("");
        return input;
    }

    public void printCommands() {
        System.out.println("\n"+"Toiminnot:"+"\n");
        System.out.println(" 1 - lisää uusi lukuvinkki");
        System.out.println(" 2 - listaa kaikki lukuvinkit");
        System.out.println("-1 - poistu"+"\n");
        System.out.println("Valitse komento (1,2,-1):");
    }

    @Override
    public int readTypeOfTip() {
        printTypesOfTip();
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Syötä kokonaisluku:");
            }
        }
    }

    public void printTypesOfTip() {
        System.out.println("Valitse lisättävä vinkkityyppi (1-?):");
        System.out.println("1 - Kirja"+"\n");
    }
    
    @Override
    public void addTip(int typeOfTip) {
        if (typeOfTip == 1) {
            // BOOK
            addBook();
        }  
    }
    
    @Override
    public void addBook() {
        System.out.println("\n"+"Anna kirjan tiedot:"+"\n");

        System.out.println("Anna nimeke:");
        String nameOfBook = scanner.nextLine();
        
        System.out.println("Jos jokin seuraavista ominaisuuksista ei ole tiedossa, syötä tyhjä merkkijono\n");
        System.out.println("Anna kirjailija:");
        String author = scanner.nextLine();

        System.out.println("Anna kirjan julkaisuvuosi:");
        String year = scanner.nextLine();
            
        System.out.println("Anna kirjan sivumäärä:");
        String pages = scanner.nextLine();
            
        System.out.println("Anna ISBN-tunniste:");
        String isbn = scanner.nextLine();
        
        //add book
        List <String> messages = logic.addBook(nameOfBook, author, year, pages, isbn);
        
        for (String message : messages) {
            System.out.println("\n" + message);
        }
    }
    
    @Override
    public void printBooks() {
        List<Book> books = logic.getBooks();
        for (Book book : books) {
            System.out.println(book);
            System.out.println("");
        }
    }
    
}
