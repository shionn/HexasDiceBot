<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:template>
<jsp:attribute name="content">
<h1>Livre dont vous êtes le Hero</h1>
<a href="<spring:url value="/ldvelh/enable"/>">
	<c:if test="${ldvelh.enable}">Désactiver Ldvelh</c:if>
	<c:if test="${not ldvelh.enable}">Activer Ldvelh</c:if>
</a>
<h2>Commandes</h2>
<a href="<spring:url value="/ldvelh/dice/1D6"/>">Demander 1D6</a>
<a href="<spring:url value="/ldvelh/dice/2D6"/>">Demander 2D6</a>
<h2>Combat</h2>
<spring:url value="/ldvelh/dice/battle" var="url" />
<form:form action="${url}" method="POST">
	<label for="hero-hab">Habilité Hero</label>
	<input type="text" id="hero-hab" name="hero-hab" value="${ldvelh.heroHab}"/>
	<label for="monster-hab">Habilité Monstre</label>
	<input type="text" id="monster-hab" name="monster-hab" value="${ldvelh.monsterHab}"/>
	<button type="submit">Combat !</button>
</form:form>
</jsp:attribute>
</t:template>
