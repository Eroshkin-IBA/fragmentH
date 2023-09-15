package com.example.fragmenth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ContactDetailsFragment : Fragment() {

    companion object {
        private const val ARG_CONTACT = "contact"

        fun newInstance(contact: Contact): ContactDetailsFragment {
            val fragment = ContactDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_CONTACT, contact)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact = it.getParcelable(ARG_CONTACT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_contact_details, container, false)
        val nameTextView: TextView = view.findViewById(R.id.name)
        val phoneNumberTextView: TextView = view.findViewById(R.id.phone)

        val editName: EditText = view.findViewById(R.id.editName)
        editName.setText(contact.firstName)

        val editLastName: EditText = view.findViewById(R.id.editLastName)
        editLastName.setText(contact.lastName)

        val editPhone: EditText = view.findViewById(R.id.editPhone)
        editPhone.setText(contact.phoneNumber)

        val saveButton: Button = view.findViewById(R.id.saveButton)
        val editButton: Button = view.findViewById(R.id.editButton)

        nameTextView.text = "${contact.firstName} ${contact.lastName}"
        phoneNumberTextView.text = contact.phoneNumber

        editButton.setOnClickListener {
            nameTextView.visibility = View.GONE
            phoneNumberTextView.visibility = View.GONE
            editButton.visibility = View.GONE
            editName.visibility = View.VISIBLE
            editLastName.visibility = View.VISIBLE
            editPhone.visibility = View.VISIBLE
            saveButton.visibility = View.VISIBLE
        }

        saveButton.setOnClickListener {
            val index = Data.contacts.indexOf(contact)
            Data.contacts.set(
                index, Contact(
                    editName.text.toString(),
                    editLastName.text.toString(),
                    editPhone.text.toString(),
                    contact.photo
                )
            )
            openContactListFragment()
        }
        return view
    }

    private fun openContactListFragment() {
        val contactListFragment = ContactListFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, contactListFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}