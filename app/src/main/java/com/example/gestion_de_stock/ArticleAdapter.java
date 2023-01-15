package com.example.gestion_de_stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestion_de_stock.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private static final String TAG = "BulletinListAdapter";
    private Context mContext;
    int mResource;
    /**
     * Default constructor for the BulletinListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ArticleAdapter(Context context, int resource, ArrayList<Article> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer id = getItem(position).getId();
        String libelle = getItem(position).getLibelle();
        Double p_u = getItem(position).getPrix_unitaire();


        Article bulletin = new Article(id, libelle, p_u , null);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvId = (TextView) convertView.findViewById(R.id.id);
        TextView tvLibelle = (TextView) convertView.findViewById(R.id.libelle);
        TextView tvP_u = (TextView) convertView.findViewById(R.id.p_u);

        tvId.setText(String.valueOf(id));
        tvLibelle.setText(libelle);
        tvP_u.setText(String.valueOf(p_u));

        return convertView;
    }
}