package valter.gabriel.Easy.Manager.handle;

import valter.gabriel.Easy.Manager.domain.Employee;

import java.util.ArrayList;
import java.util.List;

public class UpdateList <T>{
    public final List<T> updateList(List<T> previusList, List<T> newList) {
        /**
         * We add all this itens to our newEmployees Array List
         * Finally we also add all itens that as passed as parameter using req.get()
         * Than we return the new list containg the previus and the current employers
         */
        ArrayList<T> newEmployees = new ArrayList<>(previusList);
        newEmployees.addAll(newList);
        return newEmployees;
    }

}
