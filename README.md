# TP8 – Interfaces, Polymorphisme et Injection de Dépendances en Java

## 1. Présentation générale

Ce projet a pour objectif de mettre en pratique les concepts de la programmation orientée objet (POO) en Java, particulièrement les interfaces, le polymorphisme et l'injection de dépendances.

Le travail consiste à développer deux systèmes indépendants :

- Un système de gestion des paiements.
- Un système de gestion des notifications.

Chaque système utilise une interface qui définit un contrat commun. Plusieurs classes implémentent ensuite cette interface afin de proposer des comportements différents.

L'objectif est de créer une architecture flexible, extensible et faiblement couplée.

---

## 2. Objectifs pédagogiques

Les objectifs de ce TP sont :

- Comprendre le rôle des interfaces en Java.
- Déclarer une interface avec le mot-clé `interface`.
- Implémenter une interface avec `implements`.
- Utiliser l'annotation `@Override`.
- Comprendre le polymorphisme.
- Manipuler des objets à travers des références de type interface.
- Appliquer l'injection de dépendances par constructeur.
- Séparer les responsabilités entre les différentes classes.
- Créer une architecture permettant d'ajouter facilement de nouvelles fonctionnalités.

---

## 3. Environnement de développement

### Outils nécessaires

- **Langage :** Java.
- **JDK :** version 11 ou supérieure.
- **IDE :** Eclipse, IntelliJ IDEA ou NetBeans.
- **Type de projet :** Java Project.

### Vérification de Java

Pour vérifier l'installation du JDK, exécuter les commandes suivantes dans le terminal :

```bash
java -version
javac -version
```

---

## 4. Architecture du projet

Le projet est organisé en trois packages afin de séparer les différentes responsabilités.

```text
tp8_interfaces/
│
├── src/
│   └── com/
│       └── tp8/
│           │
│           ├── Main.java
│           │
│           ├── payment/
│           │   ├── PaymentProcessor.java
│           │   ├── CreditCardProcessor.java
│           │   ├── BankTransferProcessor.java
│           │   └── PaymentService.java
│           │
│           └── notification/
│               ├── NotificationSender.java
│               ├── EmailNotificationSender.java
│               ├── SmsNotificationSender.java
│               └── NotificationService.java
│
└── README.md
```

### Description des fichiers

| Fichier | Rôle |
|---|---|
| Main.java | Point d'entrée du programme et exécution des tests. |
| PaymentProcessor.java | Interface définissant le contrat des paiements. |
| CreditCardProcessor.java | Implémentation du paiement par carte. |
| BankTransferProcessor.java | Implémentation du paiement par virement. |
| PaymentService.java | Gestionnaire des transactions. |
| NotificationSender.java | Interface définissant le contrat des notifications. |
| EmailNotificationSender.java | Implémentation de l'envoi par email. |
| SmsNotificationSender.java | Implémentation de l'envoi par SMS. |
| NotificationService.java | Gestionnaire des notifications. |

---

# 5. Première partie : Système de paiement

## 5.1. Interface PaymentProcessor

Cette interface définit le contrat commun à tous les moyens de paiement.

Elle contient deux méthodes :

- `processPayment(double amount)` : traite une transaction et retourne un booléen indiquant si elle a réussi.
- `getPaymentMethod()` : retourne le nom du moyen de paiement.

Toutes les classes qui implémentent cette interface doivent obligatoirement fournir ces deux méthodes.

## 5.2. Classe CreditCardProcessor

Cette classe représente le paiement par carte bancaire.

### Attribut

- `cardNumber` : numéro de la carte bancaire.

### Fonctionnement

1. Le constructeur initialise le numéro de carte.
2. La méthode `processPayment()` vérifie le montant.
3. Si le montant est inférieur ou égal à zéro, le paiement est refusé.
4. Sinon, un message de confirmation est affiché.
5. La méthode `getPaymentMethod()` retourne « Carte Bancaire ».

## 5.3. Classe BankTransferProcessor

Cette classe représente le paiement par virement bancaire.

### Attribut

- `accountNumber` : numéro du compte bancaire.

### Fonctionnement

1. Initialisation du numéro de compte.
2. Vérification du montant.
3. Affichage de l'initiation du virement.
4. Simulation d'un délai d'une seconde avec `Thread.sleep(1000)`.
5. Affichage de la confirmation du virement.

En cas d'interruption, le thread restaure son statut d'interruption et la méthode retourne `false`.

## 5.4. Classe PaymentService

Cette classe centralise le traitement des paiements.

Elle possède un attribut de type `PaymentProcessor`.

Le processeur est transmis au constructeur : c'est l'injection de dépendances par constructeur.

La méthode `pay()` :

- Affiche le moyen de paiement.
- Appelle `processPayment()`.
- Vérifie le résultat.
- Affiche un message de réussite ou d'échec.
- Retourne le résultat.

Le service ne dépend donc pas directement d'une classe concrète.

---

# 6. Deuxième partie : Système de notification

## 6.1. Interface NotificationSender

Cette interface définit un contrat commun pour les différents canaux de communication.

Elle contient :

- `sendNotification(String recipient, String message)` : envoie une notification.
- `getChannelType()` : retourne le type de canal utilisé.

## 6.2. Classe EmailNotificationSender

Cette classe permet de simuler l'envoi d'un email.

### Fonctionnement

1. Vérification de l'adresse email.
2. Recherche du caractère `@`.
3. Si l'adresse est invalide, affichage d'un message d'erreur.
4. Sinon, affichage du message envoyé.

La validation est spécifique au canal email.

**Limitation :** la vérification du caractère `@` est une validation simplifiée et ne garantit pas qu'une adresse email soit réellement valide.

## 6.3. Classe SmsNotificationSender

Cette classe simule l'envoi d'un SMS.

Elle utilise une constante :

`MAX_SMS_LENGTH = 160`

### Fonctionnement

1. Réception du destinataire et du message.
2. Vérification de la longueur du message.
3. Si le message dépasse 160 caractères, il est tronqué.
4. Affichage du SMS envoyé.

La méthode `substring(0, 160)` permet de conserver uniquement les 160 premiers caractères.

## 6.4. Classe NotificationService

Cette classe centralise l'envoi des notifications.

Elle dépend uniquement de l'interface `NotificationSender`.

Sa méthode `sendWelcomeMessage()` :

1. Crée un message de bienvenue.
2. Affiche le canal utilisé.
3. Appelle `sendNotification()`.

Le même service peut fonctionner avec un email ou un SMS.

---

# 7. Tests et scénarios de validation

Plusieurs scénarios sont exécutés dans la classe `Main.java`.

## 7.1. Tests du système de paiement

| N° | Scénario | Entrée | Résultat attendu |
|---|---|---|---|
| 1 | Carte valide | 50 € | Paiement accepté. |
| 2 | Carte avec montant négatif | -20 € | Paiement refusé. |
| 3 | Carte avec montant nul | 0 € | Paiement refusé. |
| 4 | Virement valide | 150 € | Virement confirmé après une seconde. |
| 5 | Virement avec montant négatif | -100 € | Virement refusé. |

## 7.2. Tests du système de notification

| N° | Scénario | Entrée | Résultat attendu |
|---|---|---|---|
| 6 | Email valide | user@example.com | Message envoyé. |
| 7 | Email invalide | userexample.com | Message d'erreur. |
| 8 | SMS normal | Message de bienvenue | Message envoyé. |
| 9 | SMS trop long | 200 caractères | Message tronqué à 160 caractères. |
| 10 | Vérification des canaux | Email et SMS | Affichage des types de canaux. |

## 7.3. Résultats des tests

Les tests permettent de vérifier :

- Le bon fonctionnement du paiement par carte.
- Le bon fonctionnement du paiement par virement.
- Le rejet des montants invalides.
- La validation simplifiée des adresses email.
- La limitation de la longueur des SMS.
- Le fonctionnement du polymorphisme.
- L'utilisation d'un même service avec plusieurs implémentations.

**Remarque :** les tests sont exécutés manuellement dans la méthode `main()`. Il ne s'agit pas de tests unitaires automatisés avec JUnit.

---

# 8. Exemple de sortie dans la console

```text
========== TESTS DES PAIEMENTS ==========

TEST 1 : Carte bancaire - montant valide
--- Traitement du paiement via : Carte Bancaire ---
Paiement de 50.0€ par carte 4111-1111-1111-1111 accepté.
Transaction terminée avec succès.

TEST 2 : Carte bancaire - montant négatif
--- Traitement du paiement via : Carte Bancaire ---
Erreur : Montant invalide pour la carte 4111-1111-1111-1111
Transaction échouée.

TEST 3 : Carte bancaire - montant nul
--- Traitement du paiement via : Carte Bancaire ---
Erreur : Montant invalide pour la carte 4111-1111-1111-1111
Transaction échouée.

TEST 4 : Virement bancaire - montant valide
--- Traitement du paiement via : Virement Bancaire ---
Virement de 150.0€ vers le compte FR76-3000-6000-0112-3456 initié.
Virement confirmé.
Transaction terminée avec succès.

TEST 5 : Virement bancaire - montant négatif
--- Traitement du paiement via : Virement Bancaire ---
Erreur : Montant invalide pour le virement.
Transaction échouée.

========== TESTS DES NOTIFICATIONS ==========

TEST 6 : Email valide
Envoi via canal : Email
[EMAIL] Envoi du message à user@example.com : Bienvenue ! Votre compte a été créé avec succès.

TEST 7 : Email invalide
Envoi via canal : Email
[EMAIL] Erreur : Adresse invalide : userexample.com

TEST 8 : SMS normal
Envoi via canal : SMS
[SMS] Envoi au +33612345678 : Bienvenue ! Votre compte a été créé avec succès.

TEST 9 : SMS trop long
[SMS] Message trop long, tronqué à 160 caractères.
[SMS] Envoi au +33612345678 : ...

TEST 10 : Vérification des méthodes
Moyen de paiement : Carte Bancaire
Canal email : Email
Canal SMS : SMS

========== FIN DES TESTS ==========
```

---

# 9. Concepts fondamentaux de la POO appliqués

## 9.1. Abstraction

Les interfaces définissent les méthodes que les classes doivent implémenter, sans imposer leur logique interne.

## 9.2. Polymorphisme

Une variable de type interface peut référencer différents objets.

Exemple :

```java
PaymentProcessor processor;

processor = new CreditCardProcessor("4111-1111-1111-1111");

processor = new BankTransferProcessor("FR76-3000-6000-0112-3456");
```

La même référence peut représenter plusieurs moyens de paiement.

## 9.3. Injection de dépendances

Les services reçoivent leurs dépendances depuis l'extérieur grâce à leurs constructeurs.

Exemple :

```java
PaymentService service = new PaymentService(
    new CreditCardProcessor("4111-1111-1111-1111")
);
```

## 9.4. Faible couplage

Les services dépendent des interfaces et non des classes concrètes.

Cela permet de remplacer une implémentation sans modifier le fonctionnement interne du service.

---

# 10. Extensions possibles

Le projet peut être amélioré avec de nouvelles fonctionnalités :

### Pour le paiement

- Ajouter une classe `PayPalProcessor`.
- Ajouter un paiement par portefeuille électronique.
- Ajouter une validation plus complète des informations bancaires.
- Ajouter un historique des transactions.

### Pour les notifications

- Ajouter une classe `WhatsAppNotificationSender`.
- Ajouter une validation plus complète des numéros de téléphone.
- Ajouter un système de notifications personnalisées.
- Ajouter une gestion des erreurs d'envoi.

Ces extensions peuvent être réalisées sans modifier les interfaces ni les services existants, à condition de respecter les contrats définis.

---

# 11. Conclusion générale

Ce TP nous a permis de comprendre et de mettre en pratique les interfaces en Java.

Nous avons développé deux systèmes : un système de paiement et un système de notification.

Chaque système utilise une interface commune permettant à plusieurs classes d'implémenter des comportements différents.

L'utilisation du polymorphisme et de l'injection de dépendances permet de réduire le couplage entre les classes et de rendre le programme plus flexible.

**La notion essentielle à retenir est : programmer en fonction des interfaces et non des implémentations concrètes.**

Cette approche constitue une bonne pratique de conception en programmation orientée objet et facilite la maintenance et l'évolution des applications Java.

<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/e479f176-3843-4168-a8fe-8bd9e978f172" />
