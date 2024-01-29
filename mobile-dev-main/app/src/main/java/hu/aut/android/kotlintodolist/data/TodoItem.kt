package hu.aut.android.kotlintodolist.data

import java.io.Serializable

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import java.util.*

/*
Adatbázis táblát készti el.
Táblanév:shoppingitem.
Oszlopok:itemId, name,  price, bought.
@PrimaryKey(autoGenerate = true): elsődleges kulcs, automatikusan generálva.
Ide szükséges a bővítés új adattal.
 */
@Entity(tableName = "todoitem")
data class TodoItem(@PrimaryKey(autoGenerate = true) var itemId: Long?,
                    @ColumnInfo(name = "name") var name: String,
                    @ColumnInfo(name = "priority") var priority: Int,
                    @ColumnInfo(name = "done") var bought: Boolean,
                    @ColumnInfo(name = "deadline") var deadline: String
) : Serializable
