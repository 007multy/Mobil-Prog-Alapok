package hu.aut.android.kotlintodolist.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
/*
Itt az adatbázis műveletek találhatóak.
Új adattagkor (új ShippingItem adattag), nem szükséges módosítani itt.
 */
@Dao
interface TodoItemDAO {

    //Az összes listázása
    @Query("SELECT * FROM todoitem")
    fun findAllItems(): List<TodoItem>

    //Egy elem beszúrása
    @Insert
    fun insertItem(item: TodoItem): Long
    //Egy törlése
    @Delete
    fun deleteItem(item: TodoItem)
    //Egy módosítása
    @Update
    fun updateItem(item: TodoItem)

}
