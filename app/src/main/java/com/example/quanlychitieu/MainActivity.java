package com.example.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.quanlychitieu.ThemXoaSua_LietKeThongKe.ThemLoaiChiTieu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    static String arr[]={"Nhận lương","Đi chợ","Bán quần áo",
            "Tiền học","Tiền Trợ cấp","Tiền tình yêu",
    };

    public static final int ThemChiTieu=0;
    public static final int LuuThemChiTieu=1;
   private   DataBase ddBase;
    private GridView grList;
    private   EditText txtLayDl;
    private Button btnButton;
    private ArrayList<TenLoaiChiTieu> arrayList=new ArrayList<>();
    private  ArrayList<String> arrayListTen=new ArrayList<>();
    private ArrayAdapter<TenLCT_AdpTer> arrayAdapter;
    private  ArrayAdapter<String> arrayAdapterTen=null;
    private  TenLCT_AdpTer adpTer;
    private String adpTerTen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dangkynut();
        dangkysukien();
        registerForContextMenu(grList);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.ThemChiTieu && data != null)
        {
            if(resultCode == MainActivity.LuuThemChiTieu)
            {
                Bundle bundle=data.getBundleExtra("data");
                String ten=bundle.getString("TenChiTieu");
                int LCT=bundle.getInt("LoaiCT");
                String sql="insert into TenChiTieu VALUES(Null,"+LCT+",'"+ten+"')";

                ddBase.QueryData(sql);
                Cursor dd = ddBase.GetData("select * from TenChiTieu ");
                 if(dd.moveToLast())
                 {
                     String tenn=dd.getString(2);
                    int id=dd.getInt(0);
                    int idLCT=dd.getInt(1);
                    arrayList.add(new TenLoaiChiTieu(tenn,id,idLCT));
                }
                adpTer.notifyDataSetChanged();

            }
        }
    }
    public void dd(GridView gr)
    {
        gr.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

            }
        });
    }
    private void dangkynut()
    {
        grList = (GridView) findViewById(R.id.grList_main);
        btnButton = (Button) findViewById(R.id.btn_main);
        txtLayDl =(EditText) findViewById(R.id.txtLayDuLieu_main);
    }
     private void dangkysukien()
    {
        btnButton.setOnClickListener(new sukiencuatoi());
    }
    private class sukiencuatoi implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if(view.equals(btnButton))
            {

            }
        }
    }
    private void OnCreateData()
    {
        ddBase = new DataBase(this,"DanhSachTenChiTieu.sqlite",null,1);
        ddBase.QueryData("CREATE TABLE IF NOT EXISTS ChiTiet(Id integer primary key AUTOINCREMENT,IdTenLoaiChiTieu integer,TenCTCT Text ,NgayThanhLap Text, TienChi REAL )");
        ddBase.QueryData("CREATE TABLE IF NOT EXISTS TenChiTieu(Id integer primary key AUTOINCREMENT,LoaiChiTieu integer,TenChiTieu Text)");
    }
    private void OpenData()
    {
        Cursor dd = ddBase.GetData("select * from TenChiTieu ");
        if(dd != null) {
            if (dd.getCount() < 2) {
                for (int i = 0; i < arr.length; i++) {
                    arrayListTen.add("áhadb");
                    if (i % 2 == 0) {
                        ddBase.QueryData("INSERT into TenChiTieu VALUES(NULL,1,'" + arr[i].toString() + "')");
                    } else {
                        ddBase.QueryData("INSERT into TenChiTieu VALUES(NULL,0,'" + arr[i].toString() + "')");
                    }
                }
            }
            dd = ddBase.GetData("select * from TenChiTieu ");
            while (dd.moveToNext()) {
                String ten = dd.getString(2);
                int id = dd.getInt(0);
                int idLCT = dd.getInt(1);
                arrayList.add(new TenLoaiChiTieu(ten, id, idLCT));
            }
            dd = ddBase.GetData("select * from ChiTiet ");
            String s = "";
            while (dd.moveToNext()) {
                String ten = dd.getString(2);
                s = s + ten + "-";

            }
            txtLayDl.setText(s);
            dd = ddBase.GetData("select * from ChiTiet ");
            if (dd.getCount() < 1) {
                ddBase.QueryData("INSERT into ChiTiet VALUES (NULL ,1,'ăn tối','12/05/20000',4000000)");
                ddBase.QueryData("INSERT into ChiTiet VALUES (NULL ,4,'Trúng số','12/05/20000',3000000)");
            }
        }
    }

}