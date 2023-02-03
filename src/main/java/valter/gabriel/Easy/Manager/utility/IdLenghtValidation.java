package valter.gabriel.Easy.Manager.utility;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class IdLenghtValidation implements Predicate<Long> {
    @Override
    public boolean test(Long id) {
        String idAsString = id.toString();
        return id > 0 && idAsString.length() == 11 || idAsString.length() == 14;
    }

    public boolean isCNPJId(Long id) {
        if (id.toString().length() == 14) {
            return true;
        }
        return false;
    }

    public boolean isCPFId(Long id) {
        if (id.toString().length() == 11) {
            return true;
        }
        return false;
    }
}
