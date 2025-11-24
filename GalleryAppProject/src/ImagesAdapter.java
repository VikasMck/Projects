package com.example.mobileassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


//class for dealing with images
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    //declarations
    private List<ImageData> images_list;
    //context is needed to handle glide images
    private Context context;
    private OnItemClickListener onItemClickListener;

    //constructor
    public ImagesAdapter(List<ImageData> images, Context context) {
        this.images_list = images;
        this.context = context;
    }

    //functions that adds to the image list
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ImageData> newImages) {
        images_list.clear();
        images_list.addAll(newImages);
        notifyDataSetChanged();
    }

    //this is needed for the images to be clickable as they are added
    public interface OnItemClickListener {
        void onItemClick(int position, ImageData imageData);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //nonnull method which is required by the extend
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //using the item_image xml file make it into a workable variable, makes it easier to work with
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    //must have also
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageData imageData = images_list.get(position);

        //glide is what I use to load images into my app which is then put into the imageView
        Glide.with(context).load(imageData.getImage_url()).into(holder.image_view);

        //set the tag
        holder.tag_view.setText(imageData.getImage_tag());

        //make each image clickable
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, imageData);
            }
        });
    }

    //not used but necessary from the import
    @Override
    public int getItemCount() {
        return images_list.size();
    }

    //main class that has the main variables
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image_view;
        private final TextView tag_view;

        public ViewHolder(@NonNull View item_view) {
            super(item_view);
            image_view = item_view.findViewById(R.id.image_view_id);
            tag_view = item_view.findViewById(R.id.txt_image_page_title_id);
        }
    }
}
