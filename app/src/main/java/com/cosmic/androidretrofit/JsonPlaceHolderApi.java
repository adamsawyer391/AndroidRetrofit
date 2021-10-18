package com.cosmic.androidretrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {


    /**
     * ---------------------------POSTS-------------------------------------------------
     */

    /**
     * get all posts
     * @return
     */
    @GET("posts")
    Call<List<Posts>> getPosts();




    /**
     * get all posts by a specific user
     * @param userId
     * @return
     */
    @GET("posts")
    Call<List<Posts>> getPosts(@Query("userId") int userId);



    /**
     * get all posts by a specific user and sort them in descending order
     * @param userId
     * @param sort
     * @param order
     * @return
     */
    @GET("posts")
    Call<List<Posts>> getPosts(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    /**
     * This gives us all posts from two users
     * @param userId
     * @param userId2
     * @param sort
     * @param order
     * @return
     */
    @GET("posts")
    Call<List<Posts>> getPosts(
            @Query("userId") Integer userId,
            @Query("userId") Integer userId2,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    /**
     * Pass an array of values for the user ids you are requestign for posts. So, you could ask for all posts
     * from three or more user Ids
     * @param userId
     * @param sort
     * @param order
     * @return
     */
    @GET("posts")
    Call<List<Posts>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );


    @GET("posts")
    Call<List<Posts>> getPosts(@QueryMap Map<String, String> parameters);


    @GET("posts/{id}")
    Call<Posts> getSpecificPost(@Path("id")int id);


    /*
     * ---------------POST METHOD FOR POSTS OBJECT
     */

    /**
     * create a single post with passed Posts object
     * @return
     */
    @POST("posts")
    Call<Posts> createPost(@Body Posts posts);


    /*
     * Create a single post based on a passed individual required fields
     * @param userId
     * @param title
     * @param text
     * @return
     */
    @FormUrlEncoded
    @POST("posts")
    Call<Posts> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    /**
     * Create a single post based on a passed Map containing all of the individual required fields
     * @return
     */
    @FormUrlEncoded
    @POST("posts")
    Call<Posts> createPost(@FieldMap Map<String, String> fields);


    @PUT("posts/{id}")
    Call<Posts> putPost(
            @Path("id") int id,
            @Body Posts posts
    );

    @Headers({"Static-Header: 123", "Static-Header2: 456"})
    @PUT("posts/{id}")
    Call<Posts> putPost(
            @Header("Dynamic-Header") String header,
            @Path("id") int id,
            @Body Posts posts
    );

    @PATCH("posts/{id}")
    Call<Posts> patchPost(
            @Path("id") int id,
            @Body Posts posts
    );

    @PATCH("posts/{id}")
    Call<Posts> patchPost(
            @HeaderMap Map<String, String> headers,
            @Path("id") int id,
            @Body Posts posts
    );

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);









    /**
     * ------------------COMMENTS---------------------------------------------------------------------
     */

    /**
     * get all comments from a specific post with hard coded data in the url
     */
//    @GET("posts/2/comments")
//    Call<List<Comment>> getComments();




//    /**
//     * get all comments from a specific post by passing an integer to the interface method
//     * @param postId
//     * @return
//     */
//    @GET("posts/{id}/comments")
//    Call<List<Comment>> getComments(@Path("id")int postId);


    @GET
    Call<List<Comment>> getComments(@Url String url);

}
