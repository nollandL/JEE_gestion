<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ListeEnseignants.css">
    <title>Liste des Enseignants</title>
    <script>
        function confirmDelete(formId) {
            if (confirm("Êtes-vous sûr de vouloir supprimer cet enseignant ?")) {
                document.getElementById(formId).submit();
            }
        }
    </script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h2>Liste des Enseignants</h2>
    <table border="1">
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Contact</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="enseignant" items="${enseignants}">
            <tr>
                <td>${enseignant.nom}</td>
                <td>${enseignant.prenom}</td>
                <td>${enseignant.contact}</td>
                <td>
                	<a href="/admin/detailsEnseignant?id=${enseignant.id}">Détails</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="/admin/ajouterEnseignant">Ajouter un Enseignant</a>
</body>
</html>
