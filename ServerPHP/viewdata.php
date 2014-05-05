<html>
<body>
<?php

	//get and check connection
	$db = new mysqli("localhost", "snadmin", "snadmin*", "sensornetworks");
	if($db->connect_error){
		die("error");
	}
	
	
	$query_string = "select *from sp14_jyu_whitelist join sp14_jyu_datalog on sp14_jyu_whitelist.radioaddr = sp14_jyu_datalog.radioaddr";
	$res = $db->query($query_string);
	
	echo "<table border = '1'>
	<tr>
	<th> Radio Address</th>
	<th> Name</th>
	<th> Date and Time</th>
	<th> Air Temperature</th>
	<th> Body Temperature</th>
	<th> Force Applied to Body</th>
	</tr>";
	
	while ($row = $res->fetch_assoc()){

		echo "<tr>";
		echo "<td>" . $row['radioaddr'] . "</td>";
		echo "<td>" . $row['name'] . "</td>";
		echo "<td>" . $row['dateandtime'] . "</td>";
		echo "<td>" . $row['airTemp'] . "</td>";
		echo "<td>" . $row['bodyTemp'] . "</td>";
		echo "<td>" . $row['forceVal'] . "</td>";
		echo "</tr>";

	}
	echo "</table>";


	


	
	

	
	


?>
</body>
</html>