<?php
	$db = new mysqli ("localhost", "snadmin", "snadmin*", "sensornetworks");
	if ($db->connect_error){
		die("Errorconnecting to database");
	}
	
	$pw = $_REQUEST["pw"];
	
	if($pw=='friend'){
		$query_string = "select * from sp14_jyu_whitelist";
		$res = $db->query($query_string);
	
		$toReturn = array();
		$toReturn["success"]=false;
		$toReturn["data"]=array();
	
		while($row=$res->fetch_assoc()){
			$toReturn["data"][]=$row;
		}
		$toReturn["success"]=true;
	
		echo json_encode($toReturn);
	}
	else{
		echo "invalid password";
	}











?>