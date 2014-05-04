<html>
<body>
<h1> ADD radio addresses here!!!</h1>
<form id="myform" action="submit.php" method="post">
	RFID card address:<input type="text" name = "addr"> <br>
	user:<input type="text" name = "name">
	
	<input type="submit" value="Submit">
	<!--<button id="sub"> Submit </button>-->
</form>


<h1> Remove radio addresses here!!!</h1>
<form id="myform" action="remove.php" method="post">
	RFID card address:<input type="text" name = "addr"> <br>
	user:<input type="text" name = "name">
	
	
	<input type="submit" value="Submit">
	<!--<button id="sub"> Submit </button>-->
</form>

<h3>Click to view the log of who has come and gone</h3>
<a href="viewdata.php">View Log</a>

<h3>Click to clear the log</h3>
<a href="clearlog.php">Clear Log</a>



</body>
</html>