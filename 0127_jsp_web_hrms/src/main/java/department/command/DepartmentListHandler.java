package department.command;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import department.model.UserStatusDTO;
import department.service.DepartmentService;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;

public class DepartmentListHandler implements CommandHandler {

    private DepartmentService departmentService = new DepartmentService();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection conn = ConnectionProvider.getConnection()) {
            // DAO → Service → 결과 리스트 얻기
            List<UserStatusDTO> flatList = departmentService.getOrgChart(conn);

            // 계층 구조로 가공: 부서 → 직책 → 사용자 목록
            Map<String, Map<String, List<UserStatusDTO>>> orgChartMap = new LinkedHashMap<>();

            for (UserStatusDTO dto : flatList) {
                orgChartMap
                    .computeIfAbsent(dto.getDepartmentName(), k -> new LinkedHashMap<>())
                    .computeIfAbsent(dto.getPosition(), k -> new ArrayList<>())
                    .add(dto);
            }

            request.setAttribute("orgChartMap", orgChartMap);
        }

        return "/WEB-INF/view/department/departmentList.jsp";
    }
}
