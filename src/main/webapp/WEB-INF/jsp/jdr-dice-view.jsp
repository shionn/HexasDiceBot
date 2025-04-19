<%@ page pageEncoding="UTF-8"%>
<%@ page import="shionn.hexas.heroquest.CharacterClass" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<body>
<spring:url value="/img/d20/r01.png" var="url" />
<div  id="roll" style="width: 512px; height: 512px; background: url('${url}'); background-psition-x: 0px" >
</div>
<script type="text/javascript">
var i = 11;
window.setInterval(()=> {
	let e = document.getElementById("roll");
	e.style.backgroundPositionX = (i*512)+"px";
	i--;
	if (i==-1) i=11;
}, 100);
</script>
</body>
</html>