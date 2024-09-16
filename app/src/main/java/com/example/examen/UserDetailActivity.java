package com.example.examen;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.examen.api.ApiService;
import com.example.examen.model.PaisRes;
import com.example.examen.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private User user;
    private GoogleMap mMap;
    private ImageView ivUserFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        user = (User) getIntent().getSerializableExtra("user");

        TextView tvName = findViewById(R.id.tvName);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvPhone = findViewById(R.id.tvPhone);
        TextView tvAddress = findViewById(R.id.tvAddress);
        ImageView ivUserPicture = findViewById(R.id.ivUserPicture);
        ivUserFlag = findViewById(R.id.ivUserFlag);

        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        tvAddress.setText(String.format("%s, %s, %s, %s", user.getStreet(), user.getCity(), user.getState(), user.getPostcode()));

        Glide.with(this).load(user.getPicture()).circleCrop().into(ivUserPicture);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fetchCountryFlag();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(user.getLatitude(), user.getLongitude());
        mMap.addMarker(new MarkerOptions().position(location).title(user.getCity()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }

    private void fetchCountryFlag() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<PaisRes> call = apiService.getCountryInfo(user.getCountry());

        call.enqueue(new Callback<PaisRes>() {
            @Override
            public void onResponse(Call<PaisRes> call, Response<PaisRes> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String flagUrl = response.body().get(0).getFlags().getPng();
                    Glide.with(UserDetailActivity.this).load(flagUrl).into(ivUserFlag);
                }
            }

            @Override
            public void onFailure(Call<PaisRes> call, Throwable t) {
                // Handle error
            }
        });
    }
}