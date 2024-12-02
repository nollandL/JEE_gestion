<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier le Contact</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modifierContactEtudiant.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h2>Modifier le Contact</h2>
    <form action="/etudiants/sauvegarderContact" method="post">
        <label for="contact">Contact :</label>
        <input type="text" id="contact" name="contact" value="${etudiant.contact}" required />
        <button type="submit">Sauvegarder</button>
    </form>
</body>
</html>
