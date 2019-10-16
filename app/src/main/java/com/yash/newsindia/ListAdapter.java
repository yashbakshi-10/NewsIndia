package com.yash.newsindia;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {



    List<Article> articles;

    RequestOptions requestOptions = RequestOptions
            .diskCacheStrategyOf(DiskCacheStrategy.ALL);


    public ListAdapter(List<Article> articles) {
        this.articles = articles;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.one_card, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder listViewHolder, final int i) {

        Glide.with(listViewHolder.img.getContext())
                .load(articles.get(i).getUrlToImage())
                .error(R.drawable.newspaper_img)
                .apply(requestOptions)
                .into(listViewHolder.img);

        listViewHolder.title.setText(articles.get(i).getTitle());


        listViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent webIntent = new Intent(v.getContext(), Web.class);

                webIntent.putExtra("url", articles.get(i).getUrl());

                v.getContext().startActivity(webIntent);
            }
        });

        listViewHolder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Context context=v.getContext();
                ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("url",articles.get(i).getUrl());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(context, "Link Copied", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView title;
        RelativeLayout parent;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.article);
            parent = itemView.findViewById(R.id.parent);



        }
    }
}
