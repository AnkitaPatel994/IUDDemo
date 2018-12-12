<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"id"}) && isset($data_back->{"name"}) && isset($data_back->{"gender"}) && isset($data_back->{"hobby"}) && isset($data_back->{"dob"}))
	{
		if(!empty($data_back->{"id"}) && !empty($data_back->{"name"}) && !empty($data_back->{"gender"}) && !empty($data_back->{"hobby"}) && !empty($data_back->{"dob"}))
		{
			$id=$data_back->{"id"};
			$name=$data_back->{"name"};
			$gender=$data_back->{"gender"};
			$hobby=$data_back->{"hobby"};
			$dob=$data_back->{"dob"};
			
			echo $q_dp="update tblperson set name = '".$name."', gender = '".$gender."', hobby = '".$hobby."', dob = '".$dob."' where id = '".$id."'";
			
			$stdp=$conn->prepare($q_dp);
			$stdp->execute();
			$details = array('status'=>"1",'message'=>"success");

		}
		else
			$details = array('status'=>"0",'message'=>"Parameter is Empty");
	}
	else
		$details = array('status'=>"0",'message'=>"parameter missing");
	
	echo json_encode($details);
	
	$conn->close();
?>