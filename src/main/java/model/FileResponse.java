package model;

import java.io.Serializable;

/**
 * 파일에 대한 정보가 담겨저 있는 클래스
 * 파일 이름, 파일 경로, 파일 해쉬값, byte 형태의 바이너리, 디렉토리 여부 등이 담겨저 있다.
 */
public class FileResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String filePath;
    private String fileName;
    private String fileHash;
    private boolean isDirectory;
    private boolean isBig;
    private int index;
    private int size;

    private byte[] data;

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
     * isBig 이 true 일 경우 해당 클래스가 담고있는 파일이 split 된 파일의 몇번째 인지를 반환하는 메소드
     * isBig 이 false 일 경우 해당 값을 사용하지 않으며 0 이 들어 간다.
     *
     * @return int 형태의 값 으로 해당 파일이 split 된 파일의 몇번째 인지를 반환
     */
    public int getIndex() {
        return index;
    }

    /**
     * 해당 클래스가 담고있는 파일이 split 되 있는 경우 파일의 몇번째 인지를 설정하는 메소드
     *
     * @param index int 형태의 값 으로 해당 파일이 split 된 파일의 몇번째 인지를 설정
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 해당 클래스가 담고있는 파일이 split 되 있는 경우 split 된 파일의 총 갯수를 반환 한다.
     *
     * @return int 형태의 split 된 파일의 총 갯수 반환
     */
    public int getSize() {
        return size;
    }

    /**
     * 해당 클래스가 담고있는 파일이 split 되 있는 경우 파일의 총 갯수를 설정하는 메소드
     *
     * @param size split 된 파일의 총 갯수
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * byte[] 형태의 바이너리 값 들을 반환하는 메소드
     *
     * @return byte[] 형태의 바이너리 값 들
     */
    public byte[] getData() {
        return data;
    }

    /**
     * byte[] 형태의 바이너리 값 들을 설정하는 메소드
     *
     * @param data byte[] 형태의 바이너리 값
     */
    public void setData(byte[] data) {
        this.data = data;
    }

}
