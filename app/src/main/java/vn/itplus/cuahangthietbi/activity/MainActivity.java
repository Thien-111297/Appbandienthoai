package vn.itplus.cuahangthietbi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.adapter.LoaiSanPhamAdapter;
import vn.itplus.cuahangthietbi.adapter.SanphamAdapter;
import vn.itplus.cuahangthietbi.model.GioHang;
import vn.itplus.cuahangthietbi.model.Loaisp;
import vn.itplus.cuahangthietbi.model.Sanpham;
import vn.itplus.cuahangthietbi.util.CheckConnection;
import vn.itplus.cuahangthietbi.util.Server;

public class MainActivity extends AppCompatActivity {
    String urlGetData = "http://192.168.1.8/server/getloaisanpham.php";
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp>loaisps;
    LoaiSanPhamAdapter adapter;
    int id =0;
    String tenLoaiSp ="";
    String hinhAnhLoaiSp ="";
    ArrayList<Sanpham>listSanPham;
    SanphamAdapter sanphamAdapter;

    public static ArrayList<GioHang> chiTietGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaiSp();
            GetDuLieuSanPhamMoiNhat();
            CactchOnItemListView();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    private void CactchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0 :
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mất internet rồi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DanhSachDienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",loaisps.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mất internet rồi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DanhSachLapTopActivity.class);
                            intent.putExtra("idloaisanpham",loaisps.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mất internet rồi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            intent.putExtra("idloaisanpham",loaisps.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mất internet rồi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            intent.putExtra("idloaisanpham",loaisps.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mất internet rồi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.duongdanSanPhamMoiNhat, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            int ID = 0;
                            String tenSanpham = "";
                            Integer giaSanPham = 0;
                            String hinhAnhSanPham = "";
                            String moTaSanPham ="";
                            int idSanPham = 0;
                            for(int i =0; i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ID = jsonObject.getInt("id");
                                    tenSanpham = jsonObject.getString("tensp");
                                    giaSanPham =jsonObject.getInt("giasp");
                                    hinhAnhSanPham =jsonObject.getString("hinhanhsp");
                                    moTaSanPham =jsonObject.getString("motasp");
                                    idSanPham =jsonObject.getInt("idsanpham");
                                    listSanPham.add(new Sanpham(ID,tenSanpham,giaSanPham,hinhAnhSanPham,moTaSanPham,idSanPham));
                                    sanphamAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaiSp() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Server.duongDanLoaiSp,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSp = jsonObject.getString("tenLoaisp");
                            hinhAnhLoaiSp = jsonObject.getString("hinhanhloaisp");
                            loaisps.add(new Loaisp(id,tenLoaiSp,hinhAnhLoaiSp));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loaisps.add(3,new Loaisp(0,"Liên Hệ","https://cdn1.iconfinder.com/data/icons/e-commerce-online-shopping-3-5/64/x-21-256.png"));
                    loaisps.add(4,new Loaisp(0,"Thông tin","https://cdn0.iconfinder.com/data/icons/internet-activity-3-1/32/exclamation_mark_sign-128.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void ActionViewFlipper() {
        // Đưa chuỗi link ảnh vào cho mảng
        ArrayList<String> listQuangCao = new ArrayList<>();
        listQuangCao.add("https://cdn.tgdd.vn/Files/2016/10/11/898359/galaxy-j7-prime-qc.jpg");
        listQuangCao.add("https://cdn.tgdd.vn/Files/2018/02/17/1067643/galaxys9vss9plus_800x450.jpg");
        listQuangCao.add("http://streaming1.danviet.vn/upload/1-2019/images/2019-02-20/HOT-Samsung-chinh-thuc-tung-video-quang-cao-Galaxy-S10-unnamed-1550656095-width660height330.jpg");
        listQuangCao.add("https://tintuc.viettelstore.vn/wp-content/uploads/2018/10/video-quang-cao-Galaxy-S9-1.jpg");
        listQuangCao.add("https://cdn.tgdd.vn/Files/2017/03/09/958798/son-tung-oppo-f3_800x450.jpg");
        for(int i =0; i<listQuangCao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(listQuangCao.get(i)).into(imageView);
            // Căn chỉnh image vs ViewFlipper
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        // Để viewflipper chạy
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in_right);
        viewFlipper.setOutAnimation(animation_out_right);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void addControls() {
        toolbar                     = findViewById(R.id.toolbarManHinhChinh);
        viewFlipper                 = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh    = findViewById(R.id.recycleViewmanhinhchinh);
        navigationView              = findViewById(R.id.navigtionview);
        listViewmanhinhchinh        = findViewById(R.id.listViewmanhinhchinh);
        drawerLayout                = findViewById(R.id.drawableLayout);
        loaisps = new ArrayList<>();
        loaisps.add(0,new Loaisp(0,"Trang chính","https://cdn3.iconfinder.com/data/icons/seo-marketing-42/64/x-14-256.png"));
        adapter = new LoaiSanPhamAdapter(getApplicationContext(),loaisps);
        listViewmanhinhchinh.setAdapter(adapter);

        listSanPham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),listSanPham);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewManHinhChinh.setAdapter(sanphamAdapter);

        if(chiTietGioHang !=null){

        } else {
            chiTietGioHang = new ArrayList<>();
        }
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
