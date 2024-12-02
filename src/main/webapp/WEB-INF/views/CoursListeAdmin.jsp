<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Cours</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CoursListeAdmin.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h1>Cours Sans Professeur</h1>
    <table border="1">
        <tr>
            <th>Nom de la Matière</th>
            <th>Date</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${coursSansProf}" var="cours">
            <tr>
                <td>${cours.matiere.nom}</td>
                <td>${cours.dateCours}</td>
                <td>
                	<!-- Bouton pour modifier la date -->
                    <form action="/admin/modifierDateCours" method="get" style="display:inline;">
                        <input type="hidden" name="coursId" value="${cours.id}" />
                        <button type="submit">Modifier la Date</button>
                    </form>
                    
                 	<form action="/enseignant/supprimerCoursComplet" method="post" style="display:inline;">
                        <input type="hidden" name="coursId" value="${cours.id}" />
                        <button type="submit">Supprimer Complètement</button>
                	</form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h1>Cours Associés</h1>
    <table border="1">
        <tr>
            <th>Nom de la Matière</th>
            <th>Date</th>
            <th>Enseignant</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${coursAssocies}" var="cours">
            <tr>
                <td>${cours.matiere.nom}</td>
                <td>${cours.dateCours}</td>
                <td>${cours.enseignant.nom} ${cours.enseignant.prenom}</td>
                <td>
                    <!-- Bouton pour modifier la date -->
                    <form action="/admin/modifierDateCours" method="get" style="display:inline;">
                        <input type="hidden" name="coursId" value="${cours.id}" />
                        <button type="submit">Modifier la Date</button>
                    </form>
                    
                    <form action="/enseignant/supprimerCoursComplet" method="post" style="display:inline;">
                        <input type="hidden" name="coursId" value="${cours.id}" />
                        <button type="submit">Supprimer Complètement</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <!-- Lien pour ajouter un cours-->    
    <button onclick="window.location.href='/admin/ajouterCours'">Ajouter un cours</button>
    
</body>
</html>