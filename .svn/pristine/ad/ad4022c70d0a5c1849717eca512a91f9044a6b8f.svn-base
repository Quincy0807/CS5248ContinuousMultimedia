package dash.network;

/**
 * Created by Quincy on 15/9/27.
 */
public enum ExchangeDataState {
    REQUEST_UPLOAD_SESSION_ID(-2),
    RESPONSE_UPLOAD_SESSION_ID(-1),
    REQUEST_UPLOAD_NUMBER(0),
    RESPONSE_UPLOAD_NUMBER(1),
    REQUEST_TO_UPLOAD(2),
    RESPONSE_UPLOAD_SUCCESS(3),
    RESPONSE_UPLOAD_FAIL(4),
    REQUEST_RETRIEVE_MOVIES(5),
    RESPONSE_EXTRIEVE_MOVIES(6),
    REQUEST_PLAYLIST_ANDROID(8),
    PLAYABLE(9),
    WAITING(10),
    PLAYABLE_WITH_END(11),
    ;
    private int shadowInt;

    ExchangeDataState(int shadowInt) {
        this.shadowInt = shadowInt;
    }

    public int mappingInt() {
        return shadowInt;
    }
}
