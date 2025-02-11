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

<h2>Joueur enregistrés</h2>
<table class="pure-table">
	<tr>
		<th>Pseudo</th><th>Nom</th><th>Classe</th>
		<th>Force</th><th>Dexterité</th><th>Constitution</th><th>Intéligence</th><th>Sagesse</th><th>Charisme</th>
		<th>#</th>
	</tr>
	<c:forEach items="${players}" var="player">
		<tr>
			<td>${player.pseudo}</td>
			<td>${player.name}</td>
			<td>${player.clazz}</td>
			<td>${player.strenght} (${jdr.strmod(player.strenght)})</td>
			<td>${player.dexterity} (${jdr.strmod(player.dexterity)})</td>
			<td>${player.constitution} (${jdr.strmod(player.constitution)})</td>
			<td>${player.intelligence} (${jdr.strmod(player.intelligence)})</td>
			<td>${player.wisdom} (${jdr.strmod(player.wisdom)})</td>
			<td>${player.charisma} (${jdr.strmod(player.charisma)})</td>
			<td>
				<a class="pure-button" href="<spring:url value="/jdr/edit-player/${player.id}"/>">Editer</a>
				<a class="pure-button" href="<spring:url value="/jdr/del-player/${player.id}"/>">Supprimer</a>
			</td>
		</tr>
	</c:forEach>
</table>

<h2>Action</h2>
<div class="pure-g">
	<div class="pure-u-1-3">
		<table class="pure-table" style="margin-bottom: 10px">
			<tr>
				<th>Nom</th>
				<th>Classe</th>
				<th>Priorité</th>
			</tr>
			<c:forEach items="${prioirities}" var="player">
				<tr>
					<td>${player.name}</td><td>${player.clazz}</td><td>${player.priority}</td>
				</tr>
			</c:forEach>
		</table>
		<a href="<spring:url value="/jdr/action/priority"/>" class="pure-button pure-button-primary">Jet de priorité</a>
	</div>
</div>
<h2>Configuration</h2>
<spring:url value="/jdr/add-player" var="url" />
<c:if test="${not empty player}">
	<spring:url value="/jdr/edit-player/${player.id}" var="url" />
</c:if>
<form:form action="${url}" method="POST" class="pure-form pure-form-stacked">
	<fieldset>
		<c:if test="${empty player}">
			<legend>Ajouter un joueur</legend>
		</c:if>
		<c:if test="${not empty player}">
			<legend>Editer un joueur</legend>
		</c:if>
		<div class="pure-g">
			<div class="pure-u-1-12">
				<label for="name">Pseudo</label>
				<input type="text" id="pseudo" class="pure-u-23-24" name="pseudo" value="${player.pseudo}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="name">Nom</label>
				<input type="text" id="name" class="pure-u-23-24" name="name" value="${player.name}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="clazz">Classe</label>
				<input type="text" id="clazz" class="pure-u-23-24" name="clazz" value="${player.clazz}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="strenght">Force</label>
				<input type="text" id="strenght" class="pure-u-23-24" name="strenght" value="${player.strenght}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="clazz">Dexterité</label>
				<input type="text" id="dexterity" class="pure-u-23-24" name="dexterity" value="${player.dexterity}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="clazz">Constitution</label>
				<input type="text" id="constitution" class="pure-u-23-24" name="constitution" value="${player.constitution}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="clazz">Intéligence</label>
				<input type="text" id="intelligence" class="pure-u-23-24" name="intelligence" value="${player.intelligence}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="clazz">Sagesse</label>
				<input type="text" id="wisdom" class="pure-u-23-24" name="wisdom" value="${player.wisdom}"/>
			</div>
			<div class="pure-u-1-12">
				<label for="clazz">Charisme</label>
				<input type="text" id="charisma" class="pure-u-23-24" name="charisma" value="${player.charisma}"/>
			</div>
			<div class="pure-u-1-12">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Valider</button>
			</div>
		</div>
	</fieldset>
</form:form>

</body>
</html>