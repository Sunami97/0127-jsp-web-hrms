package myPage.service;

import java.util.List;

import myPage.dto.UserDepartmentDTO;

public class MypageList {

	private List<UserDepartmentDTO> content;

	public MypageList(List<UserDepartmentDTO> content) {
		super();
		this.content = content;
	}

	public List<UserDepartmentDTO> getContent() {
		return content;
	}

	public void setContent(List<UserDepartmentDTO> content) {
		this.content = content;
	}
	
	
}
