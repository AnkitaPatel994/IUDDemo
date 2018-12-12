<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"name"}) && isset($data_back->{"gender"}) && isset($data_back->{"hobby"}) && isset($data_back->{"dob"}) && isset($data_back->{"img"}))
	{
		if(!empty($data_back->{"name"}) && !empty($data_back->{"gender"}) && !empty($data_back->{"hobby"}) && !empty($data_back->{"dob"}) && !empty($data_back->{"img"}))
		{
			$name=$data_back->{"name"};
			$gender=$data_back->{"gender"};
			$hobby=$data_back->{"hobby"};
			$dob=$data_back->{"dob"};
			
			$data=$data_back->{"img"};
			$data = str_replace('data:image/png;base64,', '',$data);
			$data = base64_decode($data);
			$file = 'img/'. uniqid() . '.jpeg';
			$success = file_put_contents($file, $data);
			
			$q_dp="insert into tblperson(name,gender,hobby,dob,img) values(?,?,?,?,?)";
			$stdp=$conn->prepare($q_dp);
			
			if($stdp)
			{
				$stdp->bind_param('sssss',$name,$gender,$hobby,$dob,$file);
				$stdp->execute();
				$add_id=$stdp->insert_id;
				$details = array('status'=>"1",'message'=>"success", 'id'=>$add_id);
			}
		}
		else
			$details = array('status'=>"0",'message'=>"Parameter is Empty");
	}
	else
		$details = array('status'=>"0",'message'=>"parameter missing");
	
	echo json_encode($details);
	
	$conn->close();
?>