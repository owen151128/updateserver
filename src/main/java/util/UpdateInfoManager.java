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
     * 기본 생성자로써 root 노드를 설정한다.
     * root 노드의 경로 즉, 최상위 경로는 client_path 가 된다.
     *
     * @param local_path root 노드의 경로가 될 String 형태의 경로
     */
    public UpdateInfoManager(String local_path) {

        this.local_path = local_path;

        UpdateInfoTree tree = new UpdateInfoTree(local_path);

        root = tree.getRoot();

    }

    /**
     * UpdateInfoTree 값을 최신으로 업데이트 해주는 메소드
     */
    public synchronized void update() {

        root.getChildList().clear();
        UpdateInfoTree.createUpdateInfoTree(local_path.length(), local_path, root);

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
