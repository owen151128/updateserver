package util;

import main.MainConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 서버 로그 와 관련된 일들을 처리하는 클래스
 */
public class LogUtil {

    private static final String TIME_INFO = "yy-MM-dd a hh:mm:ss";
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";

    private static final String LOG_FILE_PATH = "." + File.separator + "update.log";

    private static final String ERR_LOG_FILE_CREATE_FAILED = "log file create failed. check stack trace";
    private static final String ERR_LOG_FILE_NOT_FOUND = "log file not found. check stack trace";
    private static final String ERR_LOG_WRITE_FAILED = "log write failed. check stack trace";
    private static final String ERR_LOG_UTIL_CLOSE_FAILED = "log file Stream close failed. check stack trace";

    private static final String MSG_CONNECTED = " connected";
    private static final String MSG_REQUEST = " request : ";
    private static final String MSG_SEND_DATA = " send data...";
    private static final String MSG_SEND_COMPLETE = " send complete";
    private static final String MSG_DISCONNECTED = " disconnected";
    private static final String MSG_LOG_FILE_CREATED = "log file created : ";

    private FileWriter fw;

    private SimpleDateFormat timeStamp;

    /**
     * 기본 생성자로 로그 파일을 불러오는 역활을 담당한다.
     * 로그 파일이 없을경우 생성 한다.
     * 로그 파일은 실행 경로의 update.log 로 생성 된다.
     */
    public LogUtil() {

        File logFile = new File(LOG_FILE_PATH);

        try {

            if (!logFile.isFile()) {

                if (logFile.createNewFile()) {

                    System.out.println(MSG_LOG_FILE_CREATED + logFile.getAbsolutePath());

                }

            }

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_FILE_CREATE_FAILED);

        }

        try {

            fw = new FileWriter(logFile, true);
            timeStamp = new SimpleDateFormat(TIME_INFO);

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_FILE_NOT_FOUND);

        }
    }

    /**
     * 현재 시간을 얻어와서 타임 스탬프로 만들어주는 메소드 이다.
     *
     * @return String 형태의 타임 스탬프
     */
    private String getTime() {

        return timeStamp.format(System.currentTimeMillis()) + SPACE;

    }

    /**
     * 로그를 쓰는 함수이다. 대상은 isSave 값에 따라 다르게 쓰기를 진행 한다.
     *
     * @param clientIP String 형태의 클라이언트 IP 이다. 로그에 클라이언트 를 구분하기 위해서 사용 된다.
     * @param message  String 형태의 message 로 찍어야할 로그가 있을때 사용자 정의 문자열을 입력 하여 사용 한다.
     * @param isSave   boolean 형태로 로그를 update.log 파일에 저장할지 여부이다.
     *                 true 일 경우 파일에 저장하고 콘솔에 출력 하며
     *                 false 일 경우 파일에 저장하지 않고 콘솔에 출력만 한다.
     */
    public void writeLog(String clientIP, String message, boolean isSave) {

        String result = getTime() + clientIP + message;

        try {

            System.out.println(result);

            if (isSave) {

                fw.write(result + NEW_LINE);
                fw.flush();

            }

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_WRITE_FAILED);

        }
    }

    /**
     * 로그를 쓰는 함수이다. 클라이언트 IP 가 필요 없는 로그를 저장할때 사용 한다. ex) 서버 상태 관련 로그
     *
     * @param message String 형태의 로그 message
     */
    public void writeLog(String message) {

        String result = getTime() + SPACE + message;

        try {

            System.out.println(result);

            fw.write(result + NEW_LINE);
            fw.flush();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_WRITE_FAILED);

        }
    }

    /**
     * 로그를 쓰는 함수이다. flag 에 따라 로그가 다르게 저장 된다.
     *
     * @param status   MainConstants에 정의된 CLIENT_STATUS 열거형이다.
     *                 이 값에 따라 로그가 다르게 저장 된다.
     * @param clientIP String 형태의 클라이언트 IP 이다.
     */
    public void writeLog(MainConstants.CLIENT_STATUS status, String clientIP) {

        String result = getTime() + clientIP;

        try {

            switch (status) {

                case CONNECT:
                    System.out.println(result + MSG_CONNECTED);

                    fw.write(result + MSG_CONNECTED + NEW_LINE);
                    fw.flush();

                    break;

                case SEND:
                    System.out.println(result + MSG_SEND_DATA);

                    fw.write(result + MSG_SEND_DATA + NEW_LINE);
                    fw.flush();

                    break;

                case SEND_COMPLETE:
                    System.out.println(result + MSG_SEND_COMPLETE);
                    fw.write(result + MSG_SEND_COMPLETE + NEW_LINE);
                    fw.flush();

                    break;

                case DISCONNECT:
                    System.out.println(result + MSG_DISCONNECTED);
                    fw.write(result + MSG_DISCONNECTED + NEW_LINE);
                    fw.flush();

            }

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_WRITE_FAILED);

        }
    }

    /**
     * 로그를 쓰는 함수이다. 클라이언트 에서 request 가 있을 경우 해당 request 를 로그로 저장 할때 사용 한다.
     *
     * @param clientIP String 형태의 클라이언트 IP 이다.
     * @param request  String 형태의 클라이언트 에서 보내온 request 이다.
     */
    public void writeLog(String clientIP, String request) {

        String result = getTime() + clientIP;

        try {

            System.out.println(result + MSG_REQUEST + request);
            fw.write(result + MSG_REQUEST + request + NEW_LINE);
            fw.flush();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_WRITE_FAILED);

        }
    }

    /**
     * Log Util 을 다 사용한 후 자원을 정리하는 메소드 이다.
     */
    public void close() {

        try {

            fw.close();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_LOG_UTIL_CLOSE_FAILED);
        }
    }
}
