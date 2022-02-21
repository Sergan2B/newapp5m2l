package kg.geektech.newapp5m2l;

import android.app.Application;

import kg.geektech.newapp5m2l.data.remote.PostApi;
import kg.geektech.newapp5m2l.data.remote.RetrofitClient;

public class App extends Application {
    private RetrofitClient client;
    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new RetrofitClient();
        api = client.provideApi();
    }
}
