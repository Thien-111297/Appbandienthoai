package vn.itplus.cuahangthietbi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.model.Sanpham;

public class LaptopAdapter extends BaseAdapter {
    private Context context;
    private List<Sanpham> sanphamLapTop;

    public LaptopAdapter(Context context, List<Sanpham> sanphamLapTop) {
        this.context = context;
        this.sanphamLapTop = sanphamLapTop;
    }

    @Override
    public int getCount() {
        return sanphamLapTop.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamLapTop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtTenLt,txtGiaLt,txtMotaLt;
        ImageView imgLapTop;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LaptopAdapter.ViewHolder holder = null;
        if(view==null){
            holder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanpham_laptop,null);
            holder.txtTenLt = view.findViewById(R.id.txtTenLt);
            holder.txtGiaLt = view.findViewById(R.id.txtGiaLt);
            holder.txtMotaLt = view.findViewById(R.id.txtMotaLt);
            holder.imgLapTop = view.findViewById(R.id.imgLapTop);
            view.setTag(holder);
        }else {
            holder = (LaptopAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        holder.txtTenLt.setText(sanpham.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaLt.setText("Giá : "+ decimalFormat.format(sanpham.getGia())+ " Đ");
        // cho phép hiển thị 2 dòng
        holder.txtMotaLt.setMaxLines(2);
        // cho phép thể hiện còn tiếp (...)
        holder.txtMotaLt.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtMotaLt.setText(sanpham.getMoTa());
        Picasso.with(context).load(sanpham.getHinhAnh())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.imgLapTop);
        return view;
    }
}
