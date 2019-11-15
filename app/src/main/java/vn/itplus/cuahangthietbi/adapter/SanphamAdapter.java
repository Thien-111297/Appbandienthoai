package vn.itplus.cuahangthietbi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.activity.ChiTietSanPhamActivity;
import vn.itplus.cuahangthietbi.model.Sanpham;
import vn.itplus.cuahangthietbi.util.CheckConnection;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraySanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham_moi,null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham =arraySanpham.get(position);
        holder.txtTenSanPham.setText(sanpham.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá : "+ decimalFormat.format(sanpham.getGia())+ " Đ");
        Picasso.with(context).load(sanpham.getHinhAnh())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhsanpham;
        public TextView txtTenSanPham,txtGiaSanPham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imgSanpham);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSanpham);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham",arraySanpham.get(getPosition()));
                    CheckConnection.ShowToast_Short(context,arraySanpham.get(getPosition()).getTen());
                    context.startActivity(intent);
                }
            });


        }
    }
}
