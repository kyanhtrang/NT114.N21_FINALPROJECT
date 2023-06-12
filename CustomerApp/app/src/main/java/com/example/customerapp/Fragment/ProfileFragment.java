package com.example.customerapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customerapp.R;
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

import java.util.Date;

class ProfileFragment extends Fragment {

    DatePicker textbirthday;
    TextView textfullname, textphonenum, textemail, textaddress;
    String fullname, phonenum, birthday, email, address;
    View view;
    FirebaseFirestore dtb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        dtb = FirebaseFirestore.getInstance();
        init();
        return view;
    }
    private void init(){
        dtb.collection("Users")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                fullname = document.getString("fullname");
                                phonenum = document.getString("phonenum");
                                birthday = document.getString("birth");
                                email    = document.getString("email");
                                address  = document.getString("address");
                                settext();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Can't get User's data");
                        Toast.makeText(getActivity().getBaseContext(), "Không thể lấy dữ liệu của người dùng",Toast.LENGTH_LONG).show();
                    }
                });

        textfullname    = (TextView) view.findViewById(R.id.editTextFullname);
        textphonenum    = (TextView) view.findViewById(R.id.editTextPhone2);
        textemail       = (TextView) view.findViewById(R.id.editTextTextEmailAddress3);
        textbirthday    = view.findViewById(R.id.datepicker_birthday);
        textaddress     = (TextView) view.findViewById(R.id.editTextUserAddress);
    }
    private void settext(){
        Integer year = Integer.valueOf(birthday.substring(0,2)) ,
                month = Integer.valueOf(birthday.substring(3,2)),
                day = Integer.valueOf(birthday.substring(7,4));

        textfullname.setText(fullname);
        textphonenum.setText(phonenum);
        textemail.setText(phonenum);
        textbirthday.updateDate(year, month, day);
        textaddress.setText(email);
    }
}