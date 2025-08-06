package department.command;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import department.model.DepartmentUserDTO;
import department.service.DepartmentService;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;

public class DepartmentListHandler implements CommandHandler {

    private DepartmentService departmentService = new DepartmentService();
    // 부서 서비스 객체 생성
    // 部署サービスオブジェクトの生成

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection conn = ConnectionProvider.getConnection()) {
            // DAO → Service → 결과 리스트 얻기
            // DAO → サービス → 結果リストの取得
            List<DepartmentUserDTO> flatList = departmentService.getOrgChart(conn);
            // 사용자 정보가 담긴 평면 리스트
            // ユーザー情報が含まれるフラットリスト

            // 계층 구조로 가공: 부서 → 직책 → 사용자 목록
            // 階層構造に変換：部署 → 職位 → ユーザー一覧
            Map<String, Map<String, List<DepartmentUserDTO>>> orgChartMap = new LinkedHashMap<>();
            // 순서를 유지하기 위해 LinkedHashMap 사용
            // 順序を維持するために LinkedHashMap を使用

            for (DepartmentUserDTO dto : flatList) {
                // 각 DTO에서 부서, 직책을 기준으로 계층 구조 구성
                // 各DTOから部署と職位を基に階層構造を作成
                orgChartMap
                    .computeIfAbsent(dto.getDepartmentName(), k -> new LinkedHashMap<>()) // 부서가 없으면 새로 생성
                    // 部署が存在しなければ新しく作成
                    .computeIfAbsent(dto.getPosition(), k -> new ArrayList<>()) // 직책이 없으면 새로 리스트 생성
                    // 職位が存在しなければ新しくリストを作成
                    .add(dto); // 사용자 추가
                    // ユーザーを追加
            }

            request.setAttribute("orgChartMap", orgChartMap);
            // JSP에서 사용할 수 있도록 request에 계층 데이터 저장
            // JSPで使用できるように request に階層データを保存
        }

        return "/WEB-INF/view/department/departmentList.jsp";
        // 요청을 해당 JSP 페이지로 포워딩
        // リクエストを該当 JSP ページにフォワード
    }
}
