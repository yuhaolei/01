package com.one.eng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.one.eng.self.ImageTextView;


//数学
public class UseFragment extends androidx.fragment.app.Fragment {
    View useView;
    ImageTextView imageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        useView =
                inflater.inflate(R.layout.fragment_use, container, false);
        return useView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,
                savedInstanceState);
        imageTextView =
                view.findViewById(R.id.itv_textview);
        imageTextView.insertDrawable(R.drawable.bench);
    }
}
