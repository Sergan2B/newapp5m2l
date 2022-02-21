package kg.geektech.newapp5m2l.data.remote;

import java.util.List;

import kg.geektech.newapp5m2l.data.models.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {

    @GET("posts")
    Call<List<Post>> getPosts();

//    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Body Post post
    );

    //@Query(HUWH?id-3939)
    //@Path(/3)
    @PUT ("posts/{id}")
    Call<Post> updatePost(
            @Path("id") int id,
            @Body Post post
    );

    @DELETE("posts/{id}")
    Call<ResponseBody> deletePost(@Path("id") int id);
}
