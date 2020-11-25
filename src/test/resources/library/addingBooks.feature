Feature: As a user I want to be able to add a book using Command Line Interface

    Scenario: creation of book tip is successful with valid name and author
        Given command new book is selected
        When  name "Sivullinen" and author "Albert Camus" are entered
        Then  Cli will respond with "Kirja lisätty"