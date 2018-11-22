package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * 서버의 셋팅 파일을 관리하고 셋팅 값들을 불러오는 클래스 이다.
 */
public class SettingFileManager {

    private static final String SETTING_FILE_PATH = "." + File.separator + "settings";
    private static final String LOCAL_PATH = "local_path";
    private static final String CLIENT_PATH = "client_path";

    private static final String ERR_SETTING_FILE_LOAD_FAILED = "setting file load failed. check setting file";
    private static final String ERR_SETTING_FILE_OPTION_NOT_FOUND = "no search local_path or client_path! check setting file";

    private static final String MSG_SETTING_FILE_NOT_FOUND = "setting file not found";
    private static final String MSG_SETTING_FILE_CREATE_FAILED = "setting file create failed";
    private static final String MSG_SETTING_FILE_CREATED = "setting file created";
    private static final String MSG_SETTING_FILE_LOADED = "setting file loaded";


    private static SettingFileManager instance;
    private String local_path;
    private String client_path;

    /**
     * SettingFileManager 의 인스턴스를 불러오는 메소드 이다.
     * Single-Tone 패턴 적용시 사용하는 메소드
     *
     * @return SettingFileManager 인스턴스
     */
    public static synchronized SettingFileManager getInstance() {

        if (instance == null) {

            instance = new SettingFileManager();

        }

        return instance;

    }

    /**
     * SettingFileManager 의 생성자 이다. 셋팅 파일 경로에서 셋팅 파일을 불러온 후 셋팅 값들을 불러오는 역할을 한다.
     * Single-Tone 패턴 적용으로 인해 외부에서 생성자를 호출하지 못한다.
     */
    private SettingFileManager() {
        try {

            File settingFile = new File(SETTING_FILE_PATH);

            if (!settingFile.exists()) {

                System.out.println(MSG_SETTING_FILE_NOT_FOUND);

                if (settingFile.createNewFile()) {

                    System.out.println(MSG_SETTING_FILE_CREATED);

                } else {

                    System.out.println(MSG_SETTING_FILE_CREATE_FAILED);

                }
            }

            List<String> settings = Files.readAllLines(settingFile.toPath());

            StringBuilder builder = new StringBuilder();

            for (String s : settings) {

                builder.append(s);

            }

            String result = builder.toString();

            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(result);

            local_path = element.getAsJsonObject().get(LOCAL_PATH).getAsString();
            client_path = element.getAsJsonObject().get(CLIENT_PATH).getAsString();

            if (local_path == null | client_path == null) {

                System.out.println(ERR_SETTING_FILE_LOAD_FAILED);

            }

            System.out.println(MSG_SETTING_FILE_LOADED);

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_SETTING_FILE_LOAD_FAILED);

        } catch (IllegalStateException e) {

            System.out.println(ERR_SETTING_FILE_OPTION_NOT_FOUND);
            Main.stop();

        }
    }

    /**
     * local_path 옵션 값을 반환 하는 메소드
     * local_path 옵션은 업데이트 될 파일들의 경로를 의미 한다.
     * 이 옵션이 설정되어 있지 않을 경우 서버가 start 되지 않고 바로 종료 된다.
     *
     * @return String 형태의 local_path 옵션 값
     */
    public String getLocal_path() {

        return local_path;

    }

    /**
     * client_path 옵션 값을 반환 하는 메소드
     * client_path 옵션은 클라이언트에 설치되어야 할 경로를 의미 한다.
     * 이 옵션이 설정되어 있지 않을 경우 서버가 start 되지 않고 바로 종료 된다.
     *
     * @return String 형태의 client_path 옵션 값
     */
    public String getClient_path() {

        return client_path;

    }
}
