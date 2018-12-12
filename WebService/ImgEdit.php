<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"id"}) && isset($data_back->{"img"}))
	{
		if(!empty($data_back->{"id"}) && !empty($data_back->{"img"}))
		{
			$id=$data_back->{"id"};
			
			$data=$data_back->{"img"};
			$data = str_replace('data:image/png;base64,', '',$data);
			$data = base64_decode($data);
			$file = 'img/'. uniqid() . '.jpeg';
			$success = file_put_contents($file, $data);
			
			echo $q_dp="update tblperson set img = '".$file."' where id = '".$id."'";
			
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