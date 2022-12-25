package valter.gabriel.Easy.Manager.handle.patters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends FieldValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public Boolean validation(String field) {
        Matcher matcher = EMAIL_PATTERN.matcher(field);
        boolean matches = matcher.matches();
        if (matches){
            return true;
        }else{
            return false;
        }
    }


}
