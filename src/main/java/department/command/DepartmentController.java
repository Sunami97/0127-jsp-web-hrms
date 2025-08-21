package department.command;

import java.sql.Connection;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import department.model.DepartmentDTO;
import department.service.DepartmentService;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;

public class DepartmentController implements CommandHandler {

    private DepartmentService departmentService = new DepartmentService();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection conn = ConnectionProvider.getConnection()) {
            // Service에서 계층 구조 데이터 받기
            // サービスから階層構造データを取得
            Map<String, Map<String, List<DepartmentDTO>>> orgChartMap = 
                departmentService.getOrgChartHierarchy(conn);

            request.setAttribute("orgChartMap", orgChartMap);
        }

        return "/WEB-INF/view/department/departmentList.jsp";
    }
}
