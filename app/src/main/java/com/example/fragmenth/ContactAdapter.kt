package com.example.fragmenth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ContactAdapter(
    private var contacts: List<Contact>,
    private val onItemClick: (Contact) -> Unit
) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val phoneTextView: TextView = itemView.findViewById(R.id.phone)
        val imgView: ImageView = itemView.findViewById(R.id.imageView)
    }

    fun setFilteredList(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder) {
            nameTextView.text = "${contact.firstName} ${contact.lastName}"
            phoneTextView.text = contact.phoneNumber

            if (contact.photo.isNotBlank()) {
                Glide.with(imgView.context)
                    .load(contact.photo)
                    .circleCrop()
                    .placeholder(R.drawable.baseline_person_24)
                    .error(R.drawable.baseline_person_24)
                    .into(imgView)
            } else {
                Glide.with(imgView.context).clear(imgView)
                imgView.setImageResource(R.drawable.baseline_person_24)
            }

            itemView.setOnClickListener {
                onItemClick(contact)
            }

            itemView.setOnLongClickListener { view ->
                val popupMenu = PopupMenu(view.context, view)
                popupMenu.menuInflater.inflate(R.menu.delete_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_delete -> {
                            Data.contacts.remove(contact)
                            notifyItemRemoved(position)
                            true
                        }
                        else -> false
                    }
                }

                popupMenu.show()
                true
            }
        }


    }

    override fun getItemCount(): Int {
        return contacts.size
    }


}