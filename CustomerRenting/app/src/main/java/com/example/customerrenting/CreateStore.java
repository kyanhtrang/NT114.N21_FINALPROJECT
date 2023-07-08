package com.example.customerrenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.customerrenting.Model.Store;
import com.example.customerrenting.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CreateStore extends AppCompatActivity {

    private EditText inputName;
    private RadioGroup radioGroup;
    private RadioButton rSmall, rMedium, rLarge;
    private Button btnCreate;
    private User user = new User();
    private Store store = new Store();

    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private DocumentReference userRef, storeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);

        init();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStore();
            }
        });
    }

    public void init() {
        inputName = findViewById(R.id.store_name);
        radioGroup = findViewById(R.id.radioGroup);
        btnCreate = findViewById(R.id.btn_create);
        rSmall = findViewById(R.id.Small);
        rMedium = findViewById(R.id.Medium);
        rLarge = findViewById(R.id.Large);

        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());
        userRef = db.collection("Users").document(user.getUserID());
        storeRef = db.collection("Stores").document();
    }

    public void createStore() {
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        store.setStoreName(inputName.getText().toString());
                        store.setSupplierID(document.getString("userID"));
                        store.setAddress(document.getString("address"));
                        store.setNumbers(0);
                        int checkedId = radioGroup.getCheckedRadioButtonId();
                        if (checkedId == R.id.Small) {
                            store.setSize("nho");
                        } else if (checkedId == R.id.Medium) {
                            store.setSize("vua");
                        } else if (checkedId == R.id.Large) {
                            store.setSize("lon");
                        }

                        storeRef.set(store)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        store.setStoreId(storeRef.getId());
                                        updateData(store.getStoreId());
                                        Toast.makeText(CreateStore.this, "Thêm xe thành công", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(CreateStore.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CreateStore.this, "Thêm xe thất bại", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                } else {
                    Toast.makeText(CreateStore.this, "Không thể lấy thông tin", Toast.LENGTH_LONG).show();
                }
            }

            private void updateData(String storeID) {
                Map<String, Object> data = new HashMap<>();
                data.put("storeID", storeID);
                storeRef.update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CreateStore.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateStore.this, "Error updating document", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
