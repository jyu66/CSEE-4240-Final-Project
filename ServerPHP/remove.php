<html>
<body>
<?php

	$address = $_POST['addr'];
	$user = $_POST['name'];

	$db = new mysqli("localhost", "snadmin", "snadmin*", "sensornetworks");

	if($db->connect_error){
		die("error");
	}
	
	
	$query_string1 = "delete from sp14_jyu_whitelist where addr = '$address'";
	$query_string2 = "delete from sp14_jyu_whitelist where name = '$user'";

	$db->query($query_string1);
	$db->query($query_string2);
	echo "successfully removed user from the list!";
	
	
	
	
	


?>
</body>
</html>