package vn.itplus.cuahangthietbi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.itplus.cuahangthietbi.R;
import vn.itplus.cuahangthietbi.model.Loaisp;

public class LoaiSanPhamAdapter extends BaseAdapter {
    private Context context;
    private List<Loaisp> loaispList;

    public LoaiSanPhamAdapter(Context context, List<Loaisp> loaispList) {
        this.context = context;
        this.loaispList = loaispList;
    }

    @Override
    public int getCount() {
        return loaispList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaispList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        ImageView imageviewLoaiSp ;
        TextView txtLoaiSp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder =null;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.listview_loaisp,null);
            holder.imageviewLoaiSp = view.findViewById(R.id.imageviewLoaiSp);
            holder.txtLoaiSp = view.findViewById(R.id.txtLoaiSp);
            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }
        Loaisp loaisp = (Loaisp) getItem(i);
        holder.txtLoaiSp.setText(loaisp.getTenLoaiSP());
        Picasso.with(context).load(loaisp.getHinhAnhLoaiSP())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.imageviewLoaiSp);

        return view;
    }
}
