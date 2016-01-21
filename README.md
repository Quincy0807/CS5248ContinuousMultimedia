# The Andorid code for DASH application
css/bootstrap.min.css   : the stylesheet file of bootstrap which is used in our webpage.
css/hlsCss.css          : our own stylesheet file.
js/bootstrap.min.js     : the javascript file of bootstrap which is used in our webpage.
js/hlsjs.js             : our own javascript file.
js/jquery-1.11.3.min.js : the jquery file which is used in our webpage.
src/                    : the source code of our Android app.
Client-debug.apk        : the apk file of our Android app.
helper.php              : defines some constant and wraps the basic databse operation.
hls.html                : the playback webpage for HLS.
prepare.php             : returns the next mp4 url and the state(playable/waiting/playable_with_end), for live stream playback.
transcode.php           : transcodes the segment after it is uploaded.
upload.php              : opens a new session and receives the uploading segment.
watch.php               : returns the playlists.
