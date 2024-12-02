<!DOCTYPE html>
<html>
<head>
    <title>Menu Professeur</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/MenuEnseignant.css">
</head>
<body>
    <!-- Inclusion de l'en-t�te -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <h1>Menu Professeur</h1>
    <ul>
        <li><a href="../etudiants">Voir la liste des �tudiants</a></li>
        <li><a href="/enseignant/cours">Voir la liste des cours</a></li>
        <li><a href="/enseignant/details">D�tails de l'enseignant</a></li>
    </ul>
</body>
</html>
