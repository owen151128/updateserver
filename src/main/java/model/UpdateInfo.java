package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 파일에 대한 업데이트 정보를 가지고 있는 클래스
 * UpdateInfoTree 에 대한 Node 이다.
 */
public class UpdateInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String filePath;
    private String fileName;
    private String fileHash;
    private boolean isDirectory;
    private boolean isBig;
    private ArrayList<UpdateInfo> childList;

    /**
     * 기본 생성자로써 자식 리스트를 초기화 한다.
     */
    public UpdateInfo() {
        childList = new ArrayList<>();
    }

    /**
     * 해당 파일의 경로를 반환하는 메소드
     *
     * @return String 형태의 해당 파일 경로
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 해당 파일의 경로를 설정하는 메소드
     *
     * @param filePath String 형태의 해당 파일 경로
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 해당 파일의 파일명을 반환하는 메소드
     *
     * @return String 형태의 해당 파일명
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 해당 파일의 파일명을 설정하는 메소드
     *
     * @param fileName String 형태의 해당 파일명
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 해당 파일의 Hash 값을 반환하는 메소드
     *
     * @return String 형태의 해당 Hash 값
     */
    public String getFileHash() {
        return fileHash;
    }

    /**
     * 해당 파일의 Hash 값을 설정하는 메소드
     *
     * @param fileHash String 형태의 해당 Hash 값
     */
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    /**
     * 해당 클래스가 담고있는 정보에 대해 디렉토리 여부를 반환하는 메소드
     *
     * @return boolean 형태의 값 디렉토리 일 경우 true, 파일 일 경우 false
     */
    public boolean isDirectory() {
        return isDirectory;
    }

    /**
     * 해당 클래스가 담고있는 정보에 대해 디렉토리 여부를 설정하는 메소드
     *
     * @param directory 디렉토리 일 경우 true 를 설정 하고, 아닐 경우 false 를 설정 한다.
     */
    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    /**
     * 해당 클래스가 담고있는 정보에 대해 파일 크기가 10MB 미만일 경우 를 반환하는 메소드
     *
     * @return boolean 형태의 값 10MB 초과 일 경우 true, 미만일 경우 flase
     */
    public boolean isBig() {
        return isBig;
    }

    /**
     * 해당 클래스가 담고있는 정보에 대해 파일 크기 가 10MB 미만 여부를 설정하는 메소드
     *
     * @param big 10MB 초과 일 경우 true, 미만 일 경우 false 를 설정 한다.
     */
    public void setBig(boolean big) {
        isBig = big;
    }

    /**
     * 해당 클래스의 자식 리스트를 얻는 메소드
     *
     * @return ArrayList 형태의 자식 리스트
     */
    public ArrayList<UpdateInfo> getChildList() {
        return childList;
    }

    /**
     * UpdateInfo 의 동등성 을 확인하는 메소드 이다.
     * 파일 이름, 경로 ,해쉬값, 디렉토리 여부 를 비교해서 모두 같으면 true, 하나라도 다르면 false 를 반환 한다.
     *
     * @param obj UpdateInfo 형태의 비교할 UpdateInfo
     * @return 파일 이름, 경로 ,해쉬값, 디렉토리 여부 를 비교해서 모두 같으면 true, 하나라도 다르면 false 를 반환
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof UpdateInfo) {

            UpdateInfo o = (UpdateInfo) obj;

            if (this.getFileHash() == null || o.getFileHash() == null) {

                return this.getFileName().equals(o.getFileName())
                        && this.isDirectory == o.isDirectory
                        && this.getFilePath().equals(o.getFilePath());

            }

            return this.getFileHash().equals(o.getFileHash())
                    && this.getFilePath().equals(o.getFilePath())
                    && this.isDirectory == o.isDirectory
                    && this.isBig == o.isBig
                    && this.getFileName().equals(o.getFileName());

        } else {

            return false;
        }
    }
}
