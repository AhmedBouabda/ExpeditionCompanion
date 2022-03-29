package tn.esprit.expeditioncompanion.Repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.expeditioncompanion.entities.UploadImageProfil;

public interface IUploadImage extends CrudRepository<UploadImageProfil,Long> {
}
