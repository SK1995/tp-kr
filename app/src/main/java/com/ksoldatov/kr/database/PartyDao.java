package com.ksoldatov.kr.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PartyDao {
    @Insert
    void insert(PartyEntity partyEntity);

    @Delete
    int deleteParty(PartyEntity partyEntity);

    @Update
    int updateParty(PartyEntity partyEntity);

    @Query("SELECT * FROM party WHERE hid = :hid LIMIT 1")
    PartyEntity getPartyById(String hid);

    @Query("SELECT * FROM party")
    List<PartyEntity> getAll();
}