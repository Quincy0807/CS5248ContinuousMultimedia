<?php
require("helper.php");
require("transcode.php");

$request=json_decode($_POST["json"]);

switch($request->{"exchangeDataState"}){
    case constant("REQUEST_UPLOAD_SESSION_ID"):
        doSQL(function($con) use(&$request){
            mysql_query("INSERT INTO Session values ()");
            $result=mysql_query("SELECT sessionId FROM Session order by sessionId DESC limit 1");
            //	if(mysql_num_rows($result)!=0){
            $result=mysql_fetch_array($result);
            $sessionId=$result["sessionId"];
            //	}else{
            //		mysql_query("INSERT INTO Session values ()");
// 	$sessionId=0;
            //	}
            echo json_encode(array('exchangeDataState'=>constant('RESPONSE_UPLOAD_SESSION_ID'),'sessionId'=>$sessionId));
        });
        break;
    case constant("REQUEST_TO_UPLOAD"):
        $target_dir="./video_repo/";
        $upload_file=$_FILES["uploadFile"];
        //$target_path = $target_dir . basename($request->{"segmentName"});
        $file_name = "session-".$request->{'sessionId'}.".mp4_". $request->{'segmentId'};
        $target_path = $target_dir . $file_name;
        if(move_uploaded_file($upload_file["tmp_name"],$target_path)){
            doSQL(function($con) use(&$request,&$target_path,&$upload_file,&$file_name){
                $result = mysql_query("INSERT INTO Segment(segmentId,sessionId,path,duration,timeScale,lengthInSecond,isEnd) VALUES('".$request->{"segmentId"}."','".$request->{"sessionId"}."','".$file_name."','".$request->{"duration"}."','".$request->{"timeScale"}."','".$request->{"lengthInSecond"}."','".$request->{"isEnd"}."')");
                if(!$result){
                    echo json_encode(array('exchangeDataState'=>constant("RESPONSE_UPLOAD_FAIL")));
                }else{
                    doTranscode($target_path,$request->{"lengthInSecond"}, $request->{"isEnd"});
                    if($request->{"isEnd"}==1){
                        mysql_query("UPDATE Session set isEnd=1 where sessionId = " . $request->{"sessionId"});
                    }
                    echo json_encode(array('exchangeDataState'=>constant("RESPONSE_UPLOAD_SUCCESS")));
                }
            });
        }else{
            echo json_encode(array('exchangeDataState'=>constant("RESPONSE_UPLOAD_FAIL")));
        }

        break;

    default:
        echo "error";
}