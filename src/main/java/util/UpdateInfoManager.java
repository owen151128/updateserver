package util;

import model.UpdateInfo;
import model.UpdateInfoTree;

/**
 * UpdateInfoTree 를 관리하는 역할을 담당하는 메소드
 */
public class UpdateInfoManager {

    private String local_path;

    private UpdateInfo root;

    /**
     * 기본 생성자로 local_path 를 받아 UpdateInfoTree 를 생성, 업데이트 한다.
     *
     * @param local_path String 형태의 로컬에 업데이트 파일들이 존재하는 경로
     */
    public UpdateInfoManager(String local_path) {

        this.local_path = local_path;

        UpdateInfoTree tree = new UpdateInfoTree(local_path);

        root = tree.getRoot();

        UpdateInfoTree.createUpdateInfoTree(local_path.length(), local_path, root.getChildList());
    }

    /**
     * UpdateInfoTree 값을 최신으로 업데이트 해주는 메소드
     *
     * @return 최신으로 업데이트 된 UpdateInfoTree 에 root 노드
     */
    public synchronized UpdateInfo update() {

        UpdateInfoTree.createUpdateInfoTree(local_path.length(), local_path, root.getChildList());

        return root;
    }

    /**
     * UpdateInfoTree root 노드를 반환하는 메소드
     *
     * @return UpdateInfo 형태의 UpdateInfoTree root 노드
     */
    public synchronized UpdateInfo getRoot() {

        return root;
    }
}
