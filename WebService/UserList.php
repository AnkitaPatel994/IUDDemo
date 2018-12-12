<?php 

	include "config.php";
	
	$data_back = json_decode(file_get_contents('php://input'));	
	
	$details=array();
	
	$query = "select id,name,gender,hobby,dob,img from tblperson ORDER BY id DESC";
	$stm = $conn->prepare($query);
												
	if ($stm)
	{
		$stm->execute();
		$stm->bind_result($id,$name,$gender,$hobby,$dob,$img);
		$stm->store_result();
		$count1=$stm->num_rows;
		
		if($count1!=0){			
		
		while($stm->fetch())
		{
			$User[]=array('id'=>"$id",'name'=>"$name",'gender'=>"$gender",'hobby'=>"$hobby",'dob'=>"$dob",'img'=>"$img");
		}
		
		$details = array('status'=>"1",'message'=>"Success",'User'=>$User);
	
		}else{
			$details = array('status'=>"0",'message'=>"No Category Found");
		}
	}
	else 
	{
		$details = array('status'=>"0",'message'=>"connection error exist");
	}
	$stm->close();
	
		
	echo json_encode($details);
	$conn->close();
	
	
?>