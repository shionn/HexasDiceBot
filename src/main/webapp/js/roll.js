var i = 0;
window.setInterval(()=> {
	let e = document.getElementById("roll");
	e.style['background-position-x']=(i*512)+"px";
	i++;
	if (i=12) i=0;
}, 1000);
