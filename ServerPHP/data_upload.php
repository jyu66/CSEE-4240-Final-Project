
<?php
	echo "Reached PHP data uploader!\n";
	
	//connect to database
	$db=new mysqli("localhost","snadmin","snadmin*","sensornetworks");
	if($db->connect_error){
		die("Error Connecting to Database");
	}
	
	$radio_addr=$_REQUEST["radioaddr"];
	$air_Temp=$_REQUEST["airTemp"];
	$body_Temp=$_REQUEST["bodyTemp"];
	$force_Read = $_REQUEST["forceRead"];
	$password_id=$_REQUEST["pw"];
	
	if($password_id=='friend'){
		
		$query_string ="insert into sp14_jyu_datalog (id,radioaddr,airTemp,bodyTemp,forceVal,dateandtime) values ('','$radio_addr','$air_Temp','$body_Temp','$force_Read',NOW())";
		if (mysqli_query($db,$query_string)){
			echo "Data inserted to Database table successfully";
		}
		else {
			echo "Error inserting data to table:" . mysqli_error($db);
		}
	}
	else{
		echo "Invalid Password. Please try again.";
	}
?>