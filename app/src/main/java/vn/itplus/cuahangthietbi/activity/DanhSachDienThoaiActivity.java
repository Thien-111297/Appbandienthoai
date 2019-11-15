package vn.itplus.cuahangthietbi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.adapter.DienThoaiAdapter;
import vn.itplus.cuahangthietbi.model.Sanpham;
import vn.itplus.cuahangthietbi.util.CheckConnection;
import vn.itplus.cuahangthietbi.util.Server;

public class DanhSachDienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDienThoai;
    ListView lvDienThoai;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Sanpham>mangdt;
    int idDienThoai =0;
    int page = 1;
    View footerView;
    boolean isLoading;
    mHandler mhandler;
    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_dien_thoai);
        addControls();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIdLoaiSP();
            actionToolbar();
            getData(page);
            loadMoreData();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }

    }

    private void loadMoreData() {

        lvDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",mangdt.get(i));
                startActivity(intent);
            }
        });
        lvDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem!=0&&isLoading==false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String tenDt= "";
                int gia = 0;
                String hinhAnh ="";
                String mota = "";
                int idSpdt = 0;
                if(response!=null&&response.length() !=2){
                    lvDienThoai.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenDt = jsonObject.getString("tensp");
                            gia = jsonObject.getInt("giasp");
                            hinhAnh = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("motasp");
                            idSpdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id,tenDt,gia,hinhAnh,mota,idSpdt));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    lvDienThoai.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idsanpham",String.valueOf(idDienThoai));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarDienThoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIdLoaiSP() {
        idDienThoai = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idDienThoai+"");
    }

    private void addControls() {
        toolbarDienThoai    = findViewById(R.id.toolbarDienThoai);
        lvDienThoai         =findViewById(R.id.lvDienThoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangdt);
        lvDienThoai.setAdapter(dienThoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mhandler = new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvDienThoai.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading =false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Message message = mhandler.obtainMessage(1);
            mhandler.sendMessage(message);
            super.run();
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
