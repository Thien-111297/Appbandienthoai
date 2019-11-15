package vn.itplus.cuahangthietbi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.activity.MainActivity;
import vn.itplus.cuahangthietbi.model.GioHang;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @Override
    public int getCount() {
        return gioHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return gioHangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public Button btnTru,btnThem,btnTong;
        public TextView txtTenGioHang,txtGiaGioHang;
        public ImageView imgGioHang;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
         ViewHolder viewHolder = null;
        if(view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txtTenGioHang = view.findViewById(R.id.txtTenGioHang);
            viewHolder.txtGiaGioHang = view.findViewById(R.id.txtGiaGioHang);
            viewHolder.imgGioHang = view.findViewById(R.id.imgGioHang);
            viewHolder.btnThem = view.findViewById(R.id.btnThem);
            viewHolder.btnTong = view.findViewById(R.id.btnTong);
            viewHolder.btnTru = view.findViewById(R.id.btnTru);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTenGioHang.setText(gioHang.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(gioHang.giaSp)+ " Đ");
        Picasso.with(context).load(gioHang.getHinhAnhSp())
        .placeholder(R.drawable.no_image)
        .error(R.drawable.error)
        .into(viewHolder.imgGioHang);
        viewHolder.btnTong.setText(gioHang.getSoLuongSp()+"");
        int soLuong = Integer.parseInt(viewHolder.btnTong.getText().toString());
        if( soLuong >=10){
            viewHolder.btnThem.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        } else if( soLuong<=1){
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        } else if(soLuong >=1) {
            viewHolder.btnTru.setVisibility(View.VISIBLE);
            viewHolder.btnTong.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMN = Integer.parseInt(finalViewHolder.btnTong.getText().toString())+1;
                int soLuongHT = MainActivity.chiTietGioHang.get(i).getSoLuongSp();
                long giaHT = MainActivity.chiTietGioHang.get(i).getGiaSp();
                MainActivity.chiTietGioHang.get(i).setSoLuongSp(soLuongMN);
                long giaMoiNhat = (giaHT * soLuongMN)/ soLuongHT;
                MainActivity.chiTietGioHang.get(i).setGiaSp(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " Đ");
                vn.itplus.cuahangthietbi.activity.GioHang.EventUtils();
                if(soLuongMN > 9){
                    finalViewHolder.btnThem.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTong.setText(String.valueOf(soLuongMN));
                } else {
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTong.setText(String.valueOf(soLuongMN));
                }
            }
        });
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMN = Integer.parseInt(finalViewHolder.btnTong.getText().toString())-1;
                int soLuongHT = MainActivity.chiTietGioHang.get(i).getSoLuongSp();
                long giaHT = MainActivity.chiTietGioHang.get(i).getGiaSp();
                MainActivity.chiTietGioHang.get(i).setSoLuongSp(soLuongMN);
                long giaMoiNhat = (giaHT * soLuongMN)/ soLuongHT;
                MainActivity.chiTietGioHang.get(i).setGiaSp(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " Đ");
                vn.itplus.cuahangthietbi.activity.GioHang.EventUtils();
                if(soLuongMN < 2){
                    finalViewHolder.btnTru.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTong.setText(String.valueOf(soLuongMN));
                } else {
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTong.setText(String.valueOf(soLuongMN));
                }
            }
        });
        return view;
    }
}
