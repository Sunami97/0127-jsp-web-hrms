package contacts.service;

import contacts.model.ContactsDTO;
import contacts.dao.ContactsDAO;

import java.util.List;

public class ContactsService {

    private ContactsDAO contactsDAO;

    public ContactsService() {
        contactsDAO = new ContactsDAO(); // DAO 생성자 내부에서 DB 연결 등 처리
    }

    public List<ContactsDTO> getAllContacts() {
        return contactsDAO.findAll(); // DB에서 모든 연락처 가져오기
    }
}
