package com.example.Workspace.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Workspace.R;
import com.example.Workspace.network.Workspace;
import com.example.Workspace.ui.detail.detailActivity;
import com.example.Workspace.ui.main.MainActivity;
import com.example.Workspace.utilities.GoogleApiUrl;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int previousPosition = 0;

    private List<Workspace> List_Item=new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(List<Workspace> list_Item, Context context) {
        List_Item = list_Item;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View menu11 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_list, viewGroup, false);
        return new MenuItemViewHolder(menu11);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;

            menuItemHolder.name.setText(List_Item.get(position).getPlaceName());
        menuItemHolder.rate.setText(List_Item.get(position).getPlaceRating().toString());
        if(List_Item.get(position).getPlaceOpeningHourStatus()=="false"){
        menuItemHolder.status.setText("Closed");
        }
        else{
            menuItemHolder.status.setText("Open");

        }



        Picasso.with(context)
                    .load(List_Item.get(position).getPhoto_reference())
                    .placeholder(R.drawable.place_co_workingspace)
                    .error(R.drawable.error_coworking)
                    .into(menuItemHolder.imageView);
        //showImage(List_Item.get(position).getPhoto_reference(),menuItemHolder.imageView);

    }
    private void showImage(final String uRL,ImageView image) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(uRL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bm = null;
                try {
                    bm = BitmapFactory.decodeStream(url.openConnection()
                            .getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int width = bm.getWidth();
                int height = bm.getHeight();
                float scaleWidth = ((float) 400) / width;
                float scaleHeight = ((float) 150) / height;
                // CREATE A MATRIX FOR THE MANIPULATION
                Matrix matrix = new Matrix();
                // RESIZE THE BIT MAP
                matrix.postScale(scaleWidth, scaleHeight);

                // "RECREATE" THE NEW BITMAP
                final   Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width,
                        height, matrix, false);
                ((MainActivity)context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        image.setImageBitmap(resizedBitmap);
                    }
                });
            }
        }).start();
    }
    //Viewmodel**
    public Workspace getmovieAt(int position){
        return List_Item.get(position);
    }

    //Viewmodel
    public void setmovies(List<Workspace> Workspaces) {
        this.List_Item = Workspaces;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return (null != List_Item ? List_Item.size() : 0);
    }


    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView name;
        private TextView rate;
        private TextView status;

        public MenuItemViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardview_place);
            imageView = (ImageView) view.findViewById(R.id.place_imageView);
            name = (TextView) view.findViewById(R.id.place_name_textView);
            rate=(TextView)view.findViewById(R.id.place_rating_textView);
            status = (TextView) view.findViewById(R.id.place_open_textView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=getAdapterPosition();
                    if(i!=RecyclerView.NO_POSITION){
                        Workspace Workspaceclicked=List_Item.get(i);
                        Intent intent=new Intent(context, detailActivity.class);
                        intent.putExtra(GoogleApiUrl.LOCATION_ID_EXTRA_TEXT, List_Item.get(i).getPlaceId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
