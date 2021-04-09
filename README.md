# MyHappyPlants

### Produktbeskrivning
My Happy Plants är en applikation tänkt att hjälpa en användare att ta hand om sina växter i hemmet samt ge användaren information om växterna. My Happy Plants använder sig av API:et Trefle.io, som är ett öppet och gratis API som erbjuder information om en miljon växtarter och hybrider. Applikationen omfattar ett färgglatt grafiskt användargränssnitt utvecklat i JavaFX med bilder av animerade växter, och ger möjlighet för användaren att söka bland en miljon växter, döpa dem och lägga till dem i sitt personliga bibliotek. Användaren kan även välja att lägga till växter i sin önskelista.
Applikationen påminner även användaren när det är tid att vattna eller ge växten näring, enligt appens beräkning, samt föreslår passande växter att införskaffa beroende på användarens önskemål.

### Main Branch - Instruktioner
1. Se till att alla maven dependencies har laddats in
![bild](https://user-images.githubusercontent.com/77005138/114137664-cd6c0d80-990c-11eb-8350-bdc3172e48d7.png)
2. Execute maven goal mvn javafx:compile
3. Execute maven goal mvn javafx:run (startar klient)
4. Kör main metod i se/myhappyplants/server/controller/Main.java (startar server)

