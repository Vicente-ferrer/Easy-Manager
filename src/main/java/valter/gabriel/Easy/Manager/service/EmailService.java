package valter.gabriel.Easy.Manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Email;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.exception.ApiRequestException;
import valter.gabriel.Easy.Manager.repo.JobRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final ManagerRepo managerRepo;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, ManagerRepo managerRepo) {
        this.javaMailSender = javaMailSender;
        this.managerRepo = managerRepo;
    }

    public String sendEmailToCancel(Long cnpj, Long cpf, Long id, Email email) {
        Manager manager = managerRepo.findById(cnpj).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> CNPJ " + cnpj + " não encontrado"));

        final Employee employee = manager.getEmployees()
                .stream()
                .filter(_employee -> _employee.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> CNPJ " + cnpj + " não possui o " + cpf + " na sua lista de funcionários."));

        Jobs jobs = employee.getJobs()
                .stream()
                .filter(job -> job.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> CPF " + cpf + " não possui o trabalho com identificador" + id + " na sua lista de trabalhos."));



        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String text = "DE: " + employee.getEName() + " com identificador " +
                employee.getCpf() + " solicita cancelamento do trabalho com identificação " + id + " | " + jobs.getName() +
                " por conta de " + email.getReason() + ".";

        mailMessage.setFrom("vgabrielbri@gmail.com");
        mailMessage.setTo(manager.getMEmail());
        mailMessage.setText(text);
        mailMessage.setSubject("SOLICITAÇÃO DE CANCELAMENTO DE TRABALHO");

        javaMailSender.send(mailMessage);
        return "Email enviado.";
}

    public String sendEmailToUpdate(Long cnpj, Long cpf, Long id, Email email) {
        Manager manager = managerRepo.findById(cnpj).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> CNPJ " + cnpj + " não encontrado"));

        final Employee employee = manager.getEmployees()
                .stream()
                .filter(_employee -> _employee.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> CNPJ " + cnpj + " não possui o " + cpf + " na sua lista de funcionários."));

        Jobs jobs = employee.getJobs()
                .stream()
                .filter(job -> job.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> CPF " + cpf + " não possui o trabalho com identificador" + id + " na sua lista de trabalhos."));



        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String text = "DE: " + employee.getEName() + " com identificador " +
                employee.getCpf() + " solicita a atualização do prazo de trabalho no serviço: " + id + " | " + jobs.getName() +
                " por conta de " + email.getReason() + ".";

        mailMessage.setFrom("vgabrielbri@gmail.com");
        mailMessage.setTo(manager.getMEmail());
        mailMessage.setText(text);
        mailMessage.setSubject("SOLICITAÇÃO DE ATUALIZAÇÃO DO PRAZO DO TRABALHO");

        javaMailSender.send(mailMessage);
        return "Email enviado.";
    }

}
