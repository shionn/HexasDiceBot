<%@ page pageEncoding="UTF-8"%>
<%@ page import="shionn.hexas.heroquest.CharacterClass" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- <%@ taglib tagdir="/WEB-INF/tags" prefix="t"%> --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
	<meta name="mobile-web-app-capable" content="yes" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css"
		integrity="sha384-X38yfunGUhNzHpBaEBsWLO+A0HDYOQi8ufWDkZ0k9e0eXz/tH3II7uKZ9msv++Ls"
		crossorigin="anonymous">
	<title>HexasDiceBot JDR</title>
</head>
<body style="margin: 0 10px;">

<h1>JDR</h1>

<a href="<spring:url value="/jdr/enable"/>" class="pure-button">
	<c:if test="${jdr.enable}">Désactiver JDR</c:if>
	<c:if test="${not jdr.enable}">Activer JDR</c:if>
</a>
<hr>

<table class="pure-table">
	<tr>
		<th>Joueur</th><th>Classe</th><th>#</th>
	</tr>
	<c:forEach items="${jdr.players}" var="player">
		<tr>
			<td>${player.name}</td>
			<td>${player.clazz}</td>
			<td><a class="pure-button" href="<spring:url value="/jdr/del-player/${player.name}"/>">Supprimer</a>
		</td>
	</c:forEach>
</table>

<h1>Configuration</h1>
<spring:url value="/jdr/add-player" var="url" />
<form:form action="${url}" method="POST" class="pure-form pure-form-stacked">
	<fieldset>
		<legend>Ajouter un joueur</legend>
		<div class="pure-g">
			<div class="pure-u-1-3">
				<label for="name">Joueur</label>
				<input type="text" id="name" class="pure-u-23-24" name="name"/>
			</div>
			<div class="pure-u-1-3">
				<label for="clazz">Classe</label>
				<input type="text" id="clazz" class="pure-u-23-24" name="clazz"/>
			</div>
			<div class="pure-u-1-3">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Ajouter</button>
			</div>
		</div>
	</fieldset>
</form:form>

</body>
</html>