package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactsDto;
import com.alkemy.ong.service.Interface.IContacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping(value = "/contacts")
public class ContactsController {

    @Autowired
    private IContacts contactService;

    @Autowired
    private MessageSource message;

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody ContactsDto dto){
        try {
            return ResponseEntity.ok(contactService.createContacts(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ContactsDto>> getAll(){
        return contactService.getAllContacts().filter(Predicate.not(List::isEmpty))
                .map(contactsDtos -> new ResponseEntity<>(contactsDtos, HttpStatus.OK))
                .orElse(new ResponseEntity("No hay ningún contacto aún", HttpStatus.NOT_FOUND));
    }
}
