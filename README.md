
# **JEE Gestion - Application Web pour la Gestion de Scolarité**

## **Description**
`JEE Gestion` est une application web conçue pour simplifier la gestion des informations académiques dans un établissement scolaire. Elle offre une interface utilisateur adaptée aux administrateurs, enseignants, et étudiants pour gérer les cours, inscriptions, notes, et plus encore.

---

## **Objectifs**
- Centraliser les données académiques.
- Fournir une interface personnalisée en fonction des rôles utilisateurs.
- Assurer la sécurité des données via un système d'authentification.
- Implémenter une architecture modulaire respectant le modèle MVC.
- Suivre les performances des étudiants grâce à des fonctionnalités comme la saisie de notes et le calcul des moyennes.

---

## **Fonctionnalités**
### **Administrateur**
- Gestion des étudiants, enseignants, cours, et inscriptions.
- Création, modification et suppression des utilisateurs et des entités académiques.
- Consultation des statistiques et rapports académiques.

### **Enseignant**
- Attribution et gestion des cours.
- Saisie et modification des notes des étudiants.
- Consultation des cours et des étudiants inscrits.

### **Étudiant**
- Consultation des cours inscrits.
- Accès aux notes et résultats académiques.

---

## **Technologies Utilisées**
### **Langages et Frameworks**
- **Backend :**
  - Java avec Spring Boot
  - Hibernate (ORM)
- **Frontend :**
  - JSP (Java Server Pages)
  - HTML, CSS

### **Base de Données**
- MySQL

### **Serveur d'Application**
- Apache Tomcat

### **Outils**
- Maven pour la gestion des dépendances
- Eclipse IDE pour le développement

---

## **Architecture**
Le projet suit le modèle **MVC** :
- **Modèle** : Entités Java gérées avec JPA/Hibernate.
- **Vue** : Pages JSP pour l'interface utilisateur.
- **Contrôleur** : Servlets et contrôleurs Spring Boot pour gérer les requêtes.

---

## **Schéma Relationnel**
Les principales entités et leurs relations :
- **Cours** : Identifiant, date, enseignant, matière, inscriptions.
- **Étudiants** : Identifiant, nom, prénom, date de naissance, inscriptions, notes.
- **Enseignants** : Identifiant, nom, matières, cours.
- **Inscriptions** : Liaison entre étudiants et cours.
- **Notes** : Notes attribuées aux étudiants pour chaque matière dans un cours.
- **Utilisateurs** : Authentification avec rôles (Administrateur, Enseignant, Étudiant).

---

## **Installation**
### **Prérequis**
- **Java** 17+
- **Maven**
- **MySQL**

### **Étapes**
1. Télécharger le projet pour l'ouvrir dans Eclipse ou clonez le dépôt :
   ```bash
   git clone https://github.com/nollandL/JEE_gestion.git
   ```
2. Configurez la base de données en exécutant le script SQL fourni.
3. Mettez à jour les paramètres de connexion dans le fichier `application.properties`.
4. Dans Eclipse: Run as Java Application le JEEGestionApplication.java dans le package com.exemple.jee_gestion dans le src/main/java dans le projet jee-gestion.
5. Accédez à l'application via [http://localhost:8080](http://localhost:8080) dans un navigateur.


lien du github de la partie 2 ( hibernate ) : [https://github.com/lnolland/jee_gestion_part2](https://github.com/lnolland/jee_gestion_part2).
---

## **Équipe**
- **Leo Nolland** : Spring Boot, Java, JSP
- **Rayan Chaouche** : Java, JSP, CSS
- **Mathias Dufour** : MySQL, CSS, JSP
- **Yssam Bairouki** : Java, MySQL, Spring Boot

---


