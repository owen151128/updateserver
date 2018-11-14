package server;

import model.UpdateInfo;
import model.UpdateInfoDTO;
import model.UpdateInfoTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 클라이언트 에서 요청이 들어오면 요청에 대해서 처리하고 응답하는 클래스
 */
public class ClientResponser {
    private Socket clientSocket;

    private BufferedReader br = null;
    private InputStreamReader isr = null;
    private ObjectOutputStream oos = null;

    /**
     * 기본 생성자로 클라이언트와 연결된 Socket 을 받아서 클래스를 초기화 한다.
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
            System.out.println("I/O failed. check stack trace");

        }

        return request;
    }

    /**
     * UpdateInfoDTO 를 클라이언트에 전송하는 메소드
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
            System.out.println("I/O failed. check stack trace");

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
                System.out.println("Stream close Failed! check stack trace");

            }

        }
    }
}
