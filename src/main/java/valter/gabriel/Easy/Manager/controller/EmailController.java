package valter.gabriel.Easy.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.service.EmailService;

@RestController
@RequestMapping("/api/v1/")
public class EmailController {


    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }



    @PostMapping("email/request-delete/{cnpj}/{cpf}/{id}")
    public ResponseEntity<String> triggerMail(@PathVariable("cnpj") Long cnpj, @PathVariable("cpf") Long cpf, @PathVariable("id") Long id, @RequestBody String reason) {
        String email = emailService.sendEmail(cnpj, cpf,id, reason);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }


}
