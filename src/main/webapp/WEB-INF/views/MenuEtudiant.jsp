<!DOCTYPE html>
<html>
<head>
    <title>Menu Étudiant</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/MenuEtudiant.css">
</head>
<body>
    <!-- Inclusion de l'en-tête -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h1>Menu Étudiant</h1>
    <ul>
        <li><a href="/etudiants/details">Voir mes détails</a></li>
        <li><a href="/etudiants/cours">Voir la liste des cours</a></li>
    </ul>
</body>
</html>
