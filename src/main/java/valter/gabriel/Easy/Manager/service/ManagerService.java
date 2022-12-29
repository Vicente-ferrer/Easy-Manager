package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.ResCreatedJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResEmployeeToJobCreated;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManagerToJobCreated;
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
    public void createNewManager(ReqManager reqManager) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = mapper.map(reqManager, Manager.class);

        LocalDate localDateTime = LocalDate.now();
        manager.setCreationDate(localDateTime);

        managerRepo.save(manager);
    }

    /**
     * Method used to update a manager by its identifier
     *
     * @param cnpj             manager identifier
     * @param reqManagerUpdate manager object to be updated
     * @return manager updated
     */
    public Manager updateManagerById(Long cnpj, ReqManagerUpdate reqManagerUpdate) {
        Optional<Manager> managerFounded = managerRepo.findById(cnpj);

        if (!managerFounded.isPresent()) {
            return null;
        }

        Manager myManager = managerFounded.get();

        myManager.setMName(reqManagerUpdate.getMName());
        myManager.setMEmail(reqManagerUpdate.getMEmail());
        myManager.setMPhone(reqManagerUpdate.getMPhone());
        myManager.setPassword(reqManagerUpdate.getPassword());
        myManager.setMCompany(reqManagerUpdate.getMCompany());

        managerRepo.save(myManager);
        return myManager;
    }

    /**
     * Method used to find a manager by his identifier
     *
     * @param cnpj identifier manager
     * @return manager found
     */
    public Manager findManagerById(Long cnpj) {
        Optional<Manager> manager = managerRepo.findById(cnpj);
        return manager.orElse(null);
    }

    public Manager deleteManagerById(Long cnpj) {
        Optional<Manager> myManager = managerRepo.findById(cnpj);
        if (!myManager.isPresent()) {
            return null;
        }
        managerRepo.delete(myManager.get());
        return myManager.get();
    }
}
