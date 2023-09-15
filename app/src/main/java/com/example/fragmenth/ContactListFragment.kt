package com.example.fragmenth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class ContactListFragment : Fragment() {

    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val searchView: SearchView = view.findViewById(R.id.searchView)
        adapter = ContactAdapter(Data.contacts) { contact ->
            openContactDetailsFragment(contact)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        return view
    }

    private fun openContactDetailsFragment(contact: Contact) {
        val detailsFragment = ContactDetailsFragment.newInstance(contact)
        val transaction = parentFragmentManager.beginTransaction()
        if (resources.getBoolean(R.bool.isTablet)) {
            transaction.replace(R.id.fragment_container_2, detailsFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            transaction.replace(R.id.fragmentContainer, detailsFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = mutableListOf<Contact>()
            for (i in Data.contacts) {
                if ((i.firstName.lowercase(Locale.ROOT)+" "+i.lastName.lowercase(Locale.ROOT)).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
//                Toast.makeText(this , "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

}