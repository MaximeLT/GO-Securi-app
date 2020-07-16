package App;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class Firebase {

    Path KeyPath = FileSystems.getDefault().getPath("src", "main", "resources", "mspr-java---gosecuri-firebase-adminsdk-uz0lz-a6067ec8de.json");
    static Path ImagesTempPath = FileSystems.getDefault().getPath("src", "main", "resources", "imagesTemp");


    private Firestore db;

    public Firebase() throws IOException {
        appInitGetDb();
    }

    public void appInitGetDb() throws IOException {

        // Fetch the service account key JSON file contents
        FileInputStream serviceAccount = new FileInputStream(KeyPath.toString());

        // Initialize the app
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://mspr-java---gosecuri.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        // Get the database
        db = FirestoreClient.getFirestore();
    }

    public Employe getEmploye(int id_employe) throws ExecutionException, InterruptedException {

        DocumentReference docRef = db.collection("Employe").document(String.valueOf(id_employe));
        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // Block on response
        DocumentSnapshot document = future.get();

        Employe employe = null;

        if (document.exists()) {
            // Convert document to Plain Old Java Object
            employe = document.toObject(Employe.class);
        }

        return employe;
    }

    public void removeMateriel(int id_materiel) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("Materiel").document(String.valueOf(id_materiel));
        ApiFuture<WriteResult> future = docRef.update("nombre_materiel", -1);
        WriteResult result = future.get();

    }

    public void ajoutImage(int id_employe) throws IOException, ExecutionException, InterruptedException {

        // Image to store into the database: image --> bytes array --> blob
        File image = new File((ImagesTempPath.toString() + "\\image1.png"));
        byte[] imageBytesArray = Files.readAllBytes(image.toPath());
        Blob imageBlob = Blob.fromBytes(imageBytesArray);

        // Asynchronously update the document
        Map<String, Object> update = new HashMap<>();
        update.put("image_employe", imageBlob);

        // Write the result with merge option (otherwise, the entire document is overwritten)
        ApiFuture<WriteResult> writeResult =
                db
                        .collection("Employe")
                        .document(String.valueOf(id_employe))
                        .set(imageBlob, SetOptions.merge());

    }

    public void getImage(Employe employe) throws IOException {

        // Get the blob image from the employe --> bytes array
        Blob imageBlob = employe.getImage_employe();
        byte[] imageBytesArray = imageBlob.toBytes();

        // Write bytes array to an image
        Files.write(new File((ImagesTempPath.toString() + "\\image1.png")).toPath(), imageBytesArray);
    }
}