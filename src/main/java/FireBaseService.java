import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FireBaseService {

    Path KeyPath = FileSystems.getDefault().getPath("src", "main", "resources", "mspr-java---gosecuri-firebase-adminsdk-uz0lz-a6067ec8de.json");

    static Firestore db;

    public void LaunchDatabase() throws IOException {

        FileInputStream fis = new FileInputStream(KeyPath.toString());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(fis))
                .setDatabaseUrl("https://mspr-java---gosecuri.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
    }

    public static Firestore getDb() {
        return db;
    }

    public FireBaseService() throws IOException {
        LaunchDatabase();
    }
    /*
    public Personne getPersonne(int id_personne) throws ExecutionException, InterruptedException {

        DocumentReference docRef = db.collection("Personne").document(String.valueOf(id_personne));
        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // Block on response
        DocumentSnapshot document = future.get();

        Personne personne = null;

        if (document.exists()) {
            // Convert document to Plain Old Java Object
            personne = document.toObject(Personne.class);
        }

        return personne;
    }

    public void addImage(int id_personne) throws IOException, ExecutionException, InterruptedException {

        // Image to store into the database: image --> bytes array --> blob
        File image = new File("src/main/resources/com/epsiB3/mspr_java/images/detectedFaces/testFace0.jpg");
        byte[] imageBytesArray = Files.readAllBytes(image.toPath());
        Blob imageBlob = Blob.fromBytes(imageBytesArray);

        // Asynchronously update the document
        Map<String, Object> update = new HashMap<>();
        update.put("image_personne", imageBlob);

        // Write the result with merge option (otherwise, the entire document is overwritten)
        ApiFuture<WriteResult> writeResult =
                db
                        .collection("Personne")
                        .document(String.valueOf(id_personne))
                        .set(imageBlob, SetOptions.merge());

        System.out.println("Image added at: " + writeResult.get().getUpdateTime());
    }

    public void getImage(Personne personne) throws IOException {

        // Get the blob image from the personne --> bytes array
        Blob imageBlob = personne.getImage_personne();
        byte[] imageBytesArray = imageBlob.toBytes();

        // Write bytes array to an image
        Files.write(new File("src/main/resources/com/epsiB3/mspr_java/images/detectedFaces/referenceFace.jpg").toPath(), imageBytesArray);
    }
    */
}