
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<div class="header">
    <!-- Lien vers le menu selon le rôle -->
    <c:choose>
        <c:when test="${sessionScope.userRole == 'ADMINISTRATEUR'}">
            <a href="/admin/menu">Menu Admin</a>
        </c:when>
        <c:when test="${sessionScope.userRole == 'ENSEIGNANT'}">
            <a href="/enseignant/menu">Menu Enseignant</a>
        </c:when>
        <c:when test="${sessionScope.userRole == 'ETUDIANT'}">
            <a href="/etudiants/menu">Menu Étudiant</a>
        </c:when>
        <c:otherwise>
            <a href="/">Accueil</a>
        </c:otherwise>
    </c:choose>

    <!-- Lien pour la déconnexion -->
    <a href="/login/logout">Déconnexion</a>
</div>
