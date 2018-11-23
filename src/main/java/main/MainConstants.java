package main;

/**
 * Main 클래스에서 사용할 상수들을 모아놓은 클래스
 */
public class MainConstants {

    static final int MAX_PORT = 49152;

    static final String ERR_NO_PORT_NUMBER = "update Server is required port number";
    static final String ERR_WRONG_PORT_NUMBER = "update Server port number is wrong";
    static final String ERR_IMPOSSIBLE_PORT = "update Server port number must set 1 ~ 49151";
    static final String ERR_SERVER_START_FAILED = "Server start failed. check stack trace";
    static final String ERR_SERVER_STOP_FAILED = "Stop Failed! check the stack trace";
    static final String ERR_SERVER_IO_FAILED = "Server I/O Failed. check stack trace";
    static final String ERR_SERVER_STREAM_CLOSE_FAILED = "Stream close failed! check stack trace";
    static final String ERR_RESOURCE_CLEAN_FAILED = "resource clean failed!";

    static final String MSG_UPDATE_INFO_MANAGER_STOP = "update Info Manager stop";
    static final String MSG_SERVER_LOADED = "Server loaded.";
    static final String MSG_SERVER_STOP = "server stop.";
    static final String MSG_SERVER_BUSY = " server is busy now";

    /**
     * 로그를 찍을때 클라이언트 요청에 대한 상태를 나타내는 열거형
     * <p>
     * CONNECT : 클라이언트 와 연결이 성공 하였을 때 사용하는 값
     * SEND : 클라이언트 에 데이터를 전송 할 때 사용하는 값
     * SEND_COMPLETE : 클라이언트 에 데이터 전송을 완료 했을 때 사용하는 값
     * DISCONNECT : 클라이언트 와 연결 이 종료 되었을 때 사용하는 값
     */
    public enum CLIENT_STATUS {
        CONNECT,
        SEND,
        SEND_COMPLETE,
        DISCONNECT
    }

    /**
     * 클라이언트 와 서버간의 통신 시 어떤 요청인지를 판단할때 사용되는 클래스
     * UPDATE_INFO_TREE : UpdateInfoTree 를 요청하는 경우
     * REQUEST_DOWNLOAD_DTO : DownloadRequestDTO 를 서버에서 받고 FileResponses 를 클라이언트에서 받는 요청을 하는 경우
     */
    public class RequestProtocol {

        public static final String UPDATE_INFO_TREE = "updateInfoDTO";
        public static final String REQUEST_DOWNLOAD_DTO = "downloadRequestDTO";

    }
}
