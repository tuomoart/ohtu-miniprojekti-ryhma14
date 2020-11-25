Feature: As a user I want to be able to add a book using Command Line Interface

    Scenario:
        Given Cli is started
        When  book is added
        Then  Cli will respond with "Kirja lisätty"