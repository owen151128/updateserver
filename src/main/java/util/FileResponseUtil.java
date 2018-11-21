package util;

import model.DownloadRequestDTO;
import model.FileResponse;
import model.FileResponseDTO;
import model.UpdateInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * FileResponse 관련 유틸
 * FileResponse 생성에 관한 메소드가 있다.
 */
public class FileResponseUtil {

    private static final String ERR_FILE_LOAD_FAILED = " File load failed!";

    private static SettingFileManager settingFileManager = SettingFileManager.getInstance();

    /**
     * DownloadRequestDTO 를 FileResponseDTO 로 변환 하는 메소드
     *
     * @param downloadRequestDTO DownloadRequestDTO 형태의 클라이언트 에서 요청 온 downloadRequestDTO
     * @param fileResponseDTO    FileResponseDTO 형태의 클라이언트에 보낼 fileResponseDTO
     */
    public static void downloadRequestDTOToFileResponseDTO(DownloadRequestDTO downloadRequestDTO, FileResponseDTO fileResponseDTO) {

        ArrayList<UpdateInfo> updateInfos = downloadRequestDTO.getList();
        ArrayList<FileResponse> fileResponses = fileResponseDTO.getList();

        FileResponse response;

        for (UpdateInfo info : updateInfos) {

            if (info.isDirectory()) {

                response = new FileResponse();

                response.setFileName(info.getFileName());
                response.setFilePath(info.getFilePath());
                response.setDirectory(true);

                File[] files = new File(settingFileManager.getLocal_path() + info.getFilePath()).listFiles();

                if (files != null) {

                    directoryRequestToFileResponses(files, fileResponses);

                }

                fileResponses.add(response);

            } else {

                response = new FileResponse();

                File target = new File(settingFileManager.getLocal_path() + info.getFilePath());

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

                fileResponses.add(response);

            }

        }

    }

    /**
     * 디렉토리에 대한 요청인 경우 하위 디렉토리 및 파일을 순회하여 FileResponse 들로 변환하는 메소드
     *
     * @param target    디렉토리에 대한 하위 디렉토리 및 파일들이 담긴 File[] list
     * @param responses 추가할 FileResponse 들을 저장할 ArrayList<FileResponse>
     */
    private static void directoryRequestToFileResponses(File[] target, ArrayList<FileResponse> responses) {

        FileResponse response;

        for (File f : target) {

            if (f.isDirectory()) {

                response = new FileResponse();

                response.setFileName(f.getName());
                response.setFilePath(f.getAbsolutePath().substring(settingFileManager.getLocal_path().length()));
                response.setDirectory(true);

                File[] files = f.listFiles();

                if (files != null) {

                    directoryRequestToFileResponses(files, responses);
                }

                responses.add(response);

            } else {

                if (f.getName().substring(0, 1).equals(".")) {
                    continue;
                }

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

                responses.add(response);
            }

        }

    }
}
