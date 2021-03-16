package com.example.quanlychitieu.ThemXoaSua_LietKeThongKe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.quanlychitieu.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThongKeChiTieu extends AppCompatActivity {
    private String ngayTK;
    private EditText ngayBD,ngayKT;
    Button btnDuyet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_chi_tieu);

    }
    public void add(){

    }
    private void chonNgay(final int loai){
        //khai báo ngày tùy biến theo thời gian thực
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        //xây dựng DatePicker
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //gán thời gian người dùng lựa chọn
                calendar.set(year,month,dayOfMonth);
                //định dạng ngày theo kiểu ngày / tháng / năm
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                //đưa dữ liệu vào edittext
                if(loai==1)
                {
                   ngayTK=simpleDateFormat.format(calendar.getTime());
                }
                else
                {
                    ngayTK=simpleDateFormat.format(calendar.getTime());
                }
            }
        },nam,thang,ngay);
        //hiển thị DatePicker
        datePickerDialog.show();
    }
}