<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu Administrateur</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/MenuAdmin.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h2>Bienvenue, Administrateur ${sessionScope.username}</h2>
    <ul>
        <li><a href="/admin/enseignants">Gérer les enseignants</a></li>
        <li><a href="/etudiants">Gérer les étudiants</a></li>
        <li><a href="/admin/cours">Gérer les cours</a></li>
        <li><a href="/login/logout">Se déconnecter</a></li>
    </ul>
</body>
</html>
