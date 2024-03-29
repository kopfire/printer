package dev.kopfire.site.printer.core.util;

import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.core.service.PersonDetailsService;
import dev.kopfire.site.printer.db.entity.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Primary
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        PersonDTO person = (PersonDTO) target;

        if (!person.getPassword().equals(person.getPasswordTwo()))
            errors.rejectValue("password", "", "Пароли не совпадают");

        if (!person.getCode().equals(Constants.code_reg))
            errors.rejectValue("code", "", "Код регистрации неверный");

        if (!personDetailsService.checkUserByUsername(person.getUsername()))
            errors.rejectValue("username", "", "Человек с таким именем пользователя существует");
    }
}
