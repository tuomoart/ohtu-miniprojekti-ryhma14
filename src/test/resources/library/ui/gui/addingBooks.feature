Feature: As a user I want to be able to add a book using gui

    Scenario: creation of book in gui is succesful with valid name
        Given command new book is selected in gui
        When name "Yksin Marsissa" is entered in gui
        Then gui will respond with "Kirja 'Yksin Marsissa' lisätty"