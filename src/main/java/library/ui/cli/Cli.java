package library.ui.cli;

public interface Cli {
    void start();

    String readCommand();

    int readTypeOfTip();

    void addTip(int typeOfTip);

    void addBook();

    void printBooks();
}
