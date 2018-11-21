package server;

import model.*;
import util.FileResponseUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 클라이언트 에서 요청이 들어오면 요청에 대해서 처리하고 응답하는 클래스
 */
public class ClientResponser {

    private static final String ERR_IO_FAILED = "I/O failed. check stack trace";
    private static final String ERR_STREAM_CLOSE_FAILED = "Stream close Failed! check stack trace";

    private Socket clientSocket;

    private BufferedReader br = null;
    private InputStreamReader isr = null;
    private ObjectOutputStream oos = null;


    /**
     * 기본 생성자로 클라이언트 와 연결된 Socket 을 받아서 클래스를 초기화 한다.
     *
     * @param clientSocket Socket 형태의 클라이언트와 연결된 소켓
     */
    public ClientResponser(Socket clientSocket) {

        this.clientSocket = clientSocket;

    }

    /**
     * 클라이언트 Request 를 받아서 반환하는 메소드
     *
     * @return String 형태의 클라이언트 Request
     */
    public String getRequest() {

        String request = null;
        try {

            isr = new InputStreamReader(clientSocket.getInputStream());
            br = new BufferedReader(isr);

            request = br.readLine();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);

        }

        return request;
    }

    /**
     * UpdateInfoDTO 를 클라이언트 에 전송하는 메소드
     * UpdateInfo 의 root 노드 와 client_path 가 들어있는 UpdateInfoDTO 가 전송 된다.
     *
     * @param client_path String 형태의 업데이트 파일들이 저장 될 경로이다.
     * @param root        UpdateInfo 형태의 UpdateInfo Tree 자료구조 root 이다.
     */
    public void sendUpdateInfoDTO(String client_path, UpdateInfo root) {

        try {

            UpdateInfoDTO dto = new UpdateInfoDTO(client_path, root);

            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(dto);

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);

        } finally {

            try {

                if (clientSocket != null) {

                    clientSocket.close();

                }

                if (isr != null) {

                    isr.close();

                }

                if (br != null) {

                    br.close();

                }

                if (oos != null) {

                    oos.close();

                }

            } catch (IOException e) {

                e.printStackTrace();
                System.out.println(ERR_STREAM_CLOSE_FAILED);

            }

        }
    }

    /**
     * DownloadRequestDTO 를 클라이언트 에게 전송 받아
     * FileResponseDTO 를 생성 해서 클라이언트 에 전송하는 메소드
     * DownloadRequestDTO 에 대한 FileResponseDTO 가 전송 된다.
     * 전송 되는 정보로는 파일 이름, 경로, 해쉬값, 디렉토리 여부, 파일에 대한 바이너리 byte[] 가 있다.
     */
    public void getDownloadRequestDTOAndSendFileResponseDTO() {

        ObjectInputStream ois = null;

        try {

            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());

            DownloadRequestDTO dto = (DownloadRequestDTO) ois.readObject();

            FileResponseDTO result = new FileResponseDTO();

            if (dto != null) {

                result.setList(new ArrayList<>());
                FileResponseUtil.downloadRequestDTOToFileResponseDTO(dto, result);

                oos.writeObject(result);
            }

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } finally {

            try {

                if (clientSocket != null) {

                    clientSocket.close();

                }

                if (isr != null) {

                    isr.close();

                }

                if (br != null) {

                    br.close();

                }

                if (ois != null) {

                    ois.close();

                }

            } catch (IOException e) {

                e.printStackTrace();
                System.out.println(ERR_STREAM_CLOSE_FAILED);

            }

        }
    }
}
