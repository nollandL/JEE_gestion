<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>D�tails de l'�tudiant</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/detailsEtudiantEnseignant.css">
</head>
<body>
    <!-- Inclusion de l'en-t�te -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

    <h2>D�tails de l'�tudiant</h2>
    <p><strong>ID :</strong> ${etudiant.id}</p>
    <p><strong>Nom :</strong> ${etudiant.nom}</p>
    <p><strong>Pr�nom :</strong> ${etudiant.prenom}</p>
    <p><strong>Date de Naissance :</strong> ${etudiant.dateNaissance}</p>
    <p><strong>Contact :</strong> ${etudiant.contact}</p>

    <!-- Affichage des notes et moyennes par mati�re -->
    <h3>Notes de l'�tudiant</h3>
    <table border="1">
        <tr>
            <th>Mati�re</th>
            <c:forEach var="index" begin="1" end="${maxNoteCount}">
                <th>Note ${index}</th>
            </c:forEach>
            <th>Moyenne de l'�tudiant</th>
            <th>Moyenne G�n�rale de la Mati�re</th>
            <th>Action</th>
        </tr>

        <c:forEach var="entry" items="${notesData}">
            <tr>
                <!-- Afficher le nom de la mati�re -->
                <td>${entry.value['matiereNom']}</td>

                <!-- Affichage des notes dynamiques -->
                <c:forEach var="note" items="${entry.value['notes']}">
                    <td>${note.note}</td>
                </c:forEach>

                <!-- Si le nombre de notes est inf�rieur au maximum, ajoutez des colonnes vides -->
                <c:forEach var="i" begin="1" end="${maxNoteCount - fn:length(entry.value['notes'])}">
                    <td></td>
                </c:forEach>

                <!-- Afficher la moyenne de l'�tudiant pour cette mati�re -->
                <td>${entry.value['moyenneEtudiant']}</td>

                <!-- Afficher la moyenne g�n�rale de la mati�re -->
                <td>${entry.value['moyenneGenerale']}</td>

                <!-- Bouton pour supprimer la derni�re note -->
                <td>
                    <form action="/etudiants/supprimerNote" method="get">
                        <input type="hidden" name="etudiantId" value="${etudiant.id}" />
                        <input type="hidden" name="matiereId" value="${entry.key}" />
                        <button type="submit">Supprimer la derni�re note</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <!-- Affichage des inscriptions aux cours -->
    <h3>Cours Inscrits</h3>
    <table border="1">
        <tr>
            <th>Nom du Cours</th>
            <th>Date du Cours</th>
            <th>Enseignant</th>
        </tr>
        <c:forEach var="inscription" items="${inscriptions}">
            <tr>
                <td>${inscription.cours.matiere.nom}</td>
                <td>${inscription.cours.dateCours}</td>
                <td>${inscription.cours.enseignant.nom} ${inscription.cours.enseignant.prenom}</td>
            </tr>
        </c:forEach>
    </table>

    <!-- Lien pour saisir les notes -->
    <button onclick="window.location.href='/notes/ajouterNote?etudiantId=${etudiant.id}'">Saisir les notes</button>
    
    <!-- Option pour revenir � la liste -->
    <button onclick="window.location.href='/etudiants'">Retour � la liste des �tudiants</button>
    
    <!-- Lien pour g�n�rer le relev� de notes -->
	<button onclick="window.location.href='/etudiants/genererReleve?id=${etudiant.id}'">Relev� de notes</button>
    
</body>
</html>
