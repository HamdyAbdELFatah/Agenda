package com.example.agenda;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ArrayList<DataClass> arr;
    ListView listView;
    ProgressBar progressBar;
    EditText booksearch;
    ImageView img;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arr=new ArrayList<>();
        listView=findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressBar);
        booksearch=findViewById(R.id.editText);
        btn=findViewById(R.id.button);
        img=findViewById(R.id.notebook);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyConnection().execute("https://www.googleapis.com/books/v1/volumes?q="+booksearch.getText());
            }
        });
    }
    class MyConnection extends AsyncTask<String,String,String> {

        public String title,authors,publisher,publishedDate,description,image;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                arr=new ArrayList<>();
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer s = new StringBuffer();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    s.append(temp);
                }
                JSONObject mainobject =new JSONObject(s.toString());
                JSONArray items=mainobject.getJSONArray("items");
                for(int i=0;i<items.length();i++){
                    JSONObject itemsobject=items.getJSONObject(i);
                    JSONObject volumeInfo=itemsobject.getJSONObject("volumeInfo");
                    if(volumeInfo.has("title")){
                     title=volumeInfo.getString("title");}
                    if(volumeInfo.has("authors")){
                        JSONArray authorsArray=volumeInfo.getJSONArray("authors");
                    s = new StringBuffer();
                    for (int j=0;j <authorsArray.length();j++)
                        s.append(authorsArray.getString(j)+"\n");
                     authors=s.toString();}
                    if(volumeInfo.has("publisher")){
                         publisher=volumeInfo.getString("publisher");}
                    if(volumeInfo.has("publishedDate")){
                         publishedDate=volumeInfo.getString("publishedDate");}
                    if(volumeInfo.has("description")){
                         description=volumeInfo.getString("description");}
                    if(volumeInfo.has("imageLinks")){
                        JSONObject imageLinks=volumeInfo.getJSONObject("imageLinks");
                     image=imageLinks.getString("thumbnail");}
                    arr.add(new DataClass(title,authors,publisher,publishedDate,description,image));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, ""+arr.size(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String t) {
            super.onPostExecute(t);
            if(arr.size()>0)
                img.setVisibility(View.INVISIBLE);
            else
                img.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            listView.setAdapter(   new Adapter(MainActivity.this,arr));

        }
    }
}