package vn.itplus.cuahangthietbi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.util.CheckConnection;
import vn.itplus.cuahangthietbi.util.Server;

public class ThongTinKhActivity extends AppCompatActivity {
    EditText edtTenKh,edtEmail,edtSdtKh;
    Button btnXacNhan,btnTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_kh);
        addControls();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edtTenKh.getText().toString().trim();
                final String sdt = edtSdtKh.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                if(ten.length()>0&&sdt.length()>0&&email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest =new StringRequest(Request.Method.POST, Server.duongdanDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String maDonHang) {
                            Log.d("madonhang",maDonHang);
                            if(Integer.parseInt(maDonHang)>0){
                                RequestQueue Queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.chiTietGioHang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"mời bạn tiếp tục mua hàng");
                                        }else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray  = new JSONArray();
                                        for( int i=0;i<MainActivity.chiTietGioHang.size();i++){
                                            JSONObject jsonObject =  new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",maDonHang);
                                                jsonObject.put("masanpham",MainActivity.chiTietGioHang.get(i).getIdSp());
                                                jsonObject.put("tensanpham",MainActivity.chiTietGioHang.get(i).getTenSp());
                                                jsonObject.put("giasanpham",MainActivity.chiTietGioHang.get(i).getGiaSp());
                                                jsonObject.put("soluongsanpham",MainActivity.chiTietGioHang.get(i).getSoLuongSp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                Queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Mời nhập đủ thông tin");
                }
            }
        });
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailKh);
        edtSdtKh = findViewById(R.id.edtSoDt);
        edtTenKh = findViewById(R.id.edtTenKh);
        btnTrove = findViewById(R.id.btnTroVe);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }
}
