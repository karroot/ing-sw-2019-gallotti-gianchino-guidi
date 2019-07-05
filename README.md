# ing-sw-2019-gallotti-gianchino-guidi
Per avviare l'applicazione:
1)Avviare prima il server
2)Poi avviare i client(uno per giocatore)

Impostare Il client:
Appena si avvia il client si apre una finestra dove vengono richieste alcune informazioni
1)Inserire il nome e la frase d'effetto del player

Nota:Se per caso per problemi di rete si ha una disconessione durante la partita bisogna riavviare il client e reinserire lo
stesso nome e frase d'effetto usata per collegarsi all'inzio della partita altrimenti il server rifiuta la connessione
2)Inserire l'indirizzo ipv4 locale del server
3)inserire il numero di porta(Il server apre la porta TCP sulla porta 5000)
4)Selezionare Gui se si vuole l'interfaccia grafica come modalità di visualizazzione(Se non si seleziona si avvia la CLI)
5)Selezionare RMI o Socket per la comunicazione

-Parametri server:
i parametri vanno inseriti da terminale dopo aver scritto il file jar così:

java -jar Server.jar timer terminator numeroTeschi codiceArena

timer : durata del timeout in secondi prima d'iniziare la partita che inizia quando si raggiungono i tre giocatori connessi
terminator : può essere solo "true" o "false" e indica se si vuole il terminator o no
numeroTeschi : indicare il numero di teschi da mettere nel tracciato dell'arena(5-8)
codiceArena : setta che arena usare per la partita (1-4)

Nota:se si sbagliano i valori di alcuni parametri vengono inseriti quelli di default

timer = 10
terminator = false
numeroteschi = 5
codearena = 4

-Parametri client:
Vanno passati da terminale dopo aver scritto il file jar

java -jar Client.jar timerAFK

timerAFK : durate in milisecondi del timer che fa saltare il turno al player nel caso non faccia nessun input
	   in quel tempo inserito

Nota:Sono state implementate le regole complete
