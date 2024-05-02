package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import org.springframework.stereotype.Service;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;

@Service
public class MaterialService {
    
    private final Firestore firestore;    
    
    public MaterialService() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        this.firestore = firestoreOptions.getService();
    }

    

    public Material getMaterialById(Long id) throws Exception {
        DocumentSnapshot document = firestore.collection("materials").document(String.valueOf(id)).get().get();
        if (document.exists()) {
            Material material = document.toObject(Material.class);
            material.setId(Long.valueOf(document.getId()));
            return material;
        } else {
            throw new Exception("Material with id " + id + " not found");
        }
    }

    public Material updateMaterial(Long id, Material updatedMaterial) throws Exception {
        firestore.collection("materials").document(String.valueOf(id)).set(updatedMaterial);
        return getMaterialById(id);
    }


    public Material saveMaterial(Material newMaterial) throws Exception {
        // Convert Material object to Map
        Map<String, Object> materialData = new HashMap<>();
        materialData.put("name", newMaterial.getName());
        materialData.put("contentType", newMaterial.getContentType());
        materialData.put("data", newMaterial.getData());

        // Add the material document to the Firestore collection
        ApiFuture<WriteResult> writeResult = firestore.collection("materials").document().set(materialData);

        try {
            // Get the document ID of the newly added material
            String documentId = writeResult.get().getUpdateTime().toString();
            // Set the ID for the Material object
            newMaterial.setId(Long.valueOf(documentId));
            return newMaterial;
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Failed to save material: " + e.getMessage());
        }
    }


    public void deleteMaterial(Long id) throws Exception {
        // Get the document reference for the material document
        DocumentReference docRef = firestore.collection("materials").document(String.valueOf(id));

        // Delete the material document
        ApiFuture<WriteResult> deleteResult = docRef.delete();

        try {
            // Wait for the deletion operation to complete
            deleteResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Failed to delete material: " + e.getMessage());
        }
    }

}
