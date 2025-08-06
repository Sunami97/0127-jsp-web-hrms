package department.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import department.model.DepartmentUserDTO;

public class DepartmentDAO {

    // 전체 조직도 가져오기 (부서 + 직책 + 사용자 + 상태)
    // 組織図全体の取得（部署 + 職位 + ユーザー + 状態）
    public List<DepartmentUserDTO> getOrgChart(Connection conn) throws SQLException {
        List<DepartmentUserDTO> list = new ArrayList<>();
        // 결과를 담을 리스트 생성
        // 結果を格納するリストを作成

        String sql = "SELECT d.department_name, u.position, u.name, u.work_status " +
                "FROM user_tbl u " +
                "LEFT JOIN department_tbl d ON u.department_id = d.department_id " +
                "WHERE u.emp_status = '在職' " + // 재직자만 추출 / 在職者のみ
                "ORDER BY d.department_name, u.position, u.name";
        // 조직도에 필요한 정보들을 조인하여 조회
        // 組織図に必要な情報を結合して取得

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // SQL 실행 및 결과 처리
            // SQL 実行および結果処理

            while (rs.next()) {
                // 결과 행마다 DTO에 담기
                // 各結果行を DTO に格納
                DepartmentUserDTO dto = new DepartmentUserDTO();
                dto.setDepartmentName(rs.getString("department_name")); // 부서명 설정 // 部署名を設定
                dto.setPosition(rs.getString("position"));               // 직책 설정   // 職位を設定
                dto.setName(rs.getString("name"));                       // 이름 설정   // 名前を設定
                dto.setWorkStatus(rs.getString("work_status"));          // 근무중, 연차, 출장 등 설정 // 勤務中、年休、出張などを設定
                list.add(dto); // 리스트에 추가 // リストに追加
            }
        }

        return list; // 최종 리스트 반환 // 最終リストを返す
    }

    // 특정 부서에 속한 사용자만 조회
    // 特定部署に所属するユーザーのみ取得
    public List<DepartmentUserDTO> getUsersByDepartment(Connection conn, String departmentId) throws SQLException {
        List<DepartmentUserDTO> list = new ArrayList<>();
        // 결과를 담을 리스트 생성
        // 結果を格納するリストを作成

        String sql = "SELECT d.department_name, u.position, u.name, s.status_type, s.is_current "
                   + "FROM user_tbl u "
                   + "LEFT JOIN department_tbl d ON u.department_id = d.department_id "
                   + "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id "
                   + "WHERE s.is_current = 'Y' AND d.department_id = ? "
                   + "ORDER BY u.position, u.name";
        // 특정 부서 ID에 해당하는 사용자만 조건으로 조회
        // 指定された部署IDに該当するユーザーのみを条件で取得

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId); // 첫 번째 ? 에 부서 ID 설정
            // 最初の ? に部署IDを設定

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 결과 처리 및 DTO로 변환
                    // 結果を処理して DTO に変換
                    DepartmentUserDTO dto = new DepartmentUserDTO();
                    dto.setDepartmentName(rs.getString("department_name")); // 부서명 설정 // 部署名を設定
                    dto.setPosition(rs.getString("position"));               // 직책 설정 // 職位を設定
                    dto.setName(rs.getString("name"));                       // 이름 설정 // 名前を設定
                    list.add(dto); // 리스트에 추가 // リストに追加
                }
            }
        }

        return list; // 최종 리스트 반환 // 最終リストを返す
    }
}
