package com.huji.foodtricks.foodtricks;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by User on 4/4/2017.
 */

public class CustomListAdapter  extends ArrayAdapter<RecipeCard> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        ImageView image;
        TextView description;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public CustomListAdapter(Context context, int resource, ArrayList<RecipeCard> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the information
        String title = Objects.requireNonNull(getItem(position)).getTitle();
        String imgUrl = Objects.requireNonNull(getItem(position)).getImageURL();
        final String url = Objects.requireNonNull(getItem(position)).getRecipeURL();
        int duration = Objects.requireNonNull(getItem(position)).getDuration();
        int difficulty = (int) Objects.requireNonNull(getItem(position)).getDifficulty();


        try{


            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
//                convertView = inflater.inflate(mResource, null);
                holder= new ViewHolder();
                holder.title = convertView.findViewById(R.id.cardTitle);
                holder.image = convertView.findViewById(R.id.cardImage);
                holder.description = convertView.findViewById(R.id.cardDescription);

                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(v.getContext(),WebViewActivity.class);
                    browserIntent.putExtra("url",url);
                    mContext.startActivity(browserIntent);
                }
            });


//            Animation animation = AnimationUtils.loadAnimation(mContext,
//                    (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
//            result.startAnimation(animation);
//            lastPosition = position;

            holder.title.setText(title);
            String descriptionText = "Recipe Difficulty: " + difficulty;
            int hours = duration / 60;
            int minutes = duration % 60;
            String secondDigit = "";
            if (minutes < 10){
                secondDigit = "0";
            }
            if (duration > 0){
                descriptionText += "\nPreparation Time: " + hours + ":" + secondDigit + minutes;
            }
            holder.description.setText(descriptionText);

            //create the imageloader object
            ImageLoader imageLoader = ImageLoader.getInstance();

            int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .showImageOnLoading(defaultImage).build();

            //download and display image from url
            imageLoader.displayImage(imgUrl, holder.image, options);

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }

    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
    }
}
