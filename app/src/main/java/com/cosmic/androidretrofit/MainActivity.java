package com.cosmic.androidretrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        getPosts();
        getComments();
        getOnePost();
        createPost();
        updatePost();
        patchPost();
        deletePost();
    }

    private void deletePost(){
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code : " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(errorMessage(t));
            }
        });
    }

    private void patchPost(){
        Posts posts = new Posts(20, "New Title", "Patched Text");
        Map<String, String> headers = new HashMap<>();
        headers.put("type", "application/json");
        headers.put("object-type", "post");
        headers.put("programmer", "Adam");
        Call<Posts> call = jsonPlaceHolderApi.patchPost(headers, 5, posts);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code Response: " + response.code());
                    return;
                }
                textViewResult.setText(parseResponse(response));
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                textViewResult.setText(errorMessage(t));
            }
        });
    }

    private void updatePost(){
        Posts posts = new Posts(15, null, "Replaced Text");
        Call<Posts> call = jsonPlaceHolderApi.putPost("My User ID: 001",5, posts);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code Response: " + response.code());
                    return;
                }
                textViewResult.append(parseResponse(response));
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                textViewResult.setText(errorMessage(t));
            }
        });
    }

    private void createPost(){
        String _weatherPrayer = "Almighty and most merciful Father, we humbly beseech Thee, of Thy great goodness, to restrain these immoderate rains with which have had to " +
                "contend. Grant us fair weather for Battle. Graciously hearken to us as soldiers who call upon Thee that, armed with Thy power, we may advance from victory to " +
                "victory, and crush the oppression of wickedness of our enemies and establish Thy justice among men and nations";
        //Posts posts = new Posts(33, "Weather Prayer", _weatherPrayer);
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "33");
        fields.put("title", "My Weather Prayer");
        fields.put("body", _weatherPrayer);
        Call<Posts>call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code Response: " + response.code());
                    return;
                }
                Posts postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                textViewResult.setText(errorMessage(t));
            }
        });
    }

    private void getOnePost(){
        Call<Posts> postsCall = jsonPlaceHolderApi.getSpecificPost(1);
        postsCall.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code Response: " + response.code());
                    return;
                }
                Posts posts = response.body();
                String content = "";
                content += "ID: " + posts.getId() + "\n";
                content += "User ID: " + posts.getUserId() + "\n";
                content += "Title: " + posts.getTitle() + "\n";
                content += "Text: " + posts.getText() + "\n\n";
                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                String message = "There was an error message";
                String err = t.getMessage();
                Toast.makeText(MainActivity.this, message + " " + err, Toast.LENGTH_SHORT).show();
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        Call<List<Posts>> call = jsonPlaceHolderApi.getPosts(parameters);
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code Response: " + response.code());
                    return;
                }
                Log.d(TAG, "onResponse: " + response);
                for (int i = 0; i < response.body().size(); i ++){
                    Log.d(TAG, "onResponse: individual list items : " + response.body().get(i).getTitle());
                }
                List<Posts> posts = response.body();
                for (Posts post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                String message = "There was an error message";
                String err = t.getMessage();
                Toast.makeText(MainActivity.this, message + " " + err, Toast.LENGTH_SHORT).show();
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText(response.code());
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments){
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private String errorMessage(Throwable t){
        String message = "There was an error message";
        String err = t.getMessage();
        return message + "\n" + err;
    }

    private String parseResponse(Response<Posts> response){
        Posts postResponse = response.body();
        String content = "";
        content += "Code: " + response.code() + "\n";
        content += "ID: " + postResponse.getId() + "\n";
        content += "User ID: " + postResponse.getUserId() + "\n";
        content += "Title: " + postResponse.getTitle() + "\n";
        content += "Text: " + postResponse.getText() + "\n\n";
        return content;
    }
}