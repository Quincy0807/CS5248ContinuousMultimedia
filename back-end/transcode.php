<?php
function doTranscode($filePath,$duration,$isEnd){
    exec("yes y | /usr/local/bin/ffmpeg -i $filePath -s 720x480 -b:v 3m -c:v libx264 -c:a libfaac " . "./video_repo/high/" . basename($filePath) . ".ts");
    addToPlayList("high",$filePath,$duration,$isEnd);
    exec("yes y | /usr/local/bin/ffmpeg -i $filePath -s 480x320 -b:v 768k -c:v libx264 -c:a libfaac " . "./video_repo/medium/" . basename($filePath) . ".ts");
    addToPlayList("medium",$filePath,$duration,$isEnd);
    exec("yes y | /usr/local/bin/ffmpeg -i $filePath -s 240x160 -b:v 200k -c:v libx264 -c:a libfaac " . "./video_repo/low/" . basename($filePath) . ".ts");
    addToPlayList("low",$filePath,$duration,$isEnd);
    exec("yes y | /usr/local/bin/ffmpeg -i $filePath -s 720x480 -b:v 3m -c:v libx264 -c:a libfaac " . "./video_repo/high/" . basename($filePath) . ".mp4");
    exec("yes y | /usr/local/bin/ffmpeg -i $filePath -s 480x320 -b:v 768k -c:v libx264 -c:a libfaac " . "./video_repo/medium/" . basename($filePath) . ".mp4");
    exec("yes y | /usr/local/bin/ffmpeg -i $filePath -s 240x160 -b:v 200k -c:v libx264 -c:a libfaac " . "./video_repo/low/" . basename($filePath) . ".mp4");
    exec("rm -rf $filePath");
    return true;
}



function addToPlayList($quality,$filePath,$duration,$isEnd){
    $segmentBaseName=basename($filePath);
    $playListBaseName=substr($segmentBaseName,0,strrpos($segmentBaseName,"_"));
    $playListFile="./playlist/" . $quality . "/" . $playListBaseName . ".m3u8";
    if(file_exists($playListFile)){
        $currentContent=file_get_contents($playListFile);
    }else{
        $currentContent="#EXTM3U\n#EXT-X-PLAYLIST-TYPE:EVENT\n#EXT-X-VERSION:4\n#EXT-X-TARGETDURATION:10\n#EXT-X-MEDIA-SEQUENCE:0\n";
    }
    $currentContent .= "#EXT-X-DISCONTINUITY\n#EXTINF:$duration,\n" . "http://pilatus.d1.comp.nus.edu.sg/~team01/video_repo/$quality/" . basename($filePath) . ".ts\n";
    if($isEnd==1){
        $currentContent .= "#EXT-X-ENDLIST\n";
    }
    file_put_contents($playListFile,$currentContent);
}