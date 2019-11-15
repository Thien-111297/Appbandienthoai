package vn.itplus.cuahangthietbi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.model.GioHang;
import vn.itplus.cuahangthietbi.model.Sanpham;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbaChiTietSanPham;
    ImageView imgChiTietSanPham;
    TextView txtTenSp,txtGiaSp,txtMoTaChiTiet;
    Spinner spinner;
    Button btnDatMua;
    int id = 0;
    String tenSp = "";
    int giaSp = 0;
    String hinhAnhSp = "";
    String moTaSp = "";
    int idSp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        addControls();
        actionToolBar();
        getData();
        SpinnerEvents();
        ButtonEvents();
        
    }

    private void ButtonEvents() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.chiTietGioHang.size()>0){
                    int s1 = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i = 0; i<MainActivity.chiTietGioHang.size();i++){
                        if(MainActivity.chiTietGioHang.get(i).getIdSp()==id){
                            MainActivity.chiTietGioHang.get(i).setSoLuongSp(MainActivity.chiTietGioHang.get(i).getSoLuongSp()+s1);
                            if(MainActivity.chiTietGioHang.get(i).getSoLuongSp()>=10){
                                MainActivity.chiTietGioHang.get(i).setSoLuongSp(10);
                            }
                            MainActivity.chiTietGioHang.get(i).setGiaSp(giaSp * MainActivity.chiTietGioHang.get(i).getSoLuongSp());
                            exists = true;
                        }
                    }
                    if(exists==false){
                        int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi = soLuong * giaSp;
                        MainActivity.chiTietGioHang.add(new GioHang(id,tenSp,giaMoi,hinhAnhSp,soLuong));
                    }
                } else {
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = soLuong * giaSp;
                    MainActivity.chiTietGioHang.add(new GioHang(id,tenSp,giaMoi,hinhAnhSp,soLuong));
                }
                Intent intent = new Intent(getApplicationContext(), vn.itplus.cuahangthietbi.activity.GioHang.class);
                startActivity(intent);
            }
        });
    }

    private void SpinnerEvents() {
        Integer [] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrSo = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,soLuong);
        spinner.setAdapter(arrSo);
    }

    private void getData() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        tenSp = sanpham.getTen();
        giaSp = sanpham.getGia();
        hinhAnhSp = sanpham.getHinhAnh();
        moTaSp = sanpham.getMoTa();
        idSp = sanpham.getIdSanPham();

        txtTenSp.setText(tenSp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSp.setText("Giá : "+ decimalFormat.format(giaSp)+ " Đ");
        txtMoTaChiTiet.setText(moTaSp);
        Picasso.with(getApplicationContext()).load(sanpham.getHinhAnh())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(imgChiTietSanPham);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbaChiTietSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbaChiTietSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addControls() {
        toolbaChiTietSanPham = findViewById(R.id.toolbaChiTietSanPham);
        imgChiTietSanPham = findViewById(R.id.imgChiTietSanPham);
        txtTenSp = findViewById(R.id.txtTenSp);
        txtGiaSp = findViewById(R.id.txtGiaSp);
        txtMoTaChiTiet = findViewById(R.id.txtMoTaChiTiet);
        spinner = findViewById(R.id.spinner);
        btnDatMua = findViewById(R.id.btnDatMua);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_giohang:
                Intent intent = new Intent(getApplicationContext(), vn.itplus.cuahangthietbi.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
