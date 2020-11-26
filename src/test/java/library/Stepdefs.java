package library;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import library.domain.Book;
import library.domain.Logic;
import library.ui.cli.Cli;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Stepdefs {
    StubCli cli;
    List<String> inputs;

    public class StubCli implements Cli {
        private List<String> inputs;
        private List<String> prints;
        private Logic logic = new Logic();

        public StubCli(List<String> inputs) {
            this.inputs = inputs;
            prints = new ArrayList<>();
        }

        @Override
        public void start() {
            while (!inputs.isEmpty()) {
                String input = readCommand();
                if (input.equals("-1")) {
                    break;
                } else if (input.equals("1")) {
                    int typeOfTip = readTypeOfTip();
                    addTip(typeOfTip);
                } else if (input.equals("2")) {
                    printBooks();
                } else {}
            }
        }

        public void print(String print) {
            prints.add(print);
        }

        public List getPrints() {
            return prints;
        }

        @Override
        public String readCommand() {
            String input = inputs.get(0);
            inputs.remove(0);
            return input;
        }

        @Override
        public int readTypeOfTip() {
            return Integer.parseInt(readCommand());
        }

        @Override
        public void addTip(int typeOfTip) {
            if (typeOfTip == 1) {
                addBook();
            }
        }

        @Override
        public void addBook() {
            String nameOfBook = readCommand();
            String author = readCommand();
            String year = readCommand();
            String pages = readCommand();
            String isbn = readCommand();
            String message = logic.addBook(nameOfBook, author, year, pages, isbn);
            print(message);
        }

        @Override
        public void printBooks() {
            List<Book> books = logic.getBooks();
            for (Book book : books) {
                print(book.toString());
            }
        }
    }

    @Before
    public void setup() {
        inputs = new ArrayList<>();
    }

    @Given("^command new book is selected$")
    public void commandNewBook() {
        inputs.add("1");
        inputs.add("1");
    }

    @When("name {string} is entered")
    public void enterName(String name) {
        inputs.add(name);
        inputs.add("");
        inputs.add("");
        inputs.add("");
        inputs.add("");
        cli = new StubCli(inputs);
        cli.start();
    }

    @When("name {string} and author {string} are entered")
    public void enterNameAndAuthor(String name, String author) {
        inputs.add(name);
        inputs.add(author);
        inputs.add("");
        inputs.add("");
        inputs.add("");
        cli = new StubCli(inputs);
        cli.start();
    }

    @When("name {string} and year {string} are entered")
    public void enterNameAndYear(String name, String year) {
        inputs.add(name);
        inputs.add("");
        inputs.add(year);
        inputs.add("");
        inputs.add("");
        cli = new StubCli(inputs);
        cli.start();
    }

    @When("name {string} and pages {string} are entered")
    public void enterNameAndPages(String name, String pages) {
        inputs.add(name);
        inputs.add("");
        inputs.add("");
        inputs.add(pages);
        inputs.add("");
        cli = new StubCli(inputs);
        cli.start();
    }

    @When("name {string} and isbn {string} are entered")
    public void enterNameAndISBN(String name, String isbn) {
        inputs.add(name);
        inputs.add("");
        inputs.add("");
        inputs.add("");
        inputs.add(isbn);
        cli = new StubCli(inputs);
        cli.start();
    }

    @Then("Cli will respond with {string}")
    public void cliRespondCorrect(String expectedOutput) {
        assertTrue(cli.getPrints().contains(expectedOutput));
    }
}
