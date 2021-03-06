package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.CartridgesMapper;
import dev.kopfire.site.printer.core.mapper.PersonMapper;
import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import dev.kopfire.site.printer.db.entity.Person;
import dev.kopfire.site.printer.db.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personNew = personRepository.findByUsername(username);

        if (personNew.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return personMapper.map(personNew.get(), PersonDTO.class);
    }
}
