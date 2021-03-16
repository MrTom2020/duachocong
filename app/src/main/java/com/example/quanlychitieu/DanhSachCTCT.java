package com.example.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DanhSachCTCT extends AppCompatActivity {
    public  static final int sua=1;
    public  static final int luusua=2;

     ListView lv;
    EditText txt;
    ArrayList<ChiTieuThuChi> arrayList=new ArrayList<>();
    ArrayAdapter<ChiTieuThuChi_ApdTer> arrayAdapter=null;
    ChiTieuThuChi_ApdTer apdTer;
    int posselected=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_c_t_c_t);
        BanDau();
        ax();
        registerForContextMenu(lv);

    }
    public void ax()
    {
        apdTer= new ChiTieuThuChi_ApdTer(DanhSachCTCT.this,R.layout.dongchitieuchitiet,arrayList);
        lv.setAdapter(apdTer);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                posselected=i;
                // Toast.makeText(DanhSachCTCT.this,"anh sa",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        apdTer.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.viewmnu :
                intent =new Intent(DanhSachCTCT.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.editmnu :
                 intent=new Intent(DanhSachCTCT.this,ChiTieuThuChi.class);
                 startActivityForResult(intent,DanhSachCTCT.sua);
                break;
            default:

        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }
    public void BanDau(){
            Intent intent=getIntent();
            Bundle bundle=intent.getBundleExtra("idMaLoaiCT");
            int id=bundle.getInt("idMaLoaiChiTieu");
         txt=(EditText)findViewById(R.id.txtTen_DSCTCT);
        lv=(ListView)findViewById(R.id.lvDSCTCT_DanhSachCTCT);
       DataBase ddBase = new DataBase(DanhSachCTCT.this,"DanhSachTenChiTieu.sqlite",null,1);
        String sql="SELECT tct.TenChiTieu,ct.Id,ct.TenCTCT,ct.TienChi,ct.NgayThanhLap,tct.LoaiChiTieu from ChiTiet ct, TenChiTieu tct WHERE ct.IdTenLoaiChiTieu=tct.Id";
      //  String sql2="select * from TenChiTieu ";
        Cursor cursor=ddBase.GetData(sql);
        String x="";
        while (cursor.moveToNext()){
           String tenChiTieu=cursor.getString(0);
           int idCTCT=cursor.getInt(01);
           String tenCTCT=cursor.getString(2);
           Double soTien=cursor.getDouble(3);
           String ngay=cursor.getString(4);
           int lCTCT=cursor.getInt(5);
           ChiTieuThuChi ct=new ChiTieuThuChi(tenChiTieu,idCTCT,tenCTCT,soTien,ngay,lCTCT);
           arrayList.add(ct);
        }
        txt.setText(id+"");
        registerForContextMenu(lv);


    }
}