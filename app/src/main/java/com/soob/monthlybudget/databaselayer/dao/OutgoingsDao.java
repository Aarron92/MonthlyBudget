package com.soob.monthlybudget.databaselayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.soob.monthlybudget.databaselayer.entities.Account;
import com.soob.monthlybudget.databaselayer.entities.Outgoing;

import java.util.List;
/**
 * Interface defining Data Access Object to interact with the Monthly Outgoing table
 *
 * Includes methods that offer abstract access to your app's database. At compile time, the Room
 * Persistence Library automatically generates implementations of the DAOs
 */
@Dao
public interface OutgoingsDao
{
    @Insert
    void insertOutgoing(Outgoing outgoing);

    @Update
    void updateOutgoing(Outgoing outgoing);

    @Delete
    void deleteOutgoing(Outgoing outgoing);

    @Delete
    void deleteMultipleOutgoings(Outgoing... outgoings);

    @Query("SELECT * FROM outgoings")
    List<Outgoing> getAllOutgoings();

//    @Query("UPDATE Outgoing SET name = :name WHERE id = :id")
//    void updateName(UUID id, String name);
//
//    @Query("UPDATE Outgoing SET amount = :newAmount WHERE name = :name")
//    void updateAmount(String name, Double newAmount);
//
//    @Query("UPDATE Outgoing SET comment = :newComment WHERE name = :name")
//    void updateComment(String name, String newComment);
//
//    @Query("UPDATE Outgoing SET accountRef = :newAccountRef WHERE name = :name")
//    void updateAccountRef(String name, UUID newAccountRef);
}