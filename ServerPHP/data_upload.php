
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
	
	
	//detecting motion
	if($motion==0){
		$hasMotion = "Motion Detected";
	}
	else
		$hasMotion = "No Motion Detected";
	

//determine whether or not soldier has been killed	
	if ($body_Temp==$air_Temp){
		$deadalive = "dead";
	}
	else{
		$deadalive = "alive";
	}
	
	//determining whether or not soldier is under fire
	if ($force_Read>150){
		$underfire = "UNDER FIRE!";
	}
	else{
		$underfire = "Normal Status";
	}
	
	
	
	//send sql request
	if($password_id=='friend'){
		
		$query_string ="insert into sp14_jyu_datalog (id,radioaddr,airTemp,bodyTemp,forceVal,dateandtime,hasmotion,deadalive,takingfire) values ('','$radio_addr','$air_Temp','$body_Temp','$force_Read',NOW(),'$hasMotion','$deadalive','$underfire')";
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