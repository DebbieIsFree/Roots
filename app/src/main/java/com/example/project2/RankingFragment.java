package com.example.project2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingFragment extends Fragment {

    // Add RecyclerView member
    private RecyclerView recyclerView;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        // Add the following lines to create RecyclerView

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.10.5.152:443/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        Button backButton = (Button)view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            }
        });

        Button connectButton = (Button)view.findViewById(R.id.connect_button);
        connectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                RetrofitService service1 = retrofit.create(RetrofitService.class);

                Call<MusicData> call = service1.getMusicData("1");

                call.enqueue(new Callback<MusicData>(){
                    @Override
                    public void onResponse(Call<MusicData> call, Response<MusicData> response){
                        if(response.isSuccessful()){
                            MusicData result = response.body();
                            Log.d("MY TAG", "onResponse: 성공, 결과\n"+result.getName()+result.getSinger()+Integer.toString(result.getYear()));
                        }
                        else{
                            Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<MusicData> call, Throwable t){
                        Log.d("MY TAG", "onFailure: "+t.getMessage());
                    }
                });
            }
        });

        Button addButton = (Button)view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new Adapter(getActivity().getApplicationContext());

        for(int i = 0; i < 50; i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("music", Integer.toString(i));
                jsonObject.put("singer", Integer.toString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.setArrayData(jsonObject);
        }
        recyclerView.setAdapter(adapter);

        return view;
    }
}