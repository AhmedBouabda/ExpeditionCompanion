package tn.esprit.spring.entities;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class InvitationFieldSetMapper implements FieldSetMapper<Invitation> {
    @Override
    public Invitation mapFieldSet(FieldSet fieldSet) {
        return Invitation.builder()
        		.IdInvi(null)
                .Email(fieldSet.readString(0))
                .State(fieldSet.readBoolean(0))
                .build();
    }
    
  
}
