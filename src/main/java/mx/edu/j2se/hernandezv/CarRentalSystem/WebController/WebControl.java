package mx.edu.j2se.hernandezv.CarRentalSystem.WebController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebControl {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
