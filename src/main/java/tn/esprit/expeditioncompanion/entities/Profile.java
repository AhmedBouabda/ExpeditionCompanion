package tn.esprit.expeditioncompanion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

public class Profile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idProfil;

    @NotBlank
    String nom ;

    @NotBlank
    String Prenom ;

    @OneToOne(mappedBy = "profile")
    private UploadImageProfil image ;



    private Long FollowersNbr;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Poupilarite poupilarite;


    @ManyToOne
    @JoinColumn(name="iduser",referencedColumnName = "iduser")
    private User iduser;

    @ManyToOne
    @JoinColumn(name="idDomaine",referencedColumnName = "idDomaine")
        private Domaine idDomaine;

    public UploadImageProfil getImage() {
        return image;
    }

    public void setImage(UploadImageProfil image) {
        this.image = image;
    }

    public Domaine getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(Domaine idDomaine) {
        this.idDomaine = idDomaine;
    }

    @NotBlank
    @Size(min=8, max=8)
    String Cin ;

    @Min(18)
    int age ;

    public Profile() {
    }

    public Long getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(Long idProfil) {
        this.idProfil = idProfil;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getCin() {
        return Cin;
    }

    public void setCin(String cin) {
        Cin = cin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    public Long getFollowersNbr() {
        return FollowersNbr;
    }

    public void setFollowersNbr(Long followersNbr) {
        FollowersNbr = followersNbr;
    }

    public Poupilarite getPoupilarite() {
        return poupilarite;
    }

    public void setPoupilarite(Poupilarite poupilarite) {
        this.poupilarite = poupilarite;
    }

}
