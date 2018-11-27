package com.ksoldatov.kr.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ksoldatov.kr.App;
import com.ksoldatov.kr.database.PartyDB;
import com.ksoldatov.kr.database.PartyEntity;
import com.ksoldatov.kr.model.suggestions.Suggestion;
import com.ksoldatov.kr.ui.InfoActivity;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CounterPartyAdapter extends ArrayAdapter<Suggestion> {

    private ArrayList<Suggestion> suggests;
    private ArrayList<Suggestion> tempSuggests;
    private LayoutInflater layoutInflater;
    private PartyDB partyDB;


    public CounterPartyAdapter(@NonNull Context context, int resource, ArrayList<Suggestion> suggests) {
        super(context, resource, suggests);
        this.tempSuggests = suggests;
        this.suggests = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
        partyDB = App.getApp().getPartyDB();
    }

    private String saveToDB(Suggestion sg) {

        PartyEntity entity = partyDB.partyDao()
                .getPartyById(sg.getData().getHid());
        boolean isFavourite = false;
        if (entity != null) {
            isFavourite = entity.isFavourite();
            partyDB.partyDao().deleteParty(entity);
        }
        PartyEntity partyEntity = new PartyEntity(sg, isFavourite);
        partyDB.partyDao().insert(partyEntity);
        return partyEntity.getHid();
    }

    private void toCounterPartyInfo(Suggestion sg, View v) {

        Single.fromCallable(() -> saveToDB(sg))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hid -> {
                            Bundle bundle = new Bundle();
                            bundle.putString(InfoActivity.PARTY_ID, hid);
                            bundle.putBoolean(InfoActivity.REFRESH, false);
                            Intent intent = new Intent(v.getContext(), InfoActivity.class);
                            intent.putExtras(bundle);
                            v.getContext().startActivity(intent);
                        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView textView;
        Suggestion sg = getItem(position);
        if (convertView != null) {
            textView = (TextView) convertView;
        } else {
            textView = (TextView) layoutInflater.inflate(android.R.layout.select_dialog_item, parent, false);
        }
        textView.setText(sg.getUnrestrictedValue());
        Suggestion suggestion = getItem(position);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCounterPartyInfo(suggestion, textView);
            }
        });

        return textView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return suggestionFilter;
    }

    @Override
    public int getCount() {
        return tempSuggests.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private Filter suggestionFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggests.clear();
                for (Suggestion suggest : tempSuggests) {
                    if (suggest.getUnrestrictedValue().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggests.add(suggest);
                    }
                }
                if (suggests.size() > 2) {
                    suggests.subList(2, suggests.size()).clear();
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggests;
                filterResults.count = suggests.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                clear();
                for (Suggestion sg : suggests) {
                    add(sg);
                }
                notifyDataSetChanged();
            }
        }
    };

}
