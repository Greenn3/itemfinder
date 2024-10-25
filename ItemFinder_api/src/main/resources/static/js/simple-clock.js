function simpleTime() {
	const today = new Date();
/*
	const year = today.getFullYear();
	const month = (today.getMonth() + 1).toString().padStart(2, '0');
	const day = today.getDate().toString().padStart(2, '0');
*/
	const hour = today.getHours().toString().padStart(2, '0');
	const minutes = today.getMinutes().toString().padStart(2, '0');
	const seconds = today.getSeconds().toString().padStart(2, '0');

	document.getElementById("simple-clock").innerHTML = /*year + "-" + month + "-" + day + "  |  " +*/ hour + ":" + minutes + ":" + seconds;
	setTimeout(simpleTime,1000);
}

document.addEventListener("DOMContentLoaded", function() {
	if (document.getElementById("simple-clock")) {
		simpleTime();
	}
});