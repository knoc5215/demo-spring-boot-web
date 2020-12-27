package me.jumen.demospringbootweb;

import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") Person person) {
        return "hello " + person.getName();
    }

    @GetMapping("/helloRequestParam")
    public String helloRequestParam(@RequestParam("name") Person person) {
        return "hello " + person.getName();
    }

    @GetMapping("/jpaTest")
    public String jpaTest(@RequestParam("id") Person person) {
        return "hello " + person.getName();
    }

    @GetMapping("/message")
    public String message(@RequestBody String body) {
        return body;
    }

    @GetMapping("/jsonMessage")
    public Person jsonMessage(@RequestBody Person person) {
        return person;
    }

    @GetMapping("/xmlMessage")
    public Person xmlMessage(@RequestBody Person person) {
        return person;
    }
}
