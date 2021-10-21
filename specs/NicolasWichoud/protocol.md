Protocol objectives: what does the protocol do?
Le client envoie un calcul (addition, soustracion, multiplication ou division) au serveur qui lui répond avec la solution de ce calcul.

What transport protocol do we use?
On utilise le protocole TCP.

How does the client find the server (addresses and ports)?
Le client se connecte sur le port 3101 à l'adresse IP du serveur.

Who speaks first?
Le client parle en premier pour indiquer au serveur qu'il a un calcul à faire.

Who closes the connection and when ?
C'est le client qui ferme la connexion quand il n'a plus de calculs à faire ou le serveur si le client est inactif pendant une certaine période.

What is the syntax of the messages?
<opérande1> <opérateur> <opérande2>

What is the sequence of messages exchanged by the client and the server? (flow)
Client : Hello
Serveur : Welcome
Client : 1 + 2
Serveur : = 3
Client : 3 * 4
Serveur : = 12
Client : quit
Serveur : Goodbye

Supported operations
Addition, soustraction, multiplication et division

Error handling
En cas de mauvaise syntaxe on lève une erreur 400, en cas d'opérateur inconnu on lève une erreur 300.

Extensibility
On pourrait ajouter d'autres opérateurs comme puissance ou modulo par exemple.

