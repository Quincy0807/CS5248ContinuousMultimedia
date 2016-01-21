<?php

define("REQUEST_UPLOAD_SESSION_ID",-2);
define("RESPONSE_UPLOAD_SESSION_ID",-1);
//define("REQUEST_UPLOAD_NUMBER",0);
//define("RESPONSE_UPLOAD_NUMBER",1);
define("REQUEST_TO_UPLOAD",2);
define("RESPONSE_UPLOAD_SUCCESS",3);
define("RESPONSE_UPLOAD_FAIL",4);
//define("REQUEST_RETRIEVE_MOVIES",5);
define("RESPONSE_RETRIEVE_MOVIES",6);
define("REQUEST_PLAYLIST_APPLE",7);
define("REQUEST_PLAYLIST_ANDROID",8);
define("PLAYABLE",9);
define("WAITING",10);
define("PLAYABLE_WITH_END",11);
ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(-1);

function doSQL($funcAction){
    $con=mysql_connect("localhost","team01_15","uWVDuMZZzZcrazY");
    if(!$con){
        echo "error";
    }
    else{
        mysql_select_db("team01_15_db",$con);
        $funcAction($con);
        mysql_close($con);
    }

}
?>