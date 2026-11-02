# TP8 – Interfaces et Polymorphisme en Java

## 1. Présentation du projet

Ce TP a pour objectif de mettre en pratique les concepts fondamentaux de la programmation orientée objet (POO) en Java, en particulier les interfaces, le polymorphisme, l'implémentation multiple et l'injection de dépendance.

Le projet consiste à développer deux petits systèmes indépendants :

- **Un système de gestion des paiements** : permet de simuler plusieurs moyens de paiement, notamment la carte bancaire et le virement bancaire.
- **Un système de gestion des notifications** : permet de simuler l'envoi de messages à travers différents canaux, notamment l'email et le SMS.

L'objectif principal est de montrer comment une même interface peut définir un contrat commun pour plusieurs classes ayant des comportements différents.

**Nom du projet :** `tp8_interfaces`  
**Langage :** Java  
**IDE utilisé :** Eclipse  
**Organisation :** Programmation orientée objet

---

## 2. Objectifs pédagogiques

À travers ce TP, nous cherchons à :

- Comprendre le rôle et l'utilité des interfaces en Java.
- Déclarer une interface à l'aide du mot-clé `interface`.
- Implémenter une interface avec le mot-clé `implements`.
- Redéfinir les méthodes d'une interface avec `@Override`.
- Comprendre le polymorphisme et ses avantages.
- Utiliser une référence de type interface pour manipuler différents objets.
- Appliquer le principe d'injection de dépendance par constructeur.
- Séparer les responsabilités entre les différentes classes.
- Concevoir un programme flexible, modulaire et extensible.
- Comprendre la différence entre une interface et une classe concrète.

---

## 3. Architecture du projet

Le projet est organisé en trois packages afin de séparer les différentes responsabilités.

```text
tp8_interfaces/
│
└── src/
    │
    ├── com.tp8/
    │   └── Main.java
    │
    ├── com.tp8.payment/
    │   ├── PaymentProcessor.java
    │   ├── CreditCardProcessor.java
    │   ├── BankTransferProcessor.java
    │   └── PaymentService.java
    │
    └── com.tp8.notification/
        ├── NotificationSender.java
        ├── EmailNotificationSender.java
        ├── SmsNotificationSender.java
        └── NotificationService.java
```

### Description des packages

**Package `com.tp8`**

Contient la classe principale `Main.java`, qui permet de lancer et de tester l'ensemble de l'application.

**Package `com.tp8.payment`**

Contient toutes les classes relatives à la gestion des paiements.

**Package `com.tp8.notification`**

Contient les interfaces et les classes responsables de la gestion des notifications.

---

## 4. Description détaillée des étapes

### Étape 1 : Création de l'interface PaymentProcessor

L'interface `PaymentProcessor` définit un contrat commun pour tous les moyens de paiement.

Elle contient deux méthodes abstraites :

- `processPayment(double amount)` : traite une transaction et retourne un booléen indiquant si le paiement a réussi.
- `getPaymentMethod()` : retourne le nom du moyen de paiement utilisé.

Cette interface permet d'utiliser différents moyens de paiement sans modifier le service principal.

### Étape 2 : Implémentation du paiement par carte bancaire

La classe `CreditCardProcessor` implémente l'interface `PaymentProcessor`.

Elle contient :

- Un attribut privé `cardNumber` représentant le numéro de carte.
- Un constructeur permettant d'initialiser le numéro de carte.
- Une méthode `processPayment()` qui vérifie que le montant est positif.
- Une méthode `getPaymentMethod()` qui retourne « Carte Bancaire ».

Si le montant est invalide, le paiement est refusé. Sinon, le paiement est simulé comme accepté.

### Étape 3 : Implémentation du paiement par virement bancaire

La classe `BankTransferProcessor` représente un deuxième moyen de paiement.

Elle contient :

- Un attribut `accountNumber` représentant le numéro de compte bancaire.
- Un constructeur pour initialiser le compte.
- Une méthode `processPayment()` qui vérifie le montant.
- Une simulation d'un délai de traitement avec `Thread.sleep(1000)`.
- Une gestion de l'exception `InterruptedException`.
- Une méthode `getPaymentMethod()` qui retourne « Virement Bancaire ».

Cette étape montre que plusieurs classes peuvent implémenter une même interface tout en ayant des comportements différents.

### Étape 4 : Création du service de paiement

La classe `PaymentService` permet de gérer les transactions.

Elle possède :

- Un attribut `processor` de type `PaymentProcessor`.
- Un constructeur qui reçoit un processeur de paiement.
- Une méthode `pay(double amount)` qui lance le traitement du paiement et affiche son résultat.

**Concept important : Injection de dépendance**

Le service dépend de l'interface `PaymentProcessor` et non d'une classe concrète. Il peut donc fonctionner avec une carte bancaire ou un virement sans modification de son code.

### Étape 5 : Test du système de paiement

La classe `Main` permet de tester les deux implémentations.

Les opérations réalisées sont :

1. Création d'un objet `CreditCardProcessor`.
2. Création d'un objet `PaymentService` avec le processeur de carte.
3. Exécution d'un paiement de 50 euros.
4. Création d'un objet `BankTransferProcessor`.
5. Création d'un autre `PaymentService` avec le processeur de virement.
6. Exécution d'un paiement de 150 euros.

Les résultats permettent de vérifier le fonctionnement du polymorphisme.

### Étape 6 : Création de l'interface NotificationSender

L'interface `NotificationSender` définit un contrat commun pour les différents canaux de communication.

Elle contient deux méthodes :

- `sendNotification(String recipient, String message)` : permet d'envoyer une notification à un destinataire.
- `getChannelType()` : retourne le nom du canal utilisé.

Elle joue le même rôle que `PaymentProcessor`, mais dans le domaine des notifications.

### Étape 7 : Implémentation de la notification par email

La classe `EmailNotificationSender` implémente l'interface `NotificationSender`.

Elle permet de simuler l'envoi d'un email.

Fonctionnalités :

- Vérification de la présence du caractère `@` dans l'adresse email.
- Affichage d'un message d'erreur si l'adresse est invalide.
- Affichage du message envoyé si l'adresse est acceptée.
- Retourne « Email » comme type de canal.

### Étape 8 : Implémentation de la notification par SMS

La classe `SmsNotificationSender` représente l'envoi de notifications par SMS.

Elle utilise une constante :

`MAX_SMS_LENGTH = 160`

Fonctionnalités :

- Vérification de la longueur du message.
- Troncature du message à 160 caractères s'il est trop long.
- Affichage du destinataire et du contenu du SMS.
- Retourne « SMS » comme type de canal.

La logique de validation est indépendante de celle de l'email.

### Étape 9 : Création du service de notification

La classe `NotificationService` permet de gérer l'envoi des notifications.

Elle contient :

- Un attribut `sender` de type `NotificationSender`.
- Un constructeur qui reçoit un objet implémentant cette interface.
- Une méthode `sendWelcomeMessage(String recipient)` qui prépare et envoie un message de bienvenue.

Grâce à l'injection de dépendance, le service peut utiliser un email ou un SMS sans changer son implémentation.

### Étape 10 : Intégration et test final

La classe `Main.java` regroupe les tests des deux systèmes.

Les tests réalisés sont :

**Système de paiement :**
- Paiement par carte bancaire.
- Paiement par virement bancaire.

**Système de notification :**
- Envoi d'un message par email.
- Envoi d'un message par SMS.

Cette dernière étape permet de vérifier que les interfaces fonctionnent correctement et que les services peuvent utiliser différentes implémentations.

---

## 5. Résultats attendus

Après l'exécution de `Main.java`, la console affiche les résultats des différentes opérations.

### Exemple de sortie

```text
--- Traitement du paiement via : Carte Bancaire ---
Paiement de 50.0€ par carte 4111-1111-1111-1111 accepté.
Transaction terminée avec succès.

========================================

--- Traitement du paiement via : Virement Bancaire ---
Virement de 150.0€ vers le compte FR76-3000-6000-0112-3456 initié.
Virement confirmé.
Transaction terminée avec succès.

========================================

TESTS NOTIFICATIONS

Envoi via canal : Email
[EMAIL] Envoi du message à user@example.com :
Bienvenue ! Votre compte a été créé avec succès.

Envoi via canal : SMS
[SMS] Envoi au +33612345678 :
Bienvenue ! Votre compte a été créé avec succès.
```

**Remarque :** les paiements et les notifications sont simulés. Aucune véritable transaction bancaire ou communication réseau n'est effectuée.

---

## 6. Concepts fondamentaux étudiés

| Concept | Description |
|---|---|
| Interface | Définit un contrat que les classes doivent respecter. |
| `implements` | Permet à une classe d'implémenter une interface. |
| `@Override` | Indique la redéfinition d'une méthode. |
| Polymorphisme | Permet de manipuler différents objets à travers une même interface. |
| Encapsulation | Protège les attributs grâce à la visibilité `private`. |
| Constructeur | Initialise les objets lors de leur création. |
| `final` | Empêche la réaffectation d'un attribut après son initialisation. |
| Injection de dépendance | Fournit à une classe l'objet dont elle a besoin. |
| `try/catch` | Permet de gérer les exceptions. |
| `static final` | Déclare une constante de classe. |

---

## 7. Avantages de l'utilisation des interfaces

L'utilisation des interfaces présente plusieurs avantages :

- **Flexibilité :** possibilité de changer d'implémentation sans modifier les services.
- **Réutilisabilité :** plusieurs classes peuvent respecter le même contrat.
- **Modularité :** chaque classe possède une responsabilité précise.
- **Maintenabilité :** le code est plus facile à modifier et à faire évoluer.
- **Extensibilité :** il est possible d'ajouter de nouveaux moyens de paiement ou canaux de notification.

Par exemple, il serait possible d'ajouter une classe `PayPalProcessor` qui implémente `PaymentProcessor` sans modifier la classe `PaymentService`.

De même, un nouveau canal `WhatsAppNotificationSender` pourrait être ajouté en implémentant `NotificationSender`.

---

## 8. Instructions d'exécution

### Prérequis

- Java Development Kit (JDK).
- Eclipse IDE.
- Un projet Java configuré dans Eclipse.

### Étapes d'exécution

1. Ouvrir Eclipse.
2. Importer ou ouvrir le projet `tp8_interfaces`.
3. Vérifier la présence des trois packages.
4. Vérifier que tous les fichiers sont correctement enregistrés.
5. Ouvrir le fichier `Main.java`.
6. Faire un clic droit sur le fichier.
7. Sélectionner **Run As → Java Application**.
8. Consulter les résultats dans la fenêtre Console.

---

## 9. Conclusion générale

Ce TP a permis de mettre en pratique les interfaces en Java et de comprendre leur importance dans la programmation orientée objet.

À travers les deux exemples de gestion des paiements et des notifications, nous avons appris à définir des contrats communs, à créer plusieurs implémentations concrètes et à utiliser le polymorphisme.

Nous avons également appliqué l'injection de dépendance par constructeur afin de réduire le couplage entre les classes.

En conclusion, les interfaces permettent de concevoir des applications plus flexibles, modulaires, évolutives et faciles à maintenir.

---

## 10. Informations sur le TP

| Élément | Information |
|---|---|
| TP | TP8 |
| Sujet | Interfaces en Java |
| Projet | `tp8_interfaces` |
| Langage | Java |
| IDE | Eclipse |
| Paradigme | Programmation orientée objet |
| Nombre d'interfaces | 2 |
| Nombre de classes concrètes et de services | 6 |
| Classe principale | `Main.java` |

**Fin du TP8 – Interfaces et Polymorphisme en Java.**
<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/e479f176-3843-4168-a8fe-8bd9e978f172" />
