package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.*;
import valter.gabriel.Easy.Manager.exception.ApiRequestException;
import valter.gabriel.Easy.Manager.exception.ManagerException;
import valter.gabriel.Easy.Manager.handle.UpdateList;
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private final ManagerRepo managerRepo;


    @Autowired
    public ManagerService(ManagerRepo managerRepo) {
        this.managerRepo = managerRepo;
    }

    /**
     * Method created to create a new manager
     * Accessed only from the admin panel
     *
     * @param reqManager, object with the necessary attributes for creating the manager, the list of
     *                    jobs and employers because this is something done after the creation of the manager
     */

    public ResManagerCreated createNewManager(ReqManager reqManager) {
        final boolean present = managerRepo.findById(reqManager.getCnpj()).isPresent();

        if (present) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Usuário " + reqManager.getCnpj() + " já existente no banco de dados");
        }

        ModelMapper mapper = new ModelMapper();
        Manager manager = mapper.map(reqManager, Manager.class);
        LocalDate localDateTime = LocalDate.now();
        manager.setCreationDate(localDateTime);
        managerRepo.save(manager);
        return mapper.map(manager, ResManagerCreated.class);
    }

    /**
     * Method used to update a manager by its identifier
     *
     * @param cnpj             manager identifier
     * @param reqManagerUpdate manager object to be updated
     * @return manager updated
     */
    public ResManager updateManagerById(Long cnpj, ReqManagerUpdate reqManagerUpdate) {
        Optional<Manager> managerFounded = managerRepo.findById(cnpj);

        if (!managerFounded.isPresent()) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Usuário " + cnpj + " não foi encontrado");
        }

        Manager myManager = managerFounded.get();

        myManager.setMName(reqManagerUpdate.getMName());
        myManager.setMEmail(reqManagerUpdate.getMEmail());
        myManager.setMPhone(reqManagerUpdate.getMPhone());
        myManager.setPassword(reqManagerUpdate.getPassword());
        myManager.setMCompany(reqManagerUpdate.getMCompany());

        managerRepo.save(myManager);

        ModelMapper mapper = new ModelMapper();
        ResManager resManager = mapper.map(myManager, ResManager.class);
        return resManager;
    }

    /**
     * Method used to find a manager by his identifier
     *
     * @param cnpj identifier manager
     * @return manager found
     */
    public Manager findManagerById(Long cnpj) {
        return managerRepo.findById(cnpj)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Usuário " + cnpj + " não foi encontrado"));
    }

    public void deleteManagerById(Long cnpj) {
        Optional<Manager> myManager = managerRepo.findById(cnpj);
        if (!myManager.isPresent()) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Usuário " + cnpj + " não foi encontrado");
        }
        managerRepo.delete(myManager.get());
    }
}
