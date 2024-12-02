<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>D�tails de l'Enseignant</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/detailsEnseignantAdmin.css">
</head>
<body>
    <!-- Inclusion de l'en-t�te -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h1>D�tails de l'Enseignant</h1>

    <!-- Informations de l'enseignant -->
    <table border="1">
        <tr>
            <th>Nom</th>
            <td>${enseignant.nom}</td>
        </tr>
        <tr>
            <th>Pr�nom</th>
            <td>${enseignant.prenom}</td>
        </tr>
        <tr>
            <th>Contact</th>
            <td>${enseignant.contact}</td>
        </tr>
        <tr>
            <th>Mati�res</th>
            <td>
                <ul>
                    <c:forEach var="matiere" items="${enseignant.matieres}">
                        <li>${matiere.nom}</li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
    </table>

    <h2>Cours Associ�s</h2>

    <!-- Liste des cours associ�s -->
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

    <br>
    <a href="/admin/enseignants">Retour � la liste des enseignants</a>
</body>
</html>
