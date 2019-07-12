package com.example.agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class Adapter extends BaseAdapter {

    ArrayList<DataClass> arr;
    private TextView title;
    private TextView author;
    private ImageView img;
    Context context;
    public Adapter(Context context,ArrayList<DataClass> arr) {
        this.arr = arr;
        this.context=context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).
                inflate(R.layout.list_items, parent, false);
        title=v.findViewById(R.id.book_title);
        author=v.findViewById(R.id.book_Author);
        img=v.findViewById(R.id.book_imaage);
        title.setText(arr.get(i).title);
        author.setText(arr.get(i).author);
        Picasso.get().load(arr.get(i).imageurl).placeholder(R.drawable.ic_notebook).into(img);
        LinearLayout linearLayout=v.findViewById(R.id.Container);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Book.class);
                intent.putExtra("title",arr.get(i).title);
                intent.putExtra("author",arr.get(i).author);
                intent.putExtra("publisher",arr.get(i).publisher);
                intent.putExtra("description",arr.get(i).description);
                intent.putExtra("img",arr.get(i).imageurl);
                intent.putExtra("date",arr.get(i).date);
                v.getContext().startActivity(intent);
            }
        });
        return  v;
    }
}