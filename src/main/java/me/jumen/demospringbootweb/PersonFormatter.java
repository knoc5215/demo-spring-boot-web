package me.jumen.demospringbootweb;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/*
JPA 테스트에서 출력 이상
이 formatter를 bean으로 넣어두고 있었기때문에, /jpaTest?id=1 로 넘겨도, 아래 parse method를 실행하면서 setName(1)을 해줬기 때문에 오류가 났었다
JPA를 사용할때는 formatter bean을 확인하자
 */
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
