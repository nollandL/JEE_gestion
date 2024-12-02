<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/EtudiantsListe.css">
    <script>
	    function confirmDelete(etudiantId) {
	        if (confirm("Êtes-vous sûr de vouloir supprimer cet étudiant ?")) {
	            window.location.href = '/etudiants/supprimerEtudiant?id=' + etudiantId;
	        }
	    }
	</script>
    
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

    <h2>Liste des Étudiants</h2>

    <!-- Bouton pour ajouter un étudiant -->
    <button onclick="window.location.href='/etudiants/ajouterEtudiant'">Ajouter un Étudiant</button>

    <!-- Formulaire de recherche -->
    <form action="/etudiants" method="get">
        <input type="text" name="keyword" placeholder="Rechercher par nom ou prénom" value="${keyword}" />

        <select name="matiereId">
            <option value="">Toutes les matières</option>
            <c:forEach var="matiere" items="${matieres}">
                <option value="${matiere.id}" <c:if test="${matiere.id == matiereId}">selected</c:if>>${matiere.nom}</option>
            </c:forEach>
        </select>

        <button type="submit">Filtrer</button>
    </form>

    <!-- Affichage de la liste des étudiants -->
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="etudiant" items="${etudiants}">
            <tr>
                <td>${etudiant.id}</td>
                <td>${etudiant.nom}</td>
                <td>${etudiant.prenom}</td>
                <td>
                    <a href="/etudiants/detailsEtudiantEnseignant?id=${etudiant.id}">Détails</a> |
                    <a href="/etudiants/modifierEtudiant?id=${etudiant.id}">Modifier</a> |
                    <button onclick="confirmDelete(${etudiant.id})">Supprimer</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
