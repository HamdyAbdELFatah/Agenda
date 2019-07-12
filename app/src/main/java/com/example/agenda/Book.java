package com.example.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class Book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent i=getIntent();
        TextView title=findViewById(R.id.title);
        TextView author=findViewById(R.id.author);
        TextView desc=findViewById(R.id.description);
        TextView date=findViewById(R.id.date);
        TextView publisher=findViewById(R.id.textView3);
        ImageView img=findViewById(R.id.book_img);
        title.setText(i.getStringExtra("title"));
        author.setText(i.getStringExtra("author"));
        publisher.append(i.getStringExtra("publisher"));
        desc.setText(i.getStringExtra("description"));
        date.append(i.getStringExtra("date"));
        Picasso.get().load(i.getStringExtra("img")).into(img);
    }
}
