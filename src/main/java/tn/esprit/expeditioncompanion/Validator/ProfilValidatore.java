package tn.esprit.expeditioncompanion.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tn.esprit.expeditioncompanion.entities.Profile;

@Component
public class ProfilValidatore implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Profile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Profile P = (Profile) o;
    }
}
