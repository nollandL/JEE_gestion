<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier le Contact</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modifierContact.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h2>Modifier le Contact</h2>
    <form action="/admin/sauvegarderContact" method="post">
        <label for="contact">Nouveau Contact :</label>
        <input type="text" id="contact" name="contact" value="${enseignant.contact}" required />
        <button type="submit">Enregistrer</button>
    </form>

    <!-- Bouton pour retourner aux détails -->
    <button onclick="window.location.href='/admin/details'">Retour aux Détails</button>
</body>
</html>