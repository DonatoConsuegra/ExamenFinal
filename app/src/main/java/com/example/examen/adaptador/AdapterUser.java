
package com.example.examen.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examen.R;
import com.example.examen.model.User;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserViewHolder> {
    private List<User> users;
    private Context context;
    private OnUserClickListener listener;

    public AdapterUser(Context context, List<User> users, OnUserClickListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user, listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserPicture;
        TextView tvUserName, tvUserCountry, tvUserEmail;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPicture = itemView.findViewById(R.id.ivUserPicture);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserCountry = itemView.findViewById(R.id.tvUserCountry);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
        }

        void bind(final User user, final OnUserClickListener listener) {
            tvUserName.setText(user.getName());
            tvUserCountry.setText(user.getCountry());
            tvUserEmail.setText(user.getEmail());
            Glide.with(itemView.getContext()).load(user.getPicture()).circleCrop().into(ivUserPicture);

            itemView.setOnClickListener(v -> listener.onUserClick(user));
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
}