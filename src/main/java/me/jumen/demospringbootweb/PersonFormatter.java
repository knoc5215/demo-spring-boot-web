package me.jumen.demospringbootweb;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PersonFormatter implements Formatter<Person> {
    @Override
    public Person parse(String name, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(name);
        return person;
    }

    @Override
    public String print(Person object, Locale locale) {
        String name = object.getName();
        return name;
    }
}
