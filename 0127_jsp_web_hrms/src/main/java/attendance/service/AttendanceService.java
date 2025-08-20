package attendance.service;

import java.sql.Connection;
import java.util.List;

import attendance.dao.AttendanceDao;
import attendance.model.AttendanceRecord;
import jdbc.connection.ConnectionProvider;

public class AttendanceService {
    private final AttendanceDao dao = new AttendanceDao();

    public boolean hasOpen(String userId) throws Exception {
        try (Connection c = ConnectionProvider.getConnection()) {
            return dao.existsOpenStatus(c, userId);
        }
    }

    /** 출근: 오픈 기록 없을 때만 생성 + work_status=勤務中 */
    public void clockIn(String userId) throws Exception {
        try (Connection c = ConnectionProvider.getConnection()) {
            c.setAutoCommit(false);
            boolean open = dao.existsOpenStatus(c, userId);
            if (!open) {
                dao.insertStart(c, userId, "勤務中");
                dao.updateWorkStatus(c, userId, "勤務中");
            }
            c.commit();
        }
    }

    /** 퇴근: 특정 행 종료 + work_status=退勤 */
    public void clockOut(String userId, long statusId) throws Exception {
        try (Connection c = ConnectionProvider.getConnection()) {
            c.setAutoCommit(false);
            dao.closeById(c, userId, statusId);
            dao.updateWorkStatus(c, userId, "退勤");
            c.commit();
        }
    }

    public List<AttendanceRecord> getAllHistory(String userId) throws Exception {
        try (Connection c = ConnectionProvider.getConnection()) {
            return dao.selectAllByUserDesc(c, userId);
        }
    }
    
    public static class PageResult<T> {
        private final java.util.List<T> items;
        private final int page;      // 1-based
        private final int size;
        private final int totalCount;

        public PageResult(java.util.List<T> items, int page, int size, int totalCount) {
            this.items = items; this.page = page; this.size = size; this.totalCount = totalCount;
        }
        public java.util.List<T> getItems() { return items; }
        public int getPage() { return page; }
        public int getSize() { return size; }
        public int getTotalCount() { return totalCount; }
        public int getTotalPages() { return (int)Math.ceil(totalCount / (double)size); }
        public boolean getHasPrev() { return page > 1; }
        public boolean getHasNext() { return page < getTotalPages(); }
        // 페이지 바(블록) 계산: 5개씩
        public int getStartPage() { int blk=(page-1)/5; return blk*5 + 1; }
        public int getEndPage()   { return Math.min(getStartPage()+4, getTotalPages()); }
    }

    /** 페이지네이션: 1페이지당 size개, 최신순 */
    public PageResult<attendance.model.AttendanceRecord> getHistoryPage(String userId, int page, int size) throws Exception {
        if (page < 1) page = 1;
        if (size < 1) size = 6;

        try (Connection c = ConnectionProvider.getConnection()) {
            int total = dao.countByUser(c, userId);
            int startRow = (page - 1) * size + 1;
            int endRow   = page * size;
            java.util.List<attendance.model.AttendanceRecord> items =
                total > 0 ? dao.selectPageByUserDesc(c, userId, startRow, endRow) : java.util.Collections.emptyList();
            return new PageResult<>(items, page, size, total);
        }
    }
}
