package util;

import main.MainConstants;
import model.DownloadRequestDTO;
import model.FileResponse;
import model.UpdateInfo;
import server.ClientResponser;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * FileResponse 관련 유틸
 * FileResponse 생성에 관한 메소드가 있다.
 */
public class FileResponseUtil {

    private static final String ERR_FILE_LOAD_FAILED = " File load failed!";
    private static final String ERR_IO_FAILED = "I/O failed. check stack trace";

    private static SettingFileManager settingFileManager = SettingFileManager.getInstance();

    /**
     * DownloadRequestDTO 를 FileResponse 로 변환 하여  ClientResponser 로 넘겨 클라이언트로 전송 하는 메소드
     *
     * @param downloadRequestDTO DownloadRequestDTO 형태의 클라이언트 에서 요청 온 downloadRequestDTO
     * @param clientResponser    FileResponse 를 보내기 위한 ClientResponse 객체
     */
    public static void downloadRequestDTOToFileResponse(ClientResponser clientResponser, DownloadRequestDTO downloadRequestDTO) {

        ArrayList<UpdateInfo> updateInfos = downloadRequestDTO.getList();

        FileResponse response;

        try {
            for (UpdateInfo info : updateInfos) {

                if (info.isDirectory()) {

                    response = new FileResponse();

                    response.setFileName(info.getFileName());
                    response.setFilePath(info.getFilePath());
                    response.setDirectory(true);

                    File[] files = new File(settingFileManager.getLocal_path() + info.getFilePath()).listFiles();

                    if (files != null) {

                        directoryRequestToFileResponses(files, clientResponser);

                    }

                    clientResponser.sendFileResponse(response);

                    System.out.println(response.getFileName() + " send.");

                } else {

                    File target = new File(settingFileManager.getLocal_path() + info.getFilePath());

                    if (info.isBig()) {

                        RandomAccessFile randomAccessFile;
                        FileChannel targetChannel;
                        ByteBuffer byteBuffer;
                        byte[] binaryData;

                        try {

                            randomAccessFile = new RandomAccessFile(target.getAbsolutePath(), "r");
                            targetChannel = randomAccessFile.getChannel();
                            byteBuffer = ByteBuffer.allocateDirect(MainConstants.BIG_FILE);

                            long size = target.length() /
                                    MainConstants.BIG_FILE + (target.length() % MainConstants.BIG_FILE == 0 ? 0 : 1);

                            int index = 1;

                            while (targetChannel.read(byteBuffer) > 0) {

                                byteBuffer.flip();

                                binaryData = new byte[byteBuffer.limit()];

                                for (int i = 0; i < byteBuffer.limit(); i++) {

                                    binaryData[i] = byteBuffer.get();

                                }

                                response = new FileResponse();

                                response.setSize((int) size);
                                response.setFileName(info.getFileName());
                                response.setFilePath(info.getFilePath());
                                response.setFileHash(SHA256HashGenerator.getHash(binaryData));
                                response.setDirectory(false);
                                response.setBig(true);
                                response.setIndex(index);
                                response.setData(binaryData);
                                index++;

                                clientResponser.sendFileResponse(response);

                                byteBuffer.clear();

                                if (response.getIndex() == 1) {

                                    System.out.println(response.getFileName() + " send.");

                                }

                            }

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    } else {

                        response = new FileResponse();

                        response.setFileName(info.getFileName());
                        response.setFilePath(info.getFilePath());
                        response.setFileHash(info.getFileHash());
                        response.setDirectory(false);

                        try {

                            response.setData(Files.readAllBytes(target.toPath()));

                        } catch (IOException e) {

                            e.printStackTrace();
                            System.out.println(info.getFilePath() + ERR_FILE_LOAD_FAILED);
                        }

                        clientResponser.sendFileResponse(response);

                        System.out.println(response.getFileName() + " send.");

                    }
                }

            }

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);


        }
    }

    /**
     * 디렉토리에 대한 요청인 경우 하위 디렉토리 및 파일을 순회하여 FileResponse 들로 변환하는 메소드
     *
     * @param target          디렉토리에 대한 하위 디렉토리 및 파일들이 담긴 File[] list
     * @param clientResponser FileResponse 를 보내는 메소드를 호출하기 위한 ClientResponser 객체
     */
    private static void directoryRequestToFileResponses(File[] target, ClientResponser clientResponser) {

        FileResponse response;

        try {

            for (File f : target) {

                if (f.isDirectory()) {

                    response = new FileResponse();

                    response.setFileName(f.getName());
                    response.setFilePath(f.getAbsolutePath().substring(settingFileManager.getLocal_path().length()));
                    response.setDirectory(true);

                    File[] files = f.listFiles();

                    if (files != null) {

                        directoryRequestToFileResponses(files, clientResponser);
                    }

                    clientResponser.sendFileResponse(response);

                    System.out.println(response.getFileName() + " send.");

                } else {

                    if (f.getName().substring(0, 1).equals(".")) {
                        continue;
                    }

                    if (f.length() > MainConstants.BIG_FILE) {

                        RandomAccessFile randomAccessFile;
                        FileChannel targetChannel;
                        ByteBuffer byteBuffer;
                        byte[] binaryData;

                        try {

                            randomAccessFile = new RandomAccessFile(f.getAbsolutePath(), "r");
                            targetChannel = randomAccessFile.getChannel();
                            byteBuffer = ByteBuffer.allocateDirect(MainConstants.BIG_FILE);

                            long size = f.length() /
                                    MainConstants.BIG_FILE + (f.length() % MainConstants.BIG_FILE == 0 ? 0 : 1);

                            int index = 1;

                            while (targetChannel.read(byteBuffer) > 0) {

                                byteBuffer.flip();

                                binaryData = new byte[byteBuffer.limit()];

                                for (int i = 0; i < byteBuffer.limit(); i++) {

                                    binaryData[i] = byteBuffer.get();

                                }

                                response = new FileResponse();

                                response.setSize((int) size);
                                response.setFileName(f.getName());
                                response.setFilePath(f.getAbsolutePath().substring(settingFileManager.getLocal_path().length()));
                                response.setFileHash(SHA256HashGenerator.getHash(binaryData));
                                response.setDirectory(false);
                                response.setBig(true);
                                response.setIndex(index);
                                response.setData(binaryData);
                                index++;

                                clientResponser.sendFileResponse(response);

                                byteBuffer.clear();

                                if (response.getIndex() == 1) {

                                    System.out.println(response.getFileName() + " send.");

                                }

                            }

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    } else {

                        response = new FileResponse();

                        response.setFileName(f.getName());
                        response.setFilePath(f.getAbsolutePath().substring(settingFileManager.getLocal_path().length()));
                        response.setFileHash(SHA256HashGenerator.getHash(f.getAbsoluteFile().toString()));
                        response.setDirectory(false);

                        try {

                            response.setData(Files.readAllBytes(f.toPath()));

                        } catch (IOException e) {

                            e.printStackTrace();
                            System.out.println(f.getAbsolutePath() + ERR_FILE_LOAD_FAILED);
                        }

                        clientResponser.sendFileResponse(response);

                        System.out.println(response.getFileName() + " send.");
                    }
                }

            }
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);

        }

    }
}
