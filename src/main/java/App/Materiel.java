package App;

public class Materiel {

    int id_materiel = 0;
    String nom_materiel = "";
    int nombre_materiel = 0;

    public Materiel() {

    }

    public Materiel(int id_materiel, String nom_materiel, int nombre_materiel) {

        this.id_materiel = id_materiel;
        this.nom_materiel = nom_materiel;
        this.nombre_materiel = nombre_materiel;
    }

    public int getId_materiel() {
        return this.id_materiel;
    }

    public void setId_materiel(int id_materiel) {
        this.id_materiel = id_materiel;
    }

    public String getNom_materiel() {
        return this.nom_materiel;
    }

    public void setNom_materiel(String nom_materiel) {
        this.nom_materiel = nom_materiel;
    }

    public int getNombre_materiel() {
        return this.nombre_materiel;
    }

    public void setNombre_materiel(int nombre_materiel) {
        this.nombre_materiel = nombre_materiel;
    }
}
