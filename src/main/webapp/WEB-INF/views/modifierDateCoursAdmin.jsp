<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier la Date du Cours</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modifierDateCoursAdmin.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h1>Modifier la Date du Cours</h1>
    <form action="/admin/sauvegarderDateCours" method="post">
        <input type="hidden" name="coursId" value="${cours.id}" />
        <label for="nouvelleDate">Nouvelle Date :</label>
        <input type="datetime-local" id="nouvelleDate" name="nouvelleDate" required />
        <button type="submit">Enregistrer</button>
    </form>
</body>
</html>
