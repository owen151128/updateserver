package main;

/**
 * Main 클래스에서 사용할 상수들을 모아놓은 클래스
 */
public class MainConstants {

    /**
     * 로그를 찍을때 클라이언트 요청에 대한 상태를 나타내는 열거형
     *
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
}
