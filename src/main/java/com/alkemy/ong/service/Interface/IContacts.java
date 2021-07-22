package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ContactsDto;

import java.util.List;
import java.util.Optional;

public interface IContacts {

    public ContactsDto createContacts(ContactsDto dto);

    public Optional<List<ContactsDto>> getAllContacts();


}
