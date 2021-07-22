package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactsDto;
import com.alkemy.ong.model.Contacts;
import com.alkemy.ong.repository.ContactsRepository;
import com.alkemy.ong.service.Interface.IContacts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactsServiceImpl implements IContacts {

    @Autowired
    private ContactsRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ContactsDto createContacts(ContactsDto dto) {
        Contacts contacts = new Contacts();
        contacts.setName(dto.getName());
        contacts.setMessage(dto.getMessage());
        contacts.setPhone(dto.getPhone());
        contacts.setEmail(dto.getEmail());

        return mapper.map(repository.save(contacts), ContactsDto.class);
    }

    @Override
    public Optional<List<ContactsDto>> getAllContacts() {
        List<Contacts> contactsList = repository.findAll();
        List<ContactsDto>contactsDtoList = new ArrayList<>();
        contactsList.forEach(contacts -> contactsDtoList.add(mapper.map(contacts, ContactsDto.class)));
        return Optional.of(contactsDtoList);
    }

}