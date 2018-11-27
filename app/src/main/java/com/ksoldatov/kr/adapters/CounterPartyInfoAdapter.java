package com.ksoldatov.kr.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ksoldatov.kr.App;
import com.ksoldatov.kr.R;
import com.ksoldatov.kr.database.PartyDB;
import com.ksoldatov.kr.database.PartyEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CounterPartyInfoAdapter extends RecyclerView.Adapter<CounterPartyInfoAdapter.ViewHolder> {

    private List<PartyEntity> partyEntities;
    private PartyDB partyDB;

    public CounterPartyInfoAdapter() {
        partyEntities = new ArrayList<>();
        partyDB = App.getApp().getPartyDB();

        Single.fromCallable(this::loadPartyEntities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entities -> {
                    partyEntities = entities;
                    this.notifyDataSetChanged();
                });
    }

    private List<PartyEntity> loadPartyEntities() {
        return partyDB.partyDao().getAll();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView partyName;

        public ViewHolder(View itemView) {
            super(itemView);

            partyName = (TextView) itemView.findViewById(R.id.party_name);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CounterPartyInfoAdapter.ViewHolder viewHolder, int position) {
        PartyEntity partyEntity = partyEntities.get(position);

        viewHolder.partyName.setText(partyEntity.getValue());

    }

    @Override
    public int getItemCount() {
        return partyEntities.size();
    }
}
