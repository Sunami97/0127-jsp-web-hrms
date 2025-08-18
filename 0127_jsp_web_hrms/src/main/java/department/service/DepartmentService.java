package department.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import department.dao.DepartmentDAO;
import department.model.DepartmentDTO;

public class DepartmentService {

    private DepartmentDAO departmentDAO = new DepartmentDAO();
    // DAO 객체 생성 (데이터 접근 담당)
    // DAO オブジェクトの生成（データアクセス担当）

    // 전체 조직도 조회 (평면 데이터)
    // 組織図全体の取得（フラットデータ）
    public List<DepartmentDTO> getOrgChart(Connection conn) throws SQLException {
        return departmentDAO.getOrgChart(conn);
    }

    // 전체 조직도 계층 구조로 변환 (부서 → 직책 → 사용자)
    // 組織図全体を階層構造に変換（部署 → 職位 → ユーザー）
    public Map<String, Map<String, List<DepartmentDTO>>> getOrgChartHierarchy(Connection conn) throws SQLException {
        List<DepartmentDTO> flatList = getOrgChart(conn);
        Map<String, Map<String, List<DepartmentDTO>>> orgChartMap = new LinkedHashMap<>();

        for (DepartmentDTO dto : flatList) {
            orgChartMap
                .computeIfAbsent(dto.getDepartmentName(), k -> new LinkedHashMap<>())
                .computeIfAbsent(dto.getPosition(), k -> new ArrayList<>())
                .add(dto);
        }

        return orgChartMap;
    }
}
