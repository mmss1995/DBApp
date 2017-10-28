package matteo.dbapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Matteo on 23/10/2017.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT * FROM user WHERE uid =:id ")
    User findById(int id);

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert
    void insertAll(User user);

    @Delete
    void delete(User user);
}

