# Consigne

Protocol objectives: what does the protocol do?

## Overall behavior:

- What transport protocol do we use?
- How does the client find the server (addresses and ports)?
- Who speaks first?
- Who closes the connection and when?

## Messages:

- What is the syntax of the messages?
- What is the sequence of messages exchanged by the client and the server? (flow)
- What happens when a message is received from the other party? (semantics)
- Specific elements (if useful)

## Supported operations

- Error handling
- Extensibility

## Examples: examples of some typical dialogs.


Le protocole calc est la trame qui permet à une calculette serveur de communiquer avec un client.
Le client envoie une opération et le serveur lui envoie le résultat.

Nous allons utiliser tcp.
Le client trouvera le serveur avec un socket sur le port 3101 et l'ip du serveur
le client envoie un message en premier et le serveur répond par un welcome.
Le client termine la connexion lorsqu'il utilise la commande quit ou la calculette peut fermer la connexion si le client est inactif depuis une certaine période.

les messages devront être de la forme:
<operande1> <operateur> <operande2>

flow:
greeting calc
Welcome available operators : { +, - , *, / }
1 + 4
= 5
2 * 3
= 6
2 - 7
= -5
1 / 9
= 0.11
QUIT
GOODBYE !

Si le message n'est pas de la bonne forme, il y aura une erreur de syntaxe ou une erreur d'opération inconnue
