<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"id"}))
	{
		if(!empty($data_back->{"id"}))
		{
			$id=$data_back->{"id"};
			
			$q_dp="delete from tblperson where id = '".$id."'";
			$stdp=$conn->prepare($q_dp);
			$stdp->execute();
			$details = array('status'=>"1",'message'=>"success");
			/*if($stdp)
			{
				$stdp->bind_param('sssss',$name,$gender,$hobby,$dob,$file);
				$stdp->execute();
				$add_id=$stdp->insert_id;
				$details = array('status'=>"1",'message'=>"success", 'id'=>$add_id);
			}*/
		}
		else
			$details = array('status'=>"0",'message'=>"Parameter is Empty");
	}
	else
		$details = array('status'=>"0",'message'=>"parameter missing");
	
	echo json_encode($details);
	
	$conn->close();
?>