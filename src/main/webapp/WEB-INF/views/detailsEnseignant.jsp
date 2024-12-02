<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>D�tails de l'Enseignant</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/detailsEnseignant.css">
</head>
<body>
    <!-- Inclusion de l'en-t�te -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

    <h2>D�tails de l'Enseignant</h2>
    <p><strong>ID :</strong> ${enseignant.id}</p>
    <p><strong>Nom :</strong> ${enseignant.nom}</p>
    <p><strong>Pr�nom :</strong> ${enseignant.prenom}</p>
    <p><strong>Contact :</strong> ${enseignant.contact}</p>
    <button onclick="window.location.href='/enseignant/modifierContact'">Modifier le Contact</button>

    <h3>Mati�res Enseign�es</h3>
    <ul>
        <c:forEach var="matiere" items="${enseignant.matieres}">
            <li>${matiere.nom}</li>
        </c:forEach>
    </ul>

    <h3>Cours Associ�s</h3>
    <table border="1">
        <tr>
            <th>Mati�re</th>
            <th>Date</th>
        </tr>
        <c:forEach var="cours" items="${coursAssocies}">
            <tr>
                <td>${cours.matiere.nom}</td>
                <td>${cours.dateCours}</td>
            </tr>
        </c:forEach>
    </table>

    <!-- Bouton pour retourner au menu -->
    <button onclick="window.location.href='/enseignant/menu'">Retour au menu</button>
</body>
</html>
