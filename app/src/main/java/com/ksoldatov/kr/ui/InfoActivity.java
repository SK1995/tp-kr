package com.ksoldatov.kr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ksoldatov.kr.App;
import com.ksoldatov.kr.MainActivity;
import com.ksoldatov.kr.R;
import com.ksoldatov.kr.database.PartyDB;
import com.ksoldatov.kr.database.PartyEntity;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InfoActivity extends AppCompatActivity {

    public static String PARTY_ID = "PARTY_ID";
    public static String REFRESH = "REFRESH";
    public static String MAP_EXTRA = "MAP_EXTRA";
    public static String GEO_LAT = "GEO_LAT";
    public static String GEO_LONG = "GEO_LONG";
    private String partyHid;
    private TextView infoTextView;
    private PartyDB partyDB;
    private PartyEntity partyEntity;
    private FloatingActionButton favouritesFab;
    private FloatingActionButton shareFab;
    private FloatingActionButton mapFab;
    private FloatingActionButton deleteFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_party_info);
        infoTextView = findViewById(R.id.party_info);
        favouritesFab = findViewById(R.id.favourites);
        shareFab = findViewById(R.id.share);
        mapFab = findViewById(R.id.show_on_map);
        deleteFab = findViewById(R.id.delete_from_history);

        Intent intent = getIntent();
        partyHid = intent.getExtras().getString(PARTY_ID);
        partyDB = App.getApp().getPartyDB();

        Completable.fromAction(() -> getPartyById(partyHid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    if (partyEntity != null) {
                        infoTextView.setText(partyEntity.toString());
                        updateFavouritsImage();
                    }
                });

        favouritesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Completable.fromAction(() -> updateFavouritesDbStatus())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            updateFavouritsImage();
                        });
            }
        });

        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnMap();
            }
        });

        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePartyFromHistory();
            }
        });

    }

    private void getPartyById(String id) {
        partyEntity = partyDB.partyDao().getPartyById(id);
    }

    private void updateFavouritsImage() {
        if (partyEntity.isFavourite()) {
            favouritesFab.setImageResource(R.drawable.ic_favorite_filled_24dp);
        } else {
            favouritesFab.setImageResource(R.drawable.ic_favorite_border_24dp);
        }
    }

    private void updateFavouritesDbStatus() {
        partyEntity.setFavourite(!partyEntity.isFavourite());
        partyDB.partyDao().updateParty(partyEntity);
    }

    private void share() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, partyEntity.toString());
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void showOnMap() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MAP_EXTRA, R.id.action_map);
        intent.putExtra(GEO_LAT, partyEntity.getLatitude());
        intent.putExtra(GEO_LONG, partyEntity.getLongitude());
        startActivity(intent);
    }

    private void deletePartyFromHistory() {
        Completable.fromAction(() -> partyDB.partyDao().deleteParty(partyEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish);
    }


}
