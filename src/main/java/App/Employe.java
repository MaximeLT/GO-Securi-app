package App;

import com.google.cloud.firestore.Blob;

public class Employe {

    int id_employe = 0;
    String nom_employe = "";
    String prenom_employe = "";
    Blob image_employe = null;

    public Employe() {

    }

    public Employe(int id_employe, String nom_employe, String prenom_employe, Blob image_employe) {

        this.id_employe = id_employe;
        this.nom_employe = nom_employe;
        this.prenom_employe = prenom_employe;
        this.image_employe = image_employe;
    }

    public int getId_Employe() {
        return this.id_employe;
    }

    public void setId_employe(int id_employe) {
        this.id_employe = id_employe;
    }

    public String getNom_employe() {
        return this.nom_employe;
    }

    public void setNom_employe(String nom_employe) {
        this.nom_employe = nom_employe;
    }

    public String getPrenom_employe() {
        return this.prenom_employe;
    }

    public void setPrenom_employe(String prenom_employe) {
        this.prenom_employe = prenom_employe;
    }

    public Blob getImage_employe() {
        return this.image_employe;
    }

    public void setImage_employe(Blob image_employe) {
        this.image_employe = image_employe;
    }

}
