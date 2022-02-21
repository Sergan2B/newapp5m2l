package kg.geektech.newapp5m2l.ui.form;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kg.geektech.newapp5m2l.App;
import kg.geektech.newapp5m2l.data.models.Post;
import kg.geektech.newapp5m2l.databinding.FragmentFormBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    private final int USER_ID = 1;
    private final int GROUP_ID = 39;
    private Post post;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            updatePost();
        else
            createPost();
    }

    private void updatePost() {
        assert getArguments() != null;
        post = (Post) getArguments().getSerializable("post");

        binding.etContent.setText(post.getContent());
        binding.etTitle.setText(post.getTitle());

        post.setTitle(binding.etTitle.getText().toString());
        post.setContent(binding.etContent.getText().toString());
//        Log.e(TAG, "onResponse: " + post.getTitle() + " " + post.getId());

        binding.btnSend.setOnClickListener(view -> {
            Log.e(TAG, "update: " + post.getTitle() + " " + post.getId());

            App.api.updatePost(post.getId(), new Post(
                    binding.etContent.getText().toString(),
                    binding.etTitle.getText().toString(),
                    GROUP_ID,
                    USER_ID
            )).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    requireActivity().onBackPressed();
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {

                }
            });
        });
    }

    private void createPost() {
        binding.btnSend.setOnClickListener(view1 -> {
            String title = binding.etTitle.getText().toString();
            String content = binding.etContent.getText().toString();

            if (!title.isEmpty() && !content.isEmpty()) {
                post = new Post(content, title, GROUP_ID, USER_ID);
                App.api.createPost(post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            requireActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            } else Toast.makeText(requireActivity(), "Заполните поля", Toast.LENGTH_SHORT).show();
        });
    }
}