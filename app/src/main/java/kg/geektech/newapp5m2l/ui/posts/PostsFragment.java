package kg.geektech.newapp5m2l.ui.posts;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import kg.geektech.newapp5m2l.App;
import kg.geektech.newapp5m2l.R;
import kg.geektech.newapp5m2l.data.models.Post;
import kg.geektech.newapp5m2l.databinding.FragmentPostsBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    private PostAdapter adapter;
    private NavHostFragment navHostFragment;
    private NavController navController;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter();
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        //navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                    binding.recycler.setAdapter(adapter);
                    adapter.setClickListener(new PostAdapter.OnClick() {
                        @Override
                        public void onClick(Post post) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("post", post);
                            navController.navigate(R.id.formFragment, bundle);
                        }

                        @Override
                        public void onLongClick(int id, int position) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Удалить новость?");
                            alert.setMessage("Вы действительно собираетесь удалить новость? После удаления, новость нельзя будет восстановить.");
                            alert.setPositiveButton("Удалить", (dialog, whichButton) -> {
                                App.api.deletePost(id).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        adapter.deletePost(position);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                            });
                            alert.setNegativeButton("Отмена", (dialog, whichButton) -> dialog.cancel());
                            alert.show();

                        }
                    });
//                    Log.e("Tag2", "onResponse: ");
                } else {
//                    Log.e("Tag22", "onResponse" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });

        binding.fab.setOnClickListener(view1 -> navController.navigate(R.id.action_postsFragment_to_formFragment));
    }
}