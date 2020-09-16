<?php
	$DB_NAME = "db_suryapro";
	$DB_USER = "root";
	$DB_PASS = "";
	$DB_SERVER_LOC = "db_testmkm2020"; 

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$conn = mysqli_connect($DB_SERVER_LOC, $DB_USER, $DB_PASS, $DB_NAME);

		$respon = array(); $respon['kode'] = '000';

				$USERNAME = $_POST['username'];
				$LOGINTIME = $_POST['logintime'];
				$LOGINSTATE = $_POST['loginstate'];

						$sql ="UPDATE user SET login_time = '$LOGINTIME',login_state = '$LOGINSTATE' WHERE username = '$USERNAME'";

						$result = mysqli_query($conn,$sql);
						if($result){
							echo json_encode($respon);
							exit(); //insert data sukses semua
						}else{
							$respon['kode'] = "111";
							echo json_encode($respon);
							exit();
						}				

		}
?>
