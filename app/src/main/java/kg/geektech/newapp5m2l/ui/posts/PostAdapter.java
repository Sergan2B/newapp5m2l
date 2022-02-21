package kg.geektech.newapp5m2l.ui.posts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.newapp5m2l.data.models.Post;
import kg.geektech.newapp5m2l.data.students.Student;
import kg.geektech.newapp5m2l.databinding.ItemPostBinding;

@SuppressLint("NotifyDataSetChanged")

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> posts = new ArrayList<>();
    private OnClick clickListener;

    public void setClickListener(OnClick clickListener) {
        this.clickListener = clickListener;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void deletePost(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));
        holder.itemView.setOnClickListener(view -> clickListener.onClick(posts.get(holder.getAdapterPosition())));
        holder.itemView.setOnLongClickListener(view -> {
            clickListener.onLongClick(posts.get(position).getId(), position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post) {
            if (Student.getStudent().containsKey(post.getUserId())) {
                binding.tvUserId.setText(Student.getStudent().get(post.getUserId()));
            } else {
                binding.tvUserId.setText(String.valueOf(post.getUserId()));
            }
            binding.tvTitle.setText(post.getTitle());
            binding.tvSubTitle.setText(post.getContent());
        }
    }

    interface OnClick {
        void onClick(Post post);

        void onLongClick(int id, int position);
    }
}
