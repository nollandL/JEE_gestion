<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cr�er un Compte</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CreerCompte.css">
</head>
<body>
    <h2>Cr�er un Compte</h2>
    <form action="/compte/creation" method="post">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" required>

        <label for="prenom">Pr�nom :</label>
        <input type="text" id="prenom" name="prenom" required>

        <label for="contact">Contact :</label>
        <input type="text" id="contact" name="contact" required>

        <label for="role">R�le :</label>
        <select id="role" name="role">
            <option value="ENSEIGNANT">Enseignant</option>
            <option value="ETUDIANT">�tudiant</option>
        </select>

        <label for="username">Nom d'utilisateur :</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Cr�er le compte</button>
    </form>

    <!-- Affichage d'un message d'erreur s'il existe -->
    <c:if test="${not empty message}">
        <p style="color: red;">${message}</p>
    </c:if>
</body>
</html>
