Feature: As a user I want to be able to add a book using Command Line Interface

    Scenario: creation of book tip is successful with valid name
        Given command new book is selected
        When  name "Sapiens" is entered
        Then  Cli will respond with "Kirja lisätty"

    Scenario: creation of book tip is successful with valid name and author
        Given command new book is selected
        When  name "Sivullinen" and author "Albert Camus" are entered
        Then  Cli will respond with "Kirja lisätty"

    Scenario: creation of book tip is unsuccessful with valid name and invalid author
        Given command new book is selected
        When  name "Rikos ja Rangaistus" and author "D0stoj3vsk11111" are entered
        Then  Cli will respond with "Kirjailijan nimi ei saa sisältää numeroita"

    Scenario: creation of book tip is successful with valid name and valid year
        Given command new book is selected
        When  name "Idiootti" and year "1869" are entered
        Then  Cli will respond with "Kirja lisätty"

    Scenario: creation of book tip is unsuccessful with valid name and invalid year
        Given command new book is selected
        When  name "Varkaiden kaupunki" and year "2oo8" are entered
        Then  Cli will respond with "Vääränmallinen vuosiluku"

    Scenario: creation of book tip is successful with valid name and valid pages
        Given command new book is selected
        When  name "Kun maailma järkkyi" and pages "1168" are entered
        Then  Cli will respond with "Kirja lisätty"

    Scenario: creation of book tip is unsuccessful with valid name and invalid pages
        Given command new book is selected
        When  name "Fountainhead" and pages "liikaa" are entered
        Then  Cli will respond with "Vääränmallinen sivumäärä"

    Scenario: creation of book tip is successful with valid name and valid isbn-10
        Given command new book is selected
        When  name "Kommunistinen manifesti" and isbn "071-78024-1-8" are entered
        Then  Cli will respond with "Kirja lisätty"

    Scenario: creation of book tip is successful with valid name and valid isbn-13
        Given command new book is selected
        When  name "Kansojen varallisuus" and isbn "978-160-45989-1-9" are entered
        Then  Cli will respond with "Kirja lisätty"

    Scenario: creation of book tip is unsuccessful with valid name and invalid isbn
        Given command new book is selected
        When  name "1984" and isbn "mikä on isbn?" are entered
        Then  Cli will respond with "Vääränmallinen ISBN"