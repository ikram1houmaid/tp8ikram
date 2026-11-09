# TP8 – Interfaces, Polymorphisme et Injection de Dépendances en Java

## 1. Présentation du TP

Ce TP a pour objectif de mettre en pratique les concepts fondamentaux de la programmation orientée objet (POO) en Java, notamment :

- Les interfaces Java.
- L'implémentation des interfaces avec `implements`.
- Le polymorphisme.
- L'injection de dépendances par constructeur.
- Le principe de découplage entre les classes.
- La séparation des responsabilités.

Le projet consiste à développer deux systèmes indépendants :
1. Un système de gestion des paiements avec plusieurs moyens de paiement.
2. Un système de gestion des notifications avec plusieurs canaux de communication.

L'objectif principal est de montrer comment une même interface peut être implémentée par plusieurs classes ayant des comportements différents.

---

## 2. Objectifs pédagogiques

À la fin de ce TP, on doit être capable de :

- Déclarer une interface avec le mot-clé `interface`.
- Implémenter une interface avec le mot-clé `implements`.
- Redéfinir les méthodes d'une interface avec `@Override`.
- Comprendre le fonctionnement du polymorphisme.
- Utiliser une référence de type interface pour manipuler différents objets.
- Appliquer l'injection de dépendances via un constructeur.
- Concevoir un programme flexible, extensible et facile à maintenir.

---

## 3. Structure du projet

Le projet est organisé en trois packages :

```text
tp8_interfaces/
│
├── src/
│   │
│   ├── com/
│   │   └── tp8/
│   │       │
│   │       ├── Main.java
│   │       │
│   │       ├── payment/
│   │       │   ├── PaymentProcessor.java
│   │       │   ├── CreditCardProcessor.java
│   │       │   ├── BankTransferProcessor.java
│   │       │   └── PaymentService.java
│   │       │
│   │       └── notification/
│   │           ├── NotificationSender.java
│   │           ├── EmailNotificationSender.java
│   │           ├── SmsNotificationSender.java
│   │           └── NotificationService.java
│   │
└── README.md
```

---

## 4. Partie 1 : Système de paiement

### Étape 1 : Création de l'interface PaymentProcessor

L'interface `PaymentProcessor` définit un contrat commun pour tous les moyens de paiement.

Elle contient deux méthodes :

- `processPayment(double amount)` : traite un paiement et retourne `true` si la transaction réussit, sinon `false`.
- `getPaymentMethod()` : retourne le nom du moyen de paiement.

Cette interface permet d'utiliser différents moyens de paiement sans connaître leurs détails internes.

### Étape 2 : Paiement par carte bancaire

La classe `CreditCardProcessor` implémente l'interface `PaymentProcessor`.

Elle possède :

- Un attribut `cardNumber` représentant le numéro de la carte.
- Un constructeur pour initialiser le numéro de la carte.
- Une méthode `processPayment()` qui vérifie que le montant est positif.
- Une méthode `getPaymentMethod()` qui retourne « Carte Bancaire ».

Si le montant est invalide, le paiement est refusé. Sinon, un message de confirmation est affiché.

### Étape 3 : Paiement par virement bancaire

La classe `BankTransferProcessor` représente le paiement par virement bancaire.

Elle contient :

- Un attribut `accountNumber` représentant le numéro du compte.
- Un constructeur pour initialiser le numéro du compte.
- Une méthode `processPayment()` qui vérifie le montant avant d'initier le virement.
- Une simulation de délai de traitement avec `Thread.sleep(1000)`.
- Une méthode `getPaymentMethod()` qui retourne « Virement Bancaire ».

La méthode `Thread.sleep(1000)` permet de simuler un délai d'une seconde.

En cas d'interruption, l'exception `InterruptedException` est gérée et le statut d'interruption du thread est restauré.

### Étape 4 : Création du service PaymentService

La classe `PaymentService` représente le gestionnaire des paiements.

Elle utilise une référence de type `PaymentProcessor` au lieu de dépendre directement de `CreditCardProcessor` ou `BankTransferProcessor`.

Son constructeur reçoit le processeur de paiement en paramètre.

La méthode `pay(double amount)` :

1. Affiche le moyen de paiement utilisé.
2. Lance le traitement de la transaction.
3. Vérifie le résultat du paiement.
4. Affiche un message de réussite ou d'échec.
5. Retourne le résultat de la transaction.

**Avantage :** le service peut fonctionner avec n'importe quelle classe qui implémente `PaymentProcessor`.

### Étape 5 : Test du système de paiement

Dans la classe `Main`, deux tests sont effectués :

**Premier test : Paiement par carte bancaire**

- Création d'un objet `CreditCardProcessor`.
- Transmission de cet objet au constructeur de `PaymentService`.
- Exécution d'un paiement de 50 €.

**Deuxième test : Paiement par virement bancaire**

- Création d'un objet `BankTransferProcessor`.
- Transmission de cet objet au constructeur de `PaymentService`.
- Exécution d'un paiement de 150 €.

Les deux tests utilisent le même service, mais avec deux implémentations différentes.

---

## 5. Partie 2 : Système de notification

### Étape 6 : Création de l'interface NotificationSender

L'interface `NotificationSender` définit un contrat commun pour les différents canaux de notification.

Elle contient deux méthodes :

- `sendNotification(String recipient, String message)` : envoie une notification à un destinataire.
- `getChannelType()` : retourne le nom du canal de communication.

Cette interface permet d'ajouter facilement de nouveaux canaux de notification.

### Étape 7 : Notification par email

La classe `EmailNotificationSender` implémente l'interface `NotificationSender`.

Elle permet de simuler l'envoi d'un email.

La méthode `sendNotification()` vérifie que l'adresse contient le caractère `@`.

- Si l'adresse est invalide, un message d'erreur est affiché.
- Si l'adresse est valide, le message est affiché comme envoyé.

La méthode `getChannelType()` retourne « Email ».

### Étape 8 : Notification par SMS

La classe `SmsNotificationSender` représente l'envoi de notifications par SMS.

Elle utilise une constante :

`MAX_SMS_LENGTH = 160`

Cette constante définit la longueur maximale du message SMS.

La méthode `sendNotification()` :

1. Vérifie la longueur du message.
2. Si le message dépasse 160 caractères, il est tronqué avec `substring(0, 160)`.
3. Affiche le message envoyé au destinataire.

La méthode `getChannelType()` retourne « SMS ».

**Remarque :** la validation du SMS est différente de celle de l'email. Chaque classe possède donc sa propre logique métier.

### Étape 9 : Création du service NotificationService

La classe `NotificationService` gère l'envoi des notifications.

Elle dépend uniquement de l'interface `NotificationSender`.

Son constructeur reçoit un objet qui implémente cette interface.

La méthode `sendWelcomeMessage(String recipient)` :

1. Prépare un message de bienvenue.
2. Affiche le canal utilisé.
3. Appelle la méthode `sendNotification()` pour envoyer le message.

Le service peut utiliser aussi bien un système d'envoi par email qu'un système d'envoi par SMS.

### Étape 10 : Intégration et test final

La classe `Main` permet de tester l'ensemble du système.

Les tests réalisés sont :

**Tests des paiements :**
- Paiement de 50 € par carte bancaire.
- Paiement de 150 € par virement bancaire.

**Tests des notifications :**
- Envoi d'un message de bienvenue par email.
- Envoi d'un message de bienvenue par SMS.

Ces tests permettent de vérifier le fonctionnement des interfaces et du polymorphisme dans les deux systèmes.

---

## 6. Concepts Java utilisés

| Concept | Description |
|---|---|
| `interface` | Définit un contrat commun entre plusieurs classes. |
| `implements` | Permet à une classe d'implémenter une interface. |
| `@Override` | Indique qu'une méthode redéfinit une méthode héritée ou déclarée dans une interface. |
| Polymorphisme | Permet d'utiliser plusieurs objets différents à travers une même interface. |
| Injection de dépendances | Consiste à fournir une dépendance à une classe depuis l'extérieur. |
| `private` | Protège l'accès direct aux attributs. |
| `final` | Empêche la modification de la référence après son initialisation. |
| `static final` | Définit une constante de classe. |
| `Thread.sleep()` | Permet de suspendre temporairement l'exécution d'un thread. |

---

## 7. Résultat attendu

Après l'exécution de la classe `Main`, la console affiche un résultat similaire à celui-ci :

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
[EMAIL] Envoi du message à user@example.com : Bienvenue ! Votre compte a été créé avec succès.

Envoi via canal : SMS
[SMS] Envoi au +33612345678 : Bienvenue ! Votre compte a été créé avec succès.
```

*Remarque : le virement bancaire introduit un délai d'une seconde avant sa confirmation.*

---

## 8. Avantages de cette architecture

L'utilisation des interfaces apporte plusieurs avantages :

- **Flexibilité :** possibilité de changer de moyen de paiement ou de canal de notification facilement.
- **Extensibilité :** possibilité d'ajouter de nouvelles implémentations sans modifier les services existants.
- **Réutilisabilité :** les services peuvent fonctionner avec plusieurs implémentations.
- **Maintenabilité :** chaque classe possède une responsabilité précise.
- **Faible couplage :** les services dépendent des interfaces et non des classes concrètes.

Par exemple, on peut ajouter une classe `PayPalProcessor` qui implémente `PaymentProcessor`, sans modifier la classe `PaymentService`.

De même, on peut ajouter une classe `WhatsAppNotificationSender` qui implémente `NotificationSender`, sans modifier la classe `NotificationService`.

---

## 9. Conclusion

Ce TP nous a permis de comprendre l'importance des interfaces dans la programmation orientée objet en Java.

Nous avons développé deux systèmes indépendants : un système de paiement et un système de notification.

Grâce aux interfaces, au polymorphisme et à l'injection de dépendances, nous avons obtenu une architecture flexible, évolutive et faiblement couplée.

**Le principe essentiel retenu est le suivant : programmer en fonction des interfaces plutôt qu'en fonction des implémentations concrètes.**

Ce principe facilite l'évolution des applications Java et améliore la qualité de leur conception.

<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/e479f176-3843-4168-a8fe-8bd9e978f172" />
