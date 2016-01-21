package dash.network;

import java.util.List;

/**
 * Created by Quincy on 15/9/24.
 */
public class ExchangeData {
    private int sessionId;
    private long duration;
    private long timeScale;
    private double lengthInSecond;
    private int exchangeDataState;
    private int segmentId;
    private int isEnd;
    private String mp4URL;
    private List<RemoteSessionInfo> vodSessions;
    private List<RemoteSessionInfo> liveSessions;


    public class RemoteSessionInfo {
        private String title;
        private String mediaPath;
        private int sessionId;

        public String getMediaPath() {
            return mediaPath;
        }

        public void setMediaPath(String mediaPath) {
            this.mediaPath = mediaPath;
        }

        public int getSessionId() {
            return sessionId;
        }

        public void setSessionId(int sessionId) {
            this.sessionId = sessionId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }



    public String getMp4URL() {
        return mp4URL;
    }

    public void setMp4URL(String mp4URL) {
        this.mp4URL = mp4URL;
    }
    public List<RemoteSessionInfo> getVodSessions() {
        return vodSessions;
    }

    public void setVodSessions(List<RemoteSessionInfo> vodSessions) {
        this.vodSessions = vodSessions;
    }


    public ExchangeData() {
    }

    public ExchangeData addTimeScale(long timeScale) {
        this.timeScale = timeScale;
        return this;
    }


    public ExchangeData addDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public ExchangeData addLengthInSecond(double lengthInSecond) {
        this.lengthInSecond = lengthInSecond;
        return this;
    }

    public ExchangeData addSessionId(int sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public ExchangeData addSegmentId(int segmentId) {
        this.segmentId = segmentId;
        return this;
    }


    public ExchangeData addIsEnd(int isEnd) {
        this.isEnd = isEnd;
        return this;
    }


    public ExchangeData addExchangeDataState(ExchangeDataState exchangeDataState) {
        this.exchangeDataState = exchangeDataState.mappingInt();
        return this;
    }


    public int getExchangeDataState() {
        return exchangeDataState;
    }


    public int isEnd() {
        return isEnd;
    }


    public void setExchangeDataState(int exchangeDataState) {
        this.exchangeDataState = exchangeDataState;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }


    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public long getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(long timeScale) {
        this.timeScale = timeScale;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getLengthInSecond() {
        return lengthInSecond;
    }

    public void setLengthInSecond(double lengthInSecond) {
        this.lengthInSecond = lengthInSecond;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public List<RemoteSessionInfo> getLiveSessions() {
        return liveSessions;
    }

    public void setLiveSessions(List<RemoteSessionInfo> liveSessions) {
        this.liveSessions = liveSessions;
    }


    //    private String movieName;
//
//    private double movieDuration;
//    private double segmentDuration;
//    private int segmentNumber;
//    private int continueNumber;
//    private int segmentOrder;
//
//
//    private int movieIndex;
//    private String segmentName;
//    private List<String> vodSessions;


//    public ExchangeData addMovieName(String movieName) {
//        this.movieName = movieName;
//        return this;
//    }
//
//    public ExchangeData addMovieDuration(double movieDuration) {
//        this.movieDuration = movieDuration;
//        return this;
//    }
//
//    public ExchangeData addSegmentDuration(double segmentDuration) {
//        this.segmentDuration = segmentDuration;
//        return this;
//    }


    //    public ExchangeData addSegmentOrder(int segmentOrder) {
//        this.segmentOrder = segmentOrder;
//        return this;
//    }
//
//    public ExchangeData addSegmentNumber(int segmentNumber) {
//        this.segmentNumber = segmentNumber;
//        return this;
//    }
//
//    public ExchangeData addMovieIndex(int movieIndex) {
//        this.movieIndex = movieIndex;
//        return this;
//    }


    //    public ExchangeData addSegmentName(String segmentName) {
//        this.segmentName = segmentName;
//        return this;
//    }
//
//    public int getMovieIndex() {
//        return movieIndex;
//    }
//
//    public int getSegmentOrder() {
//        return segmentOrder;
//    }


//    public int getContinueNumber() {
//        return continueNumber;
//    }


    //    public double getMovieDuration() {
//        return movieDuration;
//    }
//
//    public double getSegmentDuration() {
//        return segmentDuration;
//    }
//
//    public String getMovieName() {
//        return movieName;
//    }

//    public void setContinueNumber(int continueNumber) {
//        this.continueNumber = continueNumber;
//    }


    //    public void setMovieDuration(double movieDuration) {
//        this.movieDuration = movieDuration;
//    }
//
//    public void setSegmentDuration(double segmentDuration) {
//        this.segmentDuration = segmentDuration;
//    }
//
//    public void setMovieName(String movieName) {
//        this.movieName = movieName;
//    }
//
//    public void setMovieIndex(int movieIndex) {
//        this.movieIndex = movieIndex;
//    }

//    public List<String> getVodSessions() {
//        return vodSessions;
//    }

//    public void setVodSessions(List<String> vodSessions) {
//
//        this.vodSessions = vodSessions;
//    }


    //    public String getSegmentName() {
//        return segmentName;
//    }
//
//    public void setSegmentName(String segmentName) {
//        this.segmentName = segmentName;
//    }

}

