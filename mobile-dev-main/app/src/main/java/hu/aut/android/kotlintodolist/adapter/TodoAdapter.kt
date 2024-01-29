package hu.aut.android.kotlintodolist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import hu.aut.android.kotlintodolist.R
import hu.aut.android.kotlintodolist.MainActivity
import hu.aut.android.kotlintodolist.adapter.TodoAdapter.ViewHolder
import hu.aut.android.kotlintodolist.data.AppDatabase
import hu.aut.android.kotlintodolist.data.TodoItem
import hu.aut.android.kotlintodolist.touch.TodoTouchHelperAdapter
import kotlinx.android.synthetic.main.row_item.view.*
import java.util.*

class TodoAdapter : RecyclerView.Adapter<ViewHolder>, TodoTouchHelperAdapter {
    /* ShoppingItem elemek listája*/
    private val items = mutableListOf<TodoItem>()
    private val context: Context

    constructor(context: Context, items: List<TodoItem>) : super() {
        this.context = context
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.row_item, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*Itt kérjük le az egyes ShoppingItem elemek adattagjait, itt is szükséges az adattaggal a bővítés*/
        holder.tvName.text = items[position].name
        holder.tvPriority.text = items[position].priority.toString()
        holder.cbDone.isChecked = items[position].bought
        holder.tvDeadline.text =items[position].deadline
        /*Delete gomb eseménykezeője (a főoldalon)*/
        holder.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }
        /*Edit gomb eseménykezelője (a főoldalon), megnyitja az edit dialógust, átadja az adott ShoppingItem-et neki*/
        holder.btnEdit.setOnClickListener {
            (holder.itemView.context as MainActivity).showEditItemDialog(
                    items[holder.adapterPosition])
        }
        /*Checkbox eseménykezelője, állítja a checkbox értékét, azaz a ShoppingItem-nek, az isChecked adattagját.
        Az adatbázisban is frissíti
         */
        holder.cbDone.setOnClickListener {
            items[position].bought = holder.cbDone.isChecked
            val dbThread = Thread {
                //Itt frissíti a DB-ben
                AppDatabase.getInstance(context).todoItemDao().updateItem(items[position])
            }
            dbThread.start()
        }
    }
    /*Új elem hozzáadásakor hívódik meg*/
    fun addItem(item: TodoItem) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }
    /*Elem törlésekor hívódik meg. Az adatbázisból törli az elemet (DAO-n keresztül)*/
    fun deleteItem(position: Int) {
        val dbThread = Thread {
            AppDatabase.getInstance(context).todoItemDao().deleteItem(
                    items[position])
            (context as MainActivity).runOnUiThread{
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        dbThread.start()
    }
    /*Update-kor hívódik meg*/
    fun updateItem(item: TodoItem) {
        val idx = items.indexOf(item)
        items[idx] = item
        notifyItemChanged(idx)
    }

    override fun onItemDismissed(position: Int) {
        deleteItem(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)

        notifyItemMoved(fromPosition, toPosition)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*a ShoppingItem elemek, ide kell a bővítés új taggal*/
        /*Itt a gombokat, checkboxot is lekérjük*/
        val tvName: TextView = itemView.tvName
        val tvPriority: TextView = itemView.tvPriority
        val cbDone: CheckBox = itemView.cbDone
        val btnDelete: Button = itemView.btnDelete
        val btnEdit: Button = itemView.btnEdit
        val tvDeadline:TextView=itemView.tvDeadline
    }
}