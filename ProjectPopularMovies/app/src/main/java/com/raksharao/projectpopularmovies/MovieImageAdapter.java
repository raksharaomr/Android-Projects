package com.raksharao.projectpopularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by raksharaomr on 8/1/15.
 */
public class MovieImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> imagePaths;

    public MovieImageAdapter(Context context, List<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    public void updateImagePaths(List<String> imagePaths) {
        System.out.println(this.imagePaths);
        this.imagePaths.clear();
        this.imagePaths.addAll(imagePaths);
        System.out.println(this.imagePaths);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_view_item, null);
        } else {
            gridView = (View) convertView;
        }
        ImageView imageView = (ImageView) gridView.findViewById(R.id.gv_thumbnail_image_view);

        String fullPath = "http://image.tmdb.org/t/p/" + "w500" + imagePaths.get(position);
        Log.v("Adapter", fullPath);
        Picasso.with(context).load(fullPath).into(imageView);
        return gridView;
    }
}
