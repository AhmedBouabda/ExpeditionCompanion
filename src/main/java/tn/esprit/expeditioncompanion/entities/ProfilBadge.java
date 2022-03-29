package tn.esprit.expeditioncompanion.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilBadge {
;
Poupilarite poupilarite;

    public Poupilarite getPoupilarite() {
        return poupilarite;
    }

    public void setPoupilarite(Poupilarite poupilarite) {
        this.poupilarite = poupilarite;
    }
}
