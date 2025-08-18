package department.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import department.model.DepartmentDTO;

public class DepartmentDAO {

    // 전체 조직도 가져오기 (부서 + 직책 + 사용자 + 상태)
    // 組織図全体の取得（部署 + 職位 + ユーザー + 状態）
    public List<DepartmentDTO> getOrgChart(Connection conn) throws SQLException {
        List<DepartmentDTO> list = new ArrayList<>();
        // 결과를 담을 리스트 생성
        // 結果を格納するリストを作成

        String sql = "SELECT d.department_name, u.position, u.name, u.work_status, s.is_current " +
                "FROM user_tbl u " +
                "LEFT JOIN department_tbl d ON u.department_id = d.department_id " +
                "LEFT JOIN user_status_tbl s ON u.user_id = s.user_id " +
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
                DepartmentDTO dto = new DepartmentDTO();
                dto.setDepartmentName(rs.getString("department_name")); // 부서명 설정 // 部署名を設定
                dto.setPosition(rs.getString("position"));               // 직책 설정   // 職位を設定
                dto.setName(rs.getString("name"));                       // 이름 설정   // 名前を設定
                dto.setWorkStatus(rs.getString("work_status"));          // 근무중, 연차, 출장 등 설정 // 勤務中、年休、出張などを設定
                dto.setIsCurrent(rs.getString("is_current"));			 // 로그인 상태 설정 // ログイン状態を設定
                list.add(dto); // 리스트에 추가 // リストに追加
            }
        }

        return list; // 최종 리스트 반환 // 最終リストを返す
    }
}
