package tn.esprit.expeditioncompanion.entities;

import javax.persistence.*;

@Entity
public class Flow {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idFolow ;

    @ManyToOne
    @JoinColumn(name="iduser",referencedColumnName = "iduser")
    private User iduser;

    @ManyToOne
    @JoinColumn(name="idProfil",referencedColumnName = "idProfil")
    private Profile idProfilEnv;

    public Long getIdFolow() {
        return idFolow;
    }

    public void setIdFolow(Long idFolow) {
        this.idFolow = idFolow;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    public Profile getIdProfilEnv() {
        return idProfilEnv;
    }

    public void setIdProfilEnv(Profile idProfilEnv) {
        this.idProfilEnv = idProfilEnv;
    }

    public Flow() {
    }
}
