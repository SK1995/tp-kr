package com.ksoldatov.kr.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {PartyEntity.class}, version = 1)
@TypeConverters({DataConverter.class})
public abstract class PartyDB extends RoomDatabase {
    public abstract PartyDao partyDao();
}
