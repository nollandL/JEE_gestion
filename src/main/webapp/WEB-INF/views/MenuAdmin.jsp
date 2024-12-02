<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu Administrateur</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/MenuAdmin.css">
</head>
<body>
    <!-- Inclusion de l'en-t�te -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h2>Bienvenue, Administrateur ${sessionScope.username}</h2>
    <ul>
        <li><a href="/admin/enseignants">G�rer les enseignants</a></li>
        <li><a href="/etudiants">G�rer les �tudiants</a></li>
        <li><a href="/admin/cours">G�rer les cours</a></li>
        <li><a href="/login/logout">Se d�connecter</a></li>
    </ul>
</body>
</html>
