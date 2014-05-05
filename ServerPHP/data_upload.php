
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
	$motion = $_REQUEST["hasmotion"];
	$password_id=$_REQUEST["pw"];
	
	echo "motion= ".$motion;
	
	if($motion==0){
		$hasMotion = "Motion Detected";
	}
	else
		$hasMotion = "No Motion Detected";
	
	if($password_id=='friend'){
		
		$query_string ="insert into sp14_jyu_datalog (id,radioaddr,airTemp,bodyTemp,forceVal,dateandtime,hasmotion) values ('','$radio_addr','$air_Temp','$body_Temp','$force_Read',NOW(),'$hasMotion')";
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