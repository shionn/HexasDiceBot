<%@ page pageEncoding="UTF-8"%>
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
	<title>HexasDiceBot</title>
</head>
<body style="margin: 0 10px;">
<h1>Commandes</h1>
<a href="<spring:url value="/dice/1D6"/>" class="pure-button">Demander 1D6</a>
<a href="<spring:url value="/dice/2D6"/>" class="pure-button">Demander 2D6</a>
<h1>Combat</h1>
<spring:url value="/dice/battle" var="url" />
<form:form action="${url}" method="POST" class="pure-form pure-form-stacked">
	<fieldset>
		<div class="pure-g">
			<div class="pure-u-1-3">
				<label for="hero-hab">Habilité Hero</label>
				<input type="text" id="hero-hab" class="pure-u-23-24" name="hero-hab" value="${heroHab}"/>
			</div>
			<div class="pure-u-1-3">
				<label for="monster-hab">Habilité Monstre</label>
				<input type="text" id="monster-hab" class="pure-u-23-24" name="monster-hab" value="${monsterHab}"/>
			</div>
			<div class="pure-u-1-3">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Combat !</button>
			</div>
		</div>
	</fieldset>
</form:form>

</body>
</html>