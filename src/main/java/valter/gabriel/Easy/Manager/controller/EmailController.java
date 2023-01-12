package valter.gabriel.Easy.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Email;
import valter.gabriel.Easy.Manager.service.EmailService;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {


    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }



    @PostMapping("/request-delete/{cnpj}/{cpf}/{id}")
    public ResponseEntity<String> triggerMailDelete(@PathVariable("cnpj") Long cnpj, @PathVariable("cpf") Long cpf, @PathVariable("id") Long id, @RequestBody Email email) {
        String msg = emailService.sendEmailToCancel(cnpj, cpf,id, email);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/request-extend-time/{cnpj}/{cpf}/{id}")
    public ResponseEntity<String> triggerMailUpdate(@PathVariable("cnpj") Long cnpj, @PathVariable("cpf") Long cpf, @PathVariable("id") Long id, @RequestBody Email email) {
        String msg = emailService.sendEmailToUpdate(cnpj, cpf,id, email);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


}
