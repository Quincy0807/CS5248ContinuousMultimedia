<?php
require("helper.php");


$queryString=$_SERVER["QUERY_STRING"];
$strings=explode("_",$queryString);
$quality=$strings[0];
$sessionId=$strings[1];
$segmentId=$strings[2];

doSQL(function($con)use(&$quality,&$sessionId,&$segmentId){

    $result=mysql_query("select isEnd from Segment where sessionId=$sessionId and segmentId= $segmentId");
    if(!$result){
        echo json_encode(array('exchangeDataState'=>constant("WAITING")));
    }else{
        if(mysql_num_rows($result)==0){
            echo json_encode(array('exchangeDataState'=>constant("WAITING")));
        }else{
            $mp4URL="http://pilatus.d1.comp.nus.edu.sg/~team01/video_repo/$quality/session-$sessionId.mp4_$segmentId.mp4";
            $result=mysql_fetch_array($result);
            if($result["isEnd"]==0){
                echo json_encode(array("exchangeDataState"=>constant("PLAYABLE"),"mp4URL"=>$mp4URL));
            }else{
                echo json_encode(array("exchangeDataState"=>constant("PLAYABLE_WITH_END"),"mp4URL"=>$mp4URL));
            }
        }
    }
});