<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Cours</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CoursDisponibles.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h2>Cours Disponibles pour Inscription</h2>
    <table border="1">
        <tr>
            <th>Nom du Cours</th>
            <th>Date</th>
            <th>Enseignant</th>
            <th>Action</th>
        </tr>
        <c:forEach var="cours" items="${coursNonInscrits}">
            <tr>
                <td>${cours.matiere.nom}</td>
                <td>${cours.dateCours}</td>
                <td>${cours.enseignant.nom} ${cours.enseignant.prenom}</td>
                <td>
                    <form action="/etudiants/inscrire" method="post">
                        <input type="hidden" name="coursId" value="${cours.id}" />
                        <button type="submit">S'inscrire</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>Cours Actuellement Inscrits</h2>
    <table border="1">
        <tr>
            <th>Nom du Cours</th>
            <th>Date</th>
            <th>Enseignant</th>
            <th>Action</th>
        </tr>
        <c:forEach var="cours" items="${coursInscrits}">
            <tr>
                <td>${cours.matiere.nom}</td>
                <td>${cours.dateCours}</td>
                <td>${cours.enseignant.nom} ${cours.enseignant.prenom}</td>
                <td>
                    <form action="/etudiants/desinscrire" method="post">
                        <input type="hidden" name="coursId" value="${cours.id}" />
                        <button type="submit">Se désinscrire</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
