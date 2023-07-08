package com.example.customerrenting.Services.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.Model.Activity;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SupplierActivityDetail extends AppCompatActivity {

    FirebaseFirestore dtb;
    Intent intent;
    String ProvideID, vehicle_id, vehiclename, vehicleprice, vehicleaddress, vehiclepickup, vehicledrop, totalcost;
    String CustomerID;
    String ActivityID,noti_status;
    ImageView vehicleImage;
    private Activity temp = new Activity();


    private ArrayList<Vehicle> ls = new ArrayList<Vehicle>();
    private TextView tv_id,name,email,phoneNumber, tv_status;// Thông tin nhà cung cấp
    private TextView tv_BrandCar,tv_Gia,tv_DiaDiem,pickup,dropoff,totalCost;// Thông tin xe
    private Button btn_xacnhan,btn_huy,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);
        intent = getIntent();

        String OrderID = intent.getStringExtra("NotiID");
        ActivityID = OrderID;

        init();

        dtb = FirebaseFirestore.getInstance();
        dtb.collection("Activity")
                .whereEqualTo("activity_id", ActivityID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

//                            Notification temp = new Notification();
                                temp.setActivity_id(document.getId());
                                temp.setCustomer_id(document.get("customer_id").toString());
                                temp.setVehicle_id(document.get("vehicle_id").toString());
                                temp.setStatus(document.get("status").toString());

                                vehiclepickup = document.get("pickup").toString();
                                vehicledrop = document.get("dropoff").toString();

                                CustomerID = temp.getCustomer_id();
                                vehicle_id = temp.getVehicle_id();
                                noti_status=temp.getStatus();

                                tv_id.setText(ActivityID);

                                if(noti_status.equals( "Dang cho"))
                                {
                                    tv_status.setText("Chưa được xác nhận");
                                }
                                else
                                {
                                    if(tv_status.equals( "Thanh toan"))
                                    {
                                        tv_status.setText("Đang chờ thanh toán");
                                    }
                                    else
                                    if(noti_status.equals("Xac nhan"))
                                    {
                                        tv_status.setText("Đã xác nhận");
                                    }
                                    else
                                    if (noti_status.equals("Khong duoc xac nhan"))
                                    {
                                        tv_status.setText("Không được xác nhận");
                                    }
                                    else {
                                        tv_status.setText("Đã thanh toán");
                                    }
                                }
                                getuser(CustomerID);
                                getvehicle(vehicle_id);

                            }
                        } else {
                            Toast.makeText(SupplierActivityDetail.this, "Không thể lấy thông báo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupplierActivityDetail.this, SupplierActivityDetail.class);
                startActivity(intent);
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_noti_huy();
            }
        });

        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update_noti_xacnhan();
            }
        });
    }
    private String calculate(String a, String b){
        int result = 0, day = 1, month = 1, year = 1;
        day = Integer.parseInt(b.substring(0,b.indexOf("/")));
        day = day - Integer.parseInt(a.substring(0,a.indexOf("/")));
        a = a.substring(a.indexOf("/")+1, a.length());
        b = b.substring(b.indexOf("/")+1, b.length());
        month = Integer.parseInt(b.substring(0,b.indexOf("/")));
        month = month - Integer.parseInt(a.substring(0,a.indexOf("/")));
        a = a.substring(a.indexOf("/")+1, a.length());
        b = b.substring(b.indexOf("/")+1, b.length());
        year = Integer.parseInt(b.substring(0,3)) - Integer.parseInt(a.substring(0,3));
        a = a.substring(4, a.length());
        b = b.substring(4, b.length());
        Log.e("day", String.valueOf(day));
        Log.e("month", String.valueOf(month));
        Log.e("year", String.valueOf(year));
        if (month < 0){
            month = 12;
            year--;
        }
        if (day < 0) {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                month--;
                day = 30;
                if (month == 2) day = 28;
            }
        }
        if (month < 0){
            month = 12;
            year--;
        }
        result = year * 365 + month * 30 + day;
        Log.e("Total Day", "Total Day is : " + String.valueOf(result));
        //Log.d("Price", "Vehicle Price is : "+ vehicleprice.substring(0, vehicleprice.indexOf(" VND")-1));
        String total = String.valueOf(result * Integer.parseInt(tv_Gia.getText().toString().substring(0, tv_Gia.getText().toString().indexOf(" "))));
        Log.e("Total", "Total price : " + total);
        return total;
    }
    private void getuser(String ProvideID){
        dtb.collection("Users")
                .whereEqualTo("userID", CustomerID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                User user = new User();
                                user.setUserID(document.get("userID").toString());
                                user.setFullName(document.get("fullName").toString());
                                user.setEmail(document.get("email").toString());
                                user.setPhoneNumber(document.get("phoneNumber").toString());
                                name.setText(user.getFullName());
                                email.setText(user.getEmail());
                                phoneNumber.setText(user.getPhoneNumber());
                            }
                        } else {
                            Toast.makeText(SupplierActivityDetail.this, "Không thể lấy thông tin nhà cung cấp", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getvehicle(String vehicle_id){
        dtb.collection("Vehicles")
                .whereEqualTo("vehicle_id", vehicle_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Vehicle temp = new Vehicle();
                                temp.setVehicle_id(document.getId());

                                vehiclename = document.get("vehicle_name").toString();
                                vehicleprice = document.get("vehicle_price").toString();
                                vehicleaddress = document.get("supplier_address").toString();

                                temp.setVehicle_name(document.get("vehicle_name").toString());
                                temp.setVehicle_availability(document.get("vehicle_availability").toString());
                                temp.setVehicle_price(document.get("vehicle_price").toString());


                                tv_BrandCar.setText(temp.getVehicle_name());
                                tv_Gia.setText(temp.getVehicle_price() + "/ngày");
                                tv_DiaDiem.setText(temp.getSupplier_address());
                                temp.setSupplier_address(document.get("supplier_address").toString());

                                tv_DiaDiem.setText(vehicleaddress);
                                pickup.setText(vehiclepickup);
                                dropoff.setText(vehicledrop);
                                totalcost = calculate(vehiclepickup, vehicledrop);
                                totalCost.setText(totalcost);

                                temp.setVehicle_imageURL(document.get("vehicle_imageURL").toString());
                                if (!document.get("vehicle_imageURL").toString().isEmpty()) {
                                    Picasso.get().load(temp.getVehicle_imageURL()).into(vehicleImage);
                                }
                                else {
                                    temp.setVehicle_imageURL("");
                                }
                            }
                        } else {
                            Toast.makeText(SupplierActivityDetail.this, "Không thể lấy thông tin xe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void update_noti_huy(){

        Map<String, Object> data = new HashMap<>();
        data.put("status", "Khong xac nhan");
        dtb.collection("Notification").document(temp.getActivity_id()).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SupplierActivityDetail.this, "Đã hủy đơn hàng", Toast.LENGTH_LONG).show();
                        tv_status.setText("Không được xác nhận");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SupplierActivityDetail.this, "Lỗi hủy đơn hàng", Toast.LENGTH_LONG).show();
                    }
                });

    }
    private void update_noti_xacnhan(){

        Map<String, Object> data = new HashMap<>();
        data.put("status", "Xac nhan");
        dtb.collection("Activity").document(temp.getActivity_id()).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SupplierActivityDetail.this, "Đã xác nhận", Toast.LENGTH_LONG).show();
                        tv_status.setText("Đã xác nhận");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SupplierActivityDetail.this, "Lỗi hủy đơn hàng", Toast.LENGTH_LONG).show();
                    }
                });

    }
    public void init(){
        tv_id=findViewById(R.id.txtview_noti_id);
        email=findViewById(R.id.txtview_noti_email);
        name=findViewById(R.id.txtview_noti_name);
        phoneNumber=findViewById(R.id.txtview_noti_phoneNumber);
        tv_BrandCar=findViewById(R.id.txtview_noti_BrandCar);
        tv_DiaDiem=findViewById(R.id.txt_checkout_address);

        tv_Gia=findViewById(R.id.txtview_noti_price);
        pickup=findViewById(R.id.tv_noti_pickup);
        dropoff=findViewById(R.id.tv_noti_dropoff);
        totalCost=findViewById(R.id.txtview_noti_totalCost);
        tv_status=findViewById(R.id.txtview_noti_status);

        btn_xacnhan=findViewById(R.id.btn_noti_XacNhan);
        btn_huy=findViewById(R.id.btn_noti_huy);
        btn_back=findViewById(R.id.btn_noti_back);
        vehicleImage=findViewById(R.id.img_noti_car);
    }
}