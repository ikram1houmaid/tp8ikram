# TP8 – Les Interfaces en Java

## Présentation
Ce projet a pour objectif de comprendre et de pratiquer les interfaces en Java à travers deux exemples : la gestion des paiements et l'envoi des notifications.

## Objectifs
- Définir et implémenter des interfaces Java.
- Comprendre le polymorphisme.
- Utiliser le mot-clé `implements`.
- Appliquer l'injection de dépendance par constructeur.
- Comprendre le découplage entre les classes.
- Créer des applications extensibles et faciles à maintenir.

## Structure du projet

```text
tp8_interfaces/
│
└── src/
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

## Partie 1 : Gestion des paiements

L'interface `PaymentProcessor` définit deux méthodes :
- `processPayment(double amount)` : traite un paiement.
- `getPaymentMethod()` : retourne le moyen de paiement.

Deux classes implémentent cette interface :

- **CreditCardProcessor** : simule un paiement par carte bancaire.
- **BankTransferProcessor** : simule un virement bancaire avec un délai de traitement.

La classe `PaymentService` utilise l'interface `PaymentProcessor` pour traiter les transactions sans dépendre d'une implémentation particulière.

## Partie 2 : Gestion des notifications

L'interface `NotificationSender` définit deux méthodes :
- `sendNotification(String recipient, String message)` : envoie une notification.
- `getChannelType()` : retourne le type de notification.

Deux classes implémentent cette interface :

- **EmailNotificationSender** : simule l'envoi d'un email avec validation de l'adresse.
- **SmsNotificationSender** : simule l'envoi d'un SMS avec une limite de 160 caractères.

La classe `NotificationService` permet d'envoyer un message de bienvenue en utilisant différents canaux de notification.

## Exécution du projet

1. Ouvrir le projet `tp8_interfaces` dans Eclipse.
2. Vérifier que tous les fichiers Java sont correctement placés dans leurs packages.
3. Ouvrir la classe `Main.java`.
4. Cliquer sur **Run As → Java Application**.
5. Consulter les résultats dans la console Eclipse.

## Résultats attendus

Le programme permet de tester :
- Le paiement par carte bancaire.
- Le paiement par virement bancaire.
- L'envoi d'une notification par email.
- L'envoi d'une notification par SMS.

Les transactions valides sont acceptées et les notifications sont affichées dans la console.

## Concepts Java utilisés

- Interfaces (`interface`)
- Implémentation (`implements`)
- Redéfinition des méthodes (`@Override`)
- Polymorphisme
- Injection de dépendance
- Constructeurs
- Encapsulation
- Gestion des exceptions (`try/catch`)

## Conclusion

Ce TP permet de comprendre l'importance des interfaces dans la programmation orientée objet en Java. Elles permettent de définir des contrats communs, de séparer les responsabilités et de développer des applications modulaires, flexibles et faciles à maintenir.

**Technologie utilisée :** Java

**IDE :** Eclipse

**Nom du projet :** tp8_interfaces
<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/ebe0fe2f-c713-4dd5-9ab9-d016ab3db466" />
