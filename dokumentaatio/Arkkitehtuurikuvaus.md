Ehdotamme arkkitehtuurityyliksi kerrosarkkitehtuuria, sillä se lienee useimmille tuttu.

Pakkauksia voisi ohjelmassa olla
 - "ui", eli käyttöliittymäkoodit
   - "cli", komentorivikäyttöliittymälle alipakkaus, jos tarvitsee useampia luokkia
 - "domain", eli toimintalogiikka
 - "dao", eli tallennuksen koodi

seuraavasti:

![pakkauskaavio](https://raw.githubusercontent.com/tuomoart/ohtu-miniprojekti-ryhma14/main/dokumentaatio/misc/pakkauskaavio-sprintti1.jpg)

### ui - Käyttöliittymä

Alkuun riittänee yksi luokka, joka sisältää simppelin komentoriviohjelman koodin. Toiminnallisuuden toteuttamiseen käyttää domain.Lukuvinkkikirjasto -luokkaa.

### domain - Logiikka

Luokka Lukuvinkkikirjasto, joka on päävastuussa toimintalogiikan tuottamisesta. Logiikkaa voi jaotella tarpeen mukaan. Kaveriksi tarvitsee ainakin luokan, joka kuvaa kirjaa, esim Kirja. Tulevaisuudessa voi ja varmaan kannattaa tehdä yläluokka "Vinkki", jonka eri vinkkityypit sitten perivät ja mahdollisesti lisäävät yksilöllisiä ominaisuuksiaan.

### dao - Tallennus

Kannattanee tehdä dao-tyylin mukaisesti rajapinta vinkkien tallennukseen. Tämä tarvinnee tässä vaiheessa metodin lisaa(Kirja) tai lisaa(Vinkki), jonka avulla uusi vinkki lisätään tietokantaan.

Jos tehdään pysyväistallennus tietokantaan, niin ehdotamme SQLiteä, sillä tämä on entuudestaan tuttu. Siihen pitää rajapinnan toteuttamisen lisäksi luoda ainakin toiminnot taulujen luontiin ja varmaan myös poistamiseen.
