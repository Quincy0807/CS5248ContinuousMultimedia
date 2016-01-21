$(document).ready(function () {

    var itemColor = ["info", "success", "warning", "danger"];
    var mediaPaths={};

    $("table").on("click", ".movieItem", (function () {
        var currentName = $(this).text();
        if (!currentName.match("^session-\\d+")) {
            return
        }
     /*   $.ajax({
            url: "http://pilatus.d1.comp.nus.edu.sg/~team01/watch.php",
            method: "post",
            data: {json: JSON.stringify({exchangeDataState: 7, playlist: currentName})},
            success: function (m3u8url) {
                var playerDOM = $("#videoPlayer").get(0);
                playerDOM.src = m3u8url;
                playerDOM.play();
            },
            fail: function () {
                alert(" Error occurs during connecting to the server");
            }
        });*/
	var playerDOM= $("#videoPlayer").get(0);
	playerDOM.src=mediaPaths[currentName];
	playerDOM.play();
    }));

    $.ajax({
        url: "http://pilatus.d1.comp.nus.edu.sg/~team01/watch.php",
        method: "post",
        data: {json: JSON.stringify({exchangeDataState: 7})},
        success: function (data) {
            var retJson = $.parseJSON(data);
           // if (retJson["exchangeDataState"] == 6) {
                var movieNames = $("#movieNames");
                var movies = retJson["sessionsInServer"];
                if (movies.length == 0) {
                    movieNames.append("<tr class='danger'><td><b>here is not video available now.</b></td></tr>");
                    return
                }
                $("table").prepend("<thead><tr class='active'><th>#</th><th>Session</th><th>Timestamp</th></tr></thead>");
                $.each(movies, function (i, v) {
                    movieNames.append("<tr class=" + itemColor[i % 4] + "><td>" + (i * 1 + 1) + "</td><td class='movieItem'>" + "session-"+v["sessionId"] + "</td><td>"+v["title"]+"</td></tr>");
		mediaPaths["session-"+v["sessionId"]]=v["mediaPath"];
                })
           /* }
            else {
                alert(" Error occurs during connecting to the server");
            }*/
        },
        fail: function () {
            alert(" Error occurs during connecting to the server");
        }
    });
});


