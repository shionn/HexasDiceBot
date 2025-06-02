<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">
<h1>Heroquest</h1>

<a href="<spring:url value="/heroquest/enable"/>">
	<c:if test="${hq.enable}">Désactiver Heroquest</c:if>
	<c:if test="${not hq.enable}">Activer Heroquest</c:if>
</a>

<%-- <a href="<spring:url value="/jdr/dice-view"/>" target="_blanck">Open dice view</a> --%>

<h2>Joueur enregistrés</h2>
<table>
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
				<a href="<spring:url value="/jdr/edit-player/${player.id}"/>">Editer</a>
				<a href="<spring:url value="/jdr/del-player/${player.id}"/>">Supprimer</a>
			</td>
		</tr>
	</c:forEach>
</table>

<h2>Action</h2>

<a href="<spring:url value="/jdr/action/priority"/>">Jet de priorité</a><br><br>
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
<h2>
	<c:if test="${empty player}">Ajouter un joueur</c:if>
	<c:if test="${not empty player}">Editer un joueur</c:if>
</h2>
<spring:url value="/jdr/add-player" var="url" />
<c:if test="${not empty player}">
	<spring:url value="/jdr/edit-player/${player.id}" var="url" />
</c:if>
<form:form action="${url}" method="POST">
	<label for="name">Pseudo</label>
	<input type="text" id="pseudo" name="pseudo" value="${player.pseudo}"/>
	<label for="name">Nom</label>
	<input type="text" id="name" name="name" value="${player.name}"/>
	<label for="clazz">Classe</label>
	<input type="text" id="clazz" name="clazz" value="${player.clazz}"/>
	<label for="strenght">Force</label>
	<input type="text" id="strenght" name="strenght" value="${player.strenght}"/>
	<label for="clazz">Dexterité</label>
	<input type="text" id="dexterity" name="dexterity" value="${player.dexterity}"/>
	<label for="clazz">Constitution</label>
	<input type="text" id="constitution" name="constitution" value="${player.constitution}"/>
	<label for="clazz">Intéligence</label>
	<input type="text" id="intelligence" name="intelligence" value="${player.intelligence}"/>
	<label for="clazz">Sagesse</label>
	<input type="text" id="wisdom" name="wisdom" value="${player.wisdom}"/>
	<label for="clazz">Charisme</label>
	<input type="text" id="charisma" name="charisma" value="${player.charisma}"/>
	<button type="submit">Valider</button>
</form:form>
</jsp:attribute>
</t:template>
