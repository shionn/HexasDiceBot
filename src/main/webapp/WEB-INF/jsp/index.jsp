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
				<input type="text" id="hero-hab" class="pure-u-23-24" name="hero-hab" value="${ldvelh.heroHab}"/>
			</div>
			<div class="pure-u-1-3">
				<label for="monster-hab">Habilité Monstre</label>
				<input type="text" id="monster-hab" class="pure-u-23-24" name="monster-hab" value="${ldvelh.monsterHab}"/>
			</div>
			<div class="pure-u-1-3">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Combat !</button>
			</div>
		</div>
	</fieldset>
</form:form>
<h1>JDR</h1>
<h2>Lancer</h2>


<h2>Configuration Joueur</h2>


<h1>HeroQuest (Non fini ne marche pas)</h1>
<spring:url value="/heroquest/atk" var="url" />
<form:form action="${url}" method="POST" class="pure-form pure-form-stacked">
	<fieldset>
		<legend>Joueur attaque</legend>
		<div class="pure-g">
			<div class="pure-u-1-5">
				<label for="character">Joueur</label>
				<select id="character" class="pure-u-23-24" name="character">
					<option value="barbarian" <c:if test="${heroQuest.active == CharacterClass.barbarian }"> selected="selected"</c:if>>Barbarian</option>
					<option value="dwarf" <c:if test="${heroQuest.active == CharacterClass.dwarf }"> selected="selected"</c:if>>Dwarf</option>
					<option value="elf" <c:if test="${heroQuest.active == CharacterClass.elf }"> selected="selected"</c:if>>Elf</option>
					<option value="magician" <c:if test="${heroQuest.active == CharacterClass.magician }"> selected="selected"</c:if>>Magician</option>
				</select>
			</div>
			<div class="pure-u-1-5">
				<label for="atk">Attaque</label>
				<input type="text" id="atk" class="pure-u-23-24" name="atk" value="${heroQuest.activePlayer.atk}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="monster">Monstre</label>
				<input type="text" id="monster" class="pure-u-23-24" name="monster" value="${heroQuest.monster.player}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="def">Defense</label>
				<input type="text" id="def" class="pure-u-23-24" name="def" value="${heroQuest.monster.def}"/>
			</div>
			<div class="pure-u-1-5">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Combat !</button>
			</div>
		</div>
	</fieldset>
</form:form>
<spring:url value="/heroquest/def" var="url" />
<form:form action="${url}" method="POST" class="pure-form pure-form-stacked">
	<fieldset>
		<legend>Monstre attaque</legend>
		<div class="pure-g">
			<div class="pure-u-1-5">
				<label for="monster">Monstre</label>
				<input type="text" id="monster" class="pure-u-23-24" name="monster" value="${heroQuest.monster.player}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="atk">Attaque</label>
				<input type="text" id="atk" class="pure-u-23-24" name="atk" value="${heroQuest.monster.atk}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="character">Joueur</label>
				<select id="character" class="pure-u-23-24" name="character">
					<option value="barbarian" <c:if test="${heroQuest.active == CharacterClass.barbarian }"> selected="selected"</c:if>>Barbarian</option>
					<option value="dwarf" <c:if test="${heroQuest.active == CharacterClass.dwarf }"> selected="selected"</c:if>>Dwarf</option>
					<option value="elf" <c:if test="${heroQuest.active == CharacterClass.elf }"> selected="selected"</c:if>>Elf</option>
					<option value="magician" <c:if test="${heroQuest.active == CharacterClass.magician }"> selected="selected"</c:if>>Magician</option>
				</select>
			</div>
			<div class="pure-u-1-5">
				<label for="def">Defense</label>
				<input type="text" id="def" class="pure-u-23-24" name="def" value="${heroQuest.activePlayer.def}"/>
			</div>
			<div class="pure-u-1-5">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Combat !</button>
			</div>
		</div>
	</fieldset>

</form:form>


<h2>Configuration</h2>
<spring:url value="/heroquest/configuration" var="url" />
<form:form action="${url}" method="POST" class="pure-form pure-form-stacked">
	<fieldset>
		<div class="pure-g">
			<div class="pure-u-1-5">
				<label for="barbarian">Barbarian</label>
				<input type="text" id="barbarian" class="pure-u-23-24" name="barbarian" value="${heroQuest.barbarian.player}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="dwarf">Dwarf</label>
				<input type="text" id="dwarf" class="pure-u-23-24" name="dwarf" value="${heroQuest.dwarf.player}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="elf">Elf</label>
				<input type="text" id="elf" class="pure-u-23-24" name="elf" value="${heroQuest.elf.player}"/>
			</div>
			<div class="pure-u-1-5">
				<label for="magician">Magician</label>
				<input type="text" id="magician" class="pure-u-23-24" name="magician" value="${heroQuest.magician.player}"/>
			</div>
			<div class="pure-u-1-5">
				<button type="submit" class="pure-button pure-button-primary" style="margin-top: 26px">Configurer</button>
			</div>
		</div>
	</fieldset>
</form:form>
</body>
</html>