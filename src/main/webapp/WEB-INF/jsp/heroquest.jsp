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
		<th>Pseudo</th><th>Nom</th>
		<th>Attaque</th><th>Défense</th><th>PV</th><th>Esprit</th><th>Actif</th>
		<th>#</th>
	</tr>
	<c:forEach items="${players}" var="player">
		<tr>
			<td>${player.pseudo}</td>
			<td>${player.name}</td>
			<td>${player.attack}</td>
			<td>${player.defence}</td>
			<td>${player.body}</td>
			<td>${player.mind}</td>
			<td>${player.enable?"oui":"non"}</td>
			<td>
				<a href="<spring:url value="/heroquest/enable-player/${player.id}"/>">
					<c:if test="${player.enable}">Désactiver</c:if>
					<c:if test="${not player.enable}">Activer</c:if>
				</a>
				<a href="<spring:url value="/heroquest/edit-player/${player.id}"/>">Editer</a>
				<a href="<spring:url value="/heroquest/del-player/${player.id}"/>">Supprimer</a>
			</td>
		</tr>
	</c:forEach>
</table>

<h2>
	<c:if test="${empty player}">Ajouter un joueur</c:if>
	<c:if test="${not empty player}">Editer un joueur</c:if>
</h2>
<spring:url value="/heroquest/add-player" var="url" />
<c:if test="${not empty player}">
	<spring:url value="/heroquest/edit-player/${player.id}" var="url" />
</c:if>
<form:form action="${url}" method="POST">
	<label for="name">Pseudo</label>
	<input type="text" id="pseudo" name="pseudo" value="${player.pseudo}"/>
	<label for="name">Nom</label>
	<input type="text" id="name" name="name" value="${player.name}"/>
	<label for="attack">Attaque</label>
	<input type="text" id="attack" name="attack" value="${player.attack}"/>
	<label for="defence">Défense</label>
	<input type="text" id="defence" name="defence" value="${player.defence}"/>
	<label for="clazz">PV</label>
	<input type="text" id="body" name="body" value="${player.body}"/>
	<label for="clazz">Esprit</label>
	<input type="text" id="mind" name="mind" value="${player.mind}"/>
	<button type="submit">Valider</button>
</form:form>
</jsp:attribute>
</t:template>
