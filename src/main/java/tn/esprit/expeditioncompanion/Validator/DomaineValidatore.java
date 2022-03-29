package tn.esprit.expeditioncompanion.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tn.esprit.expeditioncompanion.entities.Domaine;

@Component
public class DomaineValidatore implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Domaine.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Domaine P = (Domaine) o;
    }
}
