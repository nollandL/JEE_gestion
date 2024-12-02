<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Cours</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ajouterCours.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

    <h2>Ajouter un Cours</h2>

    <form action="${role == 'ADMINISTRATEUR' ? '/admin/ajouterCours' : '/enseignant/ajouterCours'}" method="post">
        <label for="dateCours">Date et Heure :</label>
        <input type="datetime-local" id="dateCours" name="dateCours" required>
        
        <label for="matiereId">Matière :</label>
        <select id="matiereId" name="matiereId" required>
            <c:forEach items="${matieres}" var="matiere">
                <option value="${matiere.id}">${matiere.nom}</option>
            </c:forEach>
        </select>
        
        <!-- Si l'utilisateur est un enseignant, transmettre l'enseignantId -->
        <c:if test="${role == 'ENSEIGNANT'}">
            <input type="hidden" name="enseignantId" value="${enseignantId}">
        </c:if>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
