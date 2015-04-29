package com.example.rallmo.lagg;

import android.widget.ArrayAdapter;
import java.util.List;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rallmo on 2015-04-29.
 */
public class PatientAdapter extends CardScrollAdapter{
    private List<CardBuilder> inputCards;
    private List<PatientModel> inputData;

    public PatientAdapter(List<CardBuilder> cards){
        this.inputCards = cards;
    }

    @Override
    public int getCount(){
        return inputCards.size();
    }

    @Override
    public Object getItem(int i){
        return inputCards.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        return inputCards.get(i).getView();
    }

    @Override
    public int getPosition(Object o){
        return this.inputCards.indexOf(o);
    }


}
