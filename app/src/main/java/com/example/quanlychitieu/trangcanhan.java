package com.example.quanlychitieu;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class trangcanhan extends AppCompatActivity {
    private EditText edtten, edtmk, edtdc, edtns;
    private Button btndy, btnluu;
    private Intent intent;
    private Bundle bundle;
    private Cursor cursor;
    private DataBase dl;
    private int kk = 0;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<thongtinnguoidung> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangcanhan);
        dangkynut();
        dangkysukien();
        taodt();
        ax();

    }

    private void dangkynut() {
        edtten = (EditText) findViewById(R.id.edttencanhan);
        edtmk = (EditText) findViewById(R.id.edtmkcanhan);
        edtdc = (EditText) findViewById(R.id.edtdiachicanhan);
        edtns = (EditText) findViewById(R.id.edtngaysinhcanhan);
        btndy = (Button) findViewById(R.id.btndycn);
        btnluu = (Button) findViewById(R.id.btnluucanhan);
    }

    private void dangkysukien() {
        btnluu.setOnClickListener(new sukiencuatoi());
        btndy.setOnClickListener(new sukiencuatoi());
    }

    private void sukiendong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(trangcanhan.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thoát");
        builder.setPositiveButton("Vâng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog1 = builder.create();
        dialog1.show();
    }

    private void taodt() {
        String ssa = dangnhap.tend.toString();
        arrayList = new ArrayList<>();
        dl = new DataBase(trangcanhan.this, "dulieunguoidung.sqlite", null, 1);
        dl.QueryData("CREATE TABLE IF NOT EXISTS nguoidung(ID VARCHAR(50) PRIMARY KEY,ten VARCHAR(50),matkhau VARCHAR(100),ngaysinh VARCHAR(20),diachi VARCHAR(200))");
        cursor = dl.GetData("SELECT * FROM nguoidung WHERE ID='" + ssa + "'");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String ten = cursor.getString(1).toString();
                String mk = cursor.getString(2).toString();
                String ngaysinh = cursor.getString(3).toString();
                String diachi = cursor.getString(4).toString();
                arrayList.add(new thongtinnguoidung(ten, mk, ngaysinh, diachi));
                kk = 4;
            }
        }
        DoDuLieu();
    }

    private void DoDuLieu() {
        for (int i = 0; i < arrayList.size(); ++i) {
            Toast.makeText(trangcanhan.this, "OK2", Toast.LENGTH_SHORT).show();
            edtten.setText(arrayList.get(i).getHoten().toString());
            edtmk.setText(arrayList.get(i).getMatkhau().toString());
            edtns.setText(arrayList.get(i).getNgaysinh().toString());
            edtdc.setText(arrayList.get(i).getDiachi().toString());
        }
    }

    private void ax() {
        arrayList = new ArrayList<>();
        String id = dangnhap.tend.toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("dangnhap");
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Ma = snapshot.getKey().toString();
                String Ten = snapshot.child("Tên").getValue().toString();
                String Mk = snapshot.child("Mật khẩu").getValue().toString();
                String diachi = snapshot.child("Địa chỉ").getValue().toString();
                String ngaysinh = snapshot.child("Ngày sinh").getValue().toString();
                if (kk == 0) {
                    dl.QueryData("INSERT INTO nguoidung VALUES('" + Ma + "','" + Ten + "','" + Mk + "','" + ngaysinh + "','" + diachi + "')");
                }

                arrayList.add(new thongtinnguoidung(Ten, Mk, ngaysinh, diachi));
                for (int i = 0; i < arrayList.size(); ++i) {
                    edtten.setText(arrayList.get(i).getHoten().toString());
                    edtmk.setText(arrayList.get(i).getMatkhau().toString());
                    edtns.setText(arrayList.get(i).getNgaysinh().toString());
                    edtdc.setText(arrayList.get(i).getDiachi().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private class sukiencuatoi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.equals(btndy)) {
                sukiendong();
            }
            if (view.equals(btnluu)) {

            }
        }
    }
}