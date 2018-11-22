package main;

/**
 * Main 클래스에서 사용할 상수들을 모아놓은 클래스
 * <p>
 * TIME_OUT : 요청에 대한 Time out 을 나타 낸다.
 * MAX_PORT : 서버의 할당 가능한 제일 높은 숫자의 포트 이다.
 * UPDATE_DELAY : 로컬 업데이트에 대한 DELAY 값 으로 밀리 세컨드로 표기 한다.
 * STOP_PORT : 종료 요청을 받는 포트 번호 이다.
 * ERR_NO_PORT_NUMBER : 서버 시작 시 매개 변수로 포트 번호가 넘어오지 않았을때 출력할 문자열 이다.
 * ERR_WRONG_PORT_NUMBER : 서버 시작 시 매개 변수로 포트 번호가 이상한 값으로 넘어왔을때 출력할 문자열 이다.
 * ERR_IMPOSSIBLE_PORT : 서버 시작 시 매개 변수로 1 ~ MAX_PORT 포트 번호가 아닌 번호가 넘어왔을때 출력할 문자열 이다.
 * ERR_SERVER_START_FAILED : 오류로 인해 서버 시작에 실패 했을 경우 출력할 문자열 이다.
 * ERR_SERVER_STOP_FAILED : 오류로 인해 서버를 정상적으로 종료하지 못했을 경우 출력할 문자열 이다.
 * ERR_SERVER_IO_FAILED : 오류로 인해 네트워크 I/O 가 정상적으로 이뤄지지 않았을 경우 출력할 문자열 이다.
 * ERR_SERVER_STREAM_CLOSE_FAILED : 오류로 인해 통신 시 사용한 Stream 들이 close 되지 않았을 경우 출력할 문자열 이다.
 * ERR_RESOURCE_CLEAN_FAILED : 오류로 인해 서버 종료 시 서버에서 사용하던 리소스 정리가 재대로 되지 않을 경우 출력할 문자열 이다.
 * <p>
 * MSG_UPDATE_INFO_MANAGER_STOP : UpdateInfoManager 가 Stop 될 경우 출력할 문자열이다.
 * MSG_SERVER_LOADED : 서버 로딩이 완료 되었을 때 출력할 문자열이다.
 * MSG_SERVER_STOP : 서버가 종료되면 출력 될 문자열이다.
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
     * REQUEST_DOWNLOAD_DTO : DownloadRequestDTO 를 서버에서 받고 FileResponseDTO 를 클라이언트에서 받는 요청을 하는 경우
     */
    class RequestProtocol {

        static final String UPDATE_INFO_TREE = "updateInfoDTO";
        static final String REQUEST_DOWNLOAD_DTO = "downloadRequestDTO";

    }
}
