<?php
require("helper.php");


function retM3U8($name){
    $target_file = "$name.m3u8";
    if (file_exists("playlist/m3u8/" . $target_file)){
        return "http://pilatus.d1.comp.nus.edu.sg/~team01/playlist/m3u8/" . $target_file;
    }else{
        $variantPlayList="#EXTM3U\n#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=3000000,RESOLUTION=720x480,CODECS=\"avc1.42e00a,mp4a.40.2\"\nhttp://pilatus.d1.comp.nus.edu.sg/~team01/playlist/high/$target_file\n#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=768000,RESOLUTION=480x320,CODECS=\"avc1.42e00a,mp4a.40.2\"\nhttp://pilatus.d1.comp.nus.edu.sg/~team01/playlist/medium/$target_file\n#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=200000,RESOLUTION=240x160,CODECS=\"avc1.42e00a,mp4a.40.2\"\nhttp://pilatus.d1.comp.nus.edu.sg/~team01/playlist/low/$target_file\n";
        file_put_contents("playlist/m3u8/".$target_file,$variantPlayList);
        return "http://pilatus.d1.comp.nus.edu.sg/~team01/playlist/m3u8/" . $target_file;
    }

}

function retMPD($name,$sessionId){
    $target_file = "$name.mpd";
    if (file_exists("playlist/mpd/" . $target_file)){
        return "http://pilatus.d1.comp.nus.edu.sg/~team01/playlist/mpd/" . $target_file;
    }else{
        $text="<?xml version='1.0' encoding='UTF-8'?>\n<MPD\n
        xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n
        xmlns='urn:mpeg:dash:schema:mpd:2011'\n
        xmlns:xlink='http://www.w3.org/1999/xlink'\n
        xsi:schemaLocation='urn:mpeg:dash:schema:mpd:2011 http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-DASH_schema_files/DASH-MPD.xsd'\n
        minBufferTime='PT10.00S'\n
        mediaPresentationDuration='PT3256S'\n
        type='static' availabilityStartTime='2001-12-17T09:40:57Z'\n
        profiles='urn:mpeg:dash:profile:isoff-main:2011'>\n
    <Period start='PT0S' id='1'>\n
        <AdaptationSet mimeType='video/mp4'>\n
            <Representation id='high'  width='720' height='480'
                            bandwidth='3000000'>\n
                <SegmentTemplate timescale='1000' duration='3360' media='pilatus.d1.comp.nus.edu.sg/~team01/prepare.php?high_". $sessionId . "_\$Number\$'/>\n
            </Representation>\n
            <Representation  id='medium' width='480' height='320'
                             bandwidth='768000'>\n
                <SegmentTemplate timescale='1000' duration='3360' media='pilatus.d1.comp.nus.edu.sg/~team01/prepare.php?medium_" . $sessionId ."_\$Number\$'/>\n
            </Representation>\n
            <Representation  id='low' width='240' height='160'
                             bandwidth='200000'>\n
                <SegmentTemplate timescale='1000' duration='3360' media='pilatus.d1.comp.nus.edu.sg/~team01/prepare.php?low_" . $sessionId . "_\$Number\$'/>\n
            </Representation>\n
        </AdaptationSet>\n
    </Period>\n
</MPD>\n";
        file_put_contents("playlist/mpd/" . $target_file, $text);
        return "http://pilatus.d1.comp.nus.edu.sg/~team01/playlist/mpd/" . $target_file;
    }
}


$request=json_decode($_POST["json"]);
switch($request->{"exchangeDataState"}){
    case constant("REQUEST_PLAYLIST_APPLE"):
        doSQL(function($con){
            $result=mysql_query("select sessionId,title from Session");
            $sessions=array();
            $index=0;
            if(!$result){
            }else{
                while($row=mysql_fetch_array($result)){
                    $sessions[$index]=array("sessionId"=>$row["sessionId"],"title"=>$row["title"],"mediaPath"=>retM3U8("session-" . $row["sessionId"] . ".mp4"));
                    $index++;
                };
            }
            echo json_encode(array("exchangeDataState"=>constant("RESPONSE_RETRIEVE_MOVIES"),"sessionsInServer"=>$sessions));
        });
        break;
    case constant("REQUEST_PLAYLIST_ANDROID"):
        doSQL(function($con){
            $result=mysql_query("select sessionId,title,isEnd from Session");
            $vodSessions=array();
            $liveSessions=array();
            $vodIndex=0;
            $liveIndex=0;
            if(!$result){
            }else{
                while($row=mysql_fetch_array($result)){
                    if($row["isEnd"]==1){
                        $vodSessions[$vodIndex]=array("sessionId"=>$row["sessionId"],"title"=>$row["title"],"mediaPath"=>retMPD("session-" . $row["sessionId"] . ".mp4",$row["sessionId"]));
                        $vodIndex++;
                    }else{
                        $liveSessions[$liveIndex]=array("sessionId"=>$row["sessionId"],"title"=>$row["title"],"mediaPath"=>retMPD("session-" . $row["sessionId"] . ".mp4", $row["sessionId"]));
                        $liveIndex++;
                    }
                };
            }
            echo json_encode(array("exchangeDataState"=>constant("RESPONSE_RETRIEVE_MOVIES"),"vodSessions"=>$vodSessions,"liveSessions"=>$liveSessions));
        });
        break;

}
