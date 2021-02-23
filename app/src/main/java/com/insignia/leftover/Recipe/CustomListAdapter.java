package com.insignia.leftover.Recipe;

import android.content.Context;

import android.content.Intent;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.insignia.leftover.Favourites.FavActivity;
import com.insignia.leftover.Favourites.FavData;
import com.insignia.leftover.Favourites.RoomFavDB;
import com.insignia.leftover.R;
import com.insignia.leftover.WebViewActivity;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by User on 4/4/2017.
 */

public class CustomListAdapter  extends ArrayAdapter<RecipeCard> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    int fav_on;
    String descriptionText;
    RoomFavDB db = RoomFavDB.getInstance(getContext());
    List<FavData> favDataList ;
    /**
     * Holds variables in a View
     */
    private static class ViewHolder {

        TextView title;
        ImageView image;
        ImageButton imgButton;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the information
        final String title = Objects.requireNonNull(getItem(position)).getTitle();
        final String imgUrl = Objects.requireNonNull(getItem(position)).getImageURL();
        final String url = Objects.requireNonNull(getItem(position)).getRecipeURL();
        final int duration = Objects.requireNonNull(getItem(position)).getDuration();
        final int difficulty = (int) Objects.requireNonNull(getItem(position)).getDifficulty();


        try{


            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            final ViewHolder holder;
            fav_on=0;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
//                convertView = inflater.inflate(mResource, null);
                holder= new ViewHolder();
                holder.title = convertView.findViewById(R.id.cardTitle);
                holder.image = convertView.findViewById(R.id.cardImage);
                holder.description = convertView.findViewById(R.id.cardDescription);
                holder.imgButton = convertView.findViewById(R.id.fav);
                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            favDataList = db.favDao().getAll();
            for(int x=0 ; x < favDataList.size() ; x++){
                if(title.equals(favDataList.get(x).getTitle())){
                    holder.imgButton.setImageResource(R.drawable.ic_baseline_star_24);
                    fav_on++;
                }
            }
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(v.getContext(), WebViewActivity.class);
                    browserIntent.putExtra("url",url);
                    mContext.startActivity(browserIntent);
                }
            });
            holder.imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( fav_on == 0){
                        holder.imgButton.setImageResource(R.drawable.ic_baseline_star_24);
                        fav_on++;
                        FavData favData = new FavData(title,imgUrl,url,duration,difficulty);
                        db.favDao().insert(favData);
                    }else{
                        holder.imgButton.setImageResource(R.drawable.ic_baseline_star_border_24);
                        db.favDao().delTitle(title);
                        fav_on--;
                    }

                }
            });


//            Animation animation = AnimationUtils.loadAnimation(mContext,
//                    (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
//            result.startAnimation(animation);
//            lastPosition = position;

            holder.title.setText(title);
            descriptionText = "Recipe Difficulty: " + difficulty;
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
