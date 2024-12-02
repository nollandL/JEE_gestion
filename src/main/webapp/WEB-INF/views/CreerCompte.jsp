<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Créer un Compte</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CreerCompte.css">
</head>
<body>
    <h2>Créer un Compte</h2>
    <form action="/compte/creation" method="post">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" required>

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" required>

        <label for="contact">Contact :</label>
        <input type="text" id="contact" name="contact" required>

        <label for="role">Rôle :</label>
        <select id="role" name="role">
            <option value="ENSEIGNANT">Enseignant</option>
            <option value="ETUDIANT">Étudiant</option>
        </select>

        <label for="username">Nom d'utilisateur :</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Créer le compte</button>
    </form>

    <!-- Affichage d'un message d'erreur s'il existe -->
    <c:if test="${not empty message}">
        <p style="color: red;">${message}</p>
    </c:if>
</body>
</html>
