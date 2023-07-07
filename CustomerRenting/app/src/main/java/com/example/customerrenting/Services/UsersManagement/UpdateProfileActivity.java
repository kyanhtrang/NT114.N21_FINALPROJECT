package com.example.customerrenting.Services.UsersManagement;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.MainActivity;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateProfileActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TextView birthday;
    private Button dateButton,btnUpdate;
    private Uri mImageURI;
    private ImageView imgAvatar;
    private String imageID, gender;
    private String documentId, downloadUrl, uploadtype;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore dtb_user;
    private EditText fullname, address, city;

    private Spinner spGender;

    private User user = new User();

    ActivityResultLauncher<String> AvatarpickImagesFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        mImageURI = result;
                        imgAvatar.setImageURI(result);
                    }
                    uploadImage(uploadtype);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        init();

        ArrayList<String> items = new ArrayList<>();
        items.add("Nam");
        items.add("Nữ");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        getInfo();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtype = "UsersAvatar/";
                AvatarpickImagesFromGallery.launch("image/*");
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        birthday.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void init(){

        fullname = findViewById(R.id.profile_input_fullname);
        address = findViewById(R.id.profile_input_address);
        city = findViewById(R.id.profile_input_city);
        btnUpdate = findViewById(R.id.btn_update);
        imgAvatar = findViewById(R.id.img_avatar_profile_input_fragment);
        spGender = findViewById(R.id.gender);

        //date of birth button
        //initDatePicker();
        dateButton = findViewById(R.id.profile_input_dateofbirth);
        birthday = findViewById(R.id.tvBirthDate);
        //dateButton.setText(getTodaysDate());

        dtb_user = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());
    }

    private void uploadImage(String type) {

        //Firebase
        FirebaseStorage storage;
        StorageReference storageReference;

        // Initialize storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(mImageURI != null)
        {
            imageID = UUID.randomUUID().toString();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(type + "/"+ imageID);
            ref.putFile(mImageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
/*
                            Toast.makeText(ProfileManagement.this, "Uploaded", Toast.LENGTH_SHORT).show();
*/
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
/*
                                    Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl, Toast.LENGTH_SHORT).show();
*/
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    /*private String getTodaysDate()  {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }*/

    /*private String getMonthFormat(int month) {
        if(month == 1)
            return "01";
        if(month == 2)
            return "02";
        if(month == 3)
            return "03";
        if(month == 4)
            return "04";
        if(month == 5)
            return "05";
        if(month == 6)
            return "06";
        if(month == 7)
            return "07";
        if(month == 8)
            return "08";
        if(month == 9)
            return "09";
        if(month == 10)
            return "10";
        if(month == 11)
            return "11";
        if(month == 12)
            return "12";

        //default should never happen
        return "01";
    }*/

    /*private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }*/

    private void getInfo(){

        dtb_user.collection("Users")
                .whereEqualTo("userID", user.getUserID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                fullname.setText(document.get("fullName").toString());
                                address.setText(document.get("address").toString());
                                city.setText(document.get("city").toString());
                                gender = document.get("gender").toString();
                                if (gender == "Nam")
                                {
                                    spGender.setSelection(0);
                                }
                                else
                                {
                                    spGender.setSelection(1);
                                }
                                birthday.setText(document.get("birthday").toString());

                                user.setAvatarURL(document.get("avatarURL").toString());
                                if (!document.get("avatarURL").toString().isEmpty()) {
                                    Picasso.get().load(user.getAvatarURL()).into(imgAvatar);
                                }
                                else {
                                    user.setAvatarURL("");
                                }

                            }
                        }
                        else {
                            //
                            Toast.makeText(UpdateProfileActivity.this, "Không thể lấy thông tin", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void updateInfo() {
        user.setFullName(fullname.getText().toString());
        user.setAddress(address.getText().toString());
        user.setCity(city.getText().toString());
        user.setBirthday(birthday.getText().toString());
        user.setAvatarURL(downloadUrl);
        user.setGender(spGender.getSelectedItem().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("fullName", user.getFullName());
        data.put("address", user.getAddress());
        data.put("city", user.getCity());
        data.put("birthday", user.getBirthday());
        data.put("gender", user.getGender());

        if (downloadUrl!=null)
        {
            data.put("avatarURL", user.getAvatarURL());
        }

        dtb_user.collection("Users").document(user.getUserID())
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
/*
                        Toast.makeText(ProfileManagement.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_LONG).show();
*/
                        Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, "Error updating document", Toast.LENGTH_LONG).show();
                    }
                });

    }
}