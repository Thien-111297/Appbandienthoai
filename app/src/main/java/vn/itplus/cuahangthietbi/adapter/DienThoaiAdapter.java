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

public class DienThoaiAdapter extends BaseAdapter {
    private Context context;
    private List<Sanpham>sanphamDienThoai;

    public DienThoaiAdapter(Context context, List<Sanpham> sanphamDienThoai) {
        this.context = context;
        this.sanphamDienThoai = sanphamDienThoai;
    }

    @Override
    public int getCount() {
        return sanphamDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtTenDt,txtGiaDt,txtMotaDt;
        ImageView imgDienThoai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanpham_dienthoai,null);
            holder.txtTenDt = view.findViewById(R.id.txtTenDt);
            holder.txtGiaDt = view.findViewById(R.id.txtGiaDt);
            holder.txtMotaDt = view.findViewById(R.id.txtMotaDt);
            holder.imgDienThoai = view.findViewById(R.id.imgDienThoai);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        holder.txtTenDt.setText(sanpham.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaDt.setText("Giá : "+ decimalFormat.format(sanpham.getGia())+ " Đ");
        // cho phép hiển thị 2 dòng
        holder.txtMotaDt.setMaxLines(2);
        // cho phép thể hiện còn tiếp (...)
        holder.txtMotaDt.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtMotaDt.setText(sanpham.getMoTa());
        Picasso.with(context).load(sanpham.getHinhAnh())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.imgDienThoai);
        return view;
    }
}
