package attendance.model;

import java.util.Date;

public class AttendanceRecord {
	private long statusId;
	private String userId;
	private String statusType;
	private Date statusStart;
	private Date statusEnd;
	private boolean current;

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public Date getStatusStart() {
		return statusStart;
	}

	public void setStatusStart(Date statusStart) {
		this.statusStart = statusStart;
	}

	public Date getStatusEnd() {
		return statusEnd;
	}

	public void setStatusEnd(Date statusEnd) {
		this.statusEnd = statusEnd;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
}
