package vn.itplus.cuahangthietbi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.adapter.GioHangAdapter;
import vn.itplus.cuahangthietbi.util.CheckConnection;

public class GioHang extends AppCompatActivity {
    ListView lvGioHang;
    static TextView txtThanhToan;
    TextView txtThongBao;
    Toolbar toolbarGioHang;
    Button btnThanhToan,btnTiepTuc;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        addControls();
        ActionBar();
        CheckData();
        EventUtils();
        EventButton();

    }

    private void EventButton() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.chiTietGioHang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhActivity.class);
                    startActivity(intent);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng trống!!!");
                }
            }
        });
    }

    private void addEventListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.chiTietGioHang.size()<=0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.chiTietGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUtils();
                            if(MainActivity.chiTietGioHang.size()<=0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBao.setVisibility(View.VISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUtils();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUtils();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    public static void EventUtils() {
        long tongTien = 0;
        for (int i=0;i<MainActivity.chiTietGioHang.size();i++){
            tongTien += MainActivity.chiTietGioHang.get(i).getGiaSp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtThanhToan.setText(decimalFormat.format(tongTien)+ " Đ");
    }

    private void CheckData() {
        if(MainActivity.chiTietGioHang.size() <=0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void addControls() {
        lvGioHang       = findViewById(R.id.lvGioHang);
        txtThanhToan    = findViewById(R.id.txtThanhToan);
        txtThongBao     = findViewById(R.id.txtThongBao);
        btnThanhToan    = findViewById(R.id.btnThanhToan);
        btnTiepTuc      = findViewById(R.id.btnTiepTuc);
        toolbarGioHang  = findViewById(R.id.toolbarGioHang);
        gioHangAdapter  = new GioHangAdapter(GioHang.this,MainActivity.chiTietGioHang);
        lvGioHang.setAdapter(gioHangAdapter);
        addEventListView();
    }
    private void ActionBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
