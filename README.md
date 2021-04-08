# MyHappyPlants

### Produktbeskrivning
My Happy Plants är en applikation tänkt att hjälpa en användare att ta hand om sina växter i hemmet samt ge användaren information om växterna. My Happy Plants använder sig av API:et Trefle.io, som är ett öppet och gratis API som erbjuder information om en miljon växtarter och hybrider. Applikationen omfattar ett färgglatt grafiskt användargränssnitt utvecklat i JavaFX med bilder av animerade växter, och ger möjlighet för användaren att söka bland en miljon växter, döpa dem och lägga till dem i sitt personliga bibliotek. Användaren kan även välja att lägga till växter i sin önskelista.
Applikationen påminner även användaren när det är tid att vattna eller ge växten näring, enligt appens beräkning, samt föreslår passande växter att införskaffa beroende på användarens önskemål.

### Main Branch - Instruktioner
1. Se till att alla maven dependencies har laddats in
2. Kör main-metoden i klassen PlantService, då får man en output med sökordet i slutet av URLen i getResult(länk) anropet.

### GUI Branch (test branch, ej kopplat till API resultat än så länge)
1. Se till att det finns JavaFX runtime-dependencies installerat (enklast att byta JDK till azul15)
2. Kör GUIApp (själva klassen kan köras direkt utan main-metod) och leka runt lite
3. OBS Resources kan behöva sättas som projekt-mapp(MyHappyPlants)
