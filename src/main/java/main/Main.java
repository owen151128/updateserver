package main;

import server.ClientResponser;
import util.LogUtil;
import util.SettingFileManager;
import util.UpdateInfoManager;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Update Server 의 Main 클래스 이다.
 * <p>
 * main 메소드가 존재하는 클래스
 */
public class Main {

    private static ServerSocket server;
    private static Socket client;
    private static String clientIp;
    private static LogUtil log;

    private static boolean isBusy = false;

    private static UpdateInfoManager manager;

    private static Thread updateThread;

    /**
     * Update Server 의 main 메소드 이다.
     *
     * @param args String[] 형태의 서버 시작시 매개변수가 저장되는 변수 이다.
     */
    public static void main(String[] args) {

        int port;

        if (args.length == 0) {

            JOptionPane.showMessageDialog(null, MainConstants.ERR_NO_PORT_NUMBER);

            return;

        } else {

            try {

                int tmp = Integer.parseInt(args[0]);

                if (tmp < 0 || tmp > MainConstants.MAX_PORT) {

                    JOptionPane.showMessageDialog(null, MainConstants.ERR_IMPOSSIBLE_PORT);

                    return;
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(null, MainConstants.ERR_WRONG_PORT_NUMBER);

                return;
            }

            port = Integer.parseInt(args[0]);

        }

        log = new LogUtil();

        try {

            server = new ServerSocket(port);

        } catch (IOException e) {

            e.printStackTrace();
            log.writeLog(MainConstants.ERR_SERVER_START_FAILED);

        }

        SettingFileManager settingFileManager = SettingFileManager.getInstance();

        if (settingFileManager.getLocal_path() != null) {

            manager = new UpdateInfoManager(settingFileManager.getLocal_path());

        }

        updateThread = new Thread(() -> {

            try {

                while (true) {

                    Thread.sleep(settingFileManager.getUpdate_refresh());

                    isBusy = true;

                    manager.update();

                    isBusy = false;
                }
            } catch (InterruptedException e) {

                log.writeLog(MainConstants.MSG_UPDATE_INFO_MANAGER_STOP);

            }
        });

        Thread stopThread = new Thread(() -> {

            Socket socket = null;

            try {

                ServerSocket stop;
                stop = new ServerSocket(settingFileManager.getStop_port());

                while (socket == null) {

                    socket = stop.accept();

                }

                stop();

            } catch (IOException e) {

                e.printStackTrace();
                log.writeLog(MainConstants.ERR_SERVER_STOP_FAILED);

            }

        });

        stopThread.start();

        updateThread.start();

        log.writeLog(MainConstants.MSG_SERVER_LOADED);

        while (true) {

            try {

                client = server.accept();

                client.setSoTimeout(settingFileManager.getTime_out());
                clientIp = client.getInetAddress().toString().substring(1);

            } catch (SocketException e) {

                break;

            } catch (IOException e) {

                e.printStackTrace();
                log.writeLog(MainConstants.ERR_SERVER_IO_FAILED);

                break;
            }

            log.writeLog(MainConstants.CLIENT_STATUS.CONNECT, clientIp);

            Thread networkThread = new Thread(() -> {

                ClientResponser connector = new ClientResponser(client);
                String request = connector.getRequest();

                log.writeLog(clientIp, request);

                if (isBusy) {

                    connector.sendBusy(request);
                    log.writeLog(clientIp, MainConstants.MSG_SERVER_BUSY, true);

                } else {

                    switch (request) {

                        case MainConstants.RequestProtocol.UPDATE_INFO_TREE:

                            log.writeLog(MainConstants.CLIENT_STATUS.SEND, clientIp);

                            connector.sendUpdateInfoDTO(settingFileManager.getClient_path(), manager.getRoot());

                            log.writeLog(MainConstants.CLIENT_STATUS.SEND_COMPLETE, clientIp);

                            break;

                        case MainConstants.RequestProtocol.REQUEST_DOWNLOAD_DTO:

                            log.writeLog(MainConstants.CLIENT_STATUS.SEND, clientIp);

                            connector.getDownloadRequestDTOAndSendFileResponseDTO();

                            log.writeLog(MainConstants.CLIENT_STATUS.SEND_COMPLETE, clientIp);

                    }
                }


                try {

                    client.close();

                } catch (IOException e) {

                    e.printStackTrace();
                    log.writeLog(MainConstants.ERR_SERVER_STREAM_CLOSE_FAILED);

                }

                log.writeLog(MainConstants.CLIENT_STATUS.DISCONNECT, clientIp);

            });

            networkThread.start();
        }
    }

    /**
     * 서버 종료 시에 호출 되는 메소드
     * 서버 에서 사용하던 자원 들을 정리하는 역할을 한다.
     */
    public static void stop() {

        try {

            if (log != null) {

                log.writeLog(MainConstants.MSG_SERVER_STOP);

            }

            if (client != null) {

                client.close();

            }

            if (server != null) {

                server.close();

            }

            if (log != null) {

                log.close();

            }

            if (updateThread != null) {

                updateThread.interrupt();

            }

            System.exit(0);

        } catch (IOException e) {

            e.printStackTrace();
            log.writeLog(MainConstants.ERR_RESOURCE_CLEAN_FAILED);

        }
    }
}
