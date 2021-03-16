package com.example.quanlychitieu.ThemXoaSua_LietKeThongKe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quanlychitieu.DataBase;
import com.example.quanlychitieu.MainActivity;
import com.example.quanlychitieu.R;

public class ThemLoaiChiTieu extends AppCompatActivity {
    RadioGroup radioGroup;
    EditText txtTen;
    Button btnOk;
    int idLoaiChitieu;
    String tenChiTieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_chi_tieu);
        BanDau();
        Intent intent=getIntent();
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =radioGroup.getCheckedRadioButtonId();
                if(id==0){

                    idLoaiChitieu =1;
                }
                else{
                    idLoaiChitieu =0;
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                tenChiTieu=txtTen.getText().toString().trim().trim();
                int x=tenChiTieu.length();
                if(tenChiTieu.length()>0){
                    bundle.putString("TenChiTieu",tenChiTieu);
                    bundle.putInt("LoaiCT",idLoaiChitieu );
                    intent.putExtra("data",bundle);
                  //  DataBase ddBase=new DataBase(ThemLoaiChiTieu.this,"DanhSachTenChiTieu.sqlite",null,1);;
                   // String sql="insert into TenChiTieu VALUES(Null,"+idLoaiChitieu+",'"+tenChiTieu+"')";
                 //   ddBase.QueryData(sql);
                    setResult(MainActivity.LuuThemChiTieu,intent);
                    finish();
                }
                else{
                    Toast.makeText(ThemLoaiChiTieu.this, "Bạn chưa nhập tên chi tiêu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void BanDau(){
        radioGroup=(RadioGroup)findViewById(R.id.raGr_ThemLCT);
        txtTen=(EditText)findViewById(R.id.txtTenChiTieu_ThemChiTieu);
        btnOk=(Button)findViewById(R.id.btnXacNhan_ThemLCT);

    }
}