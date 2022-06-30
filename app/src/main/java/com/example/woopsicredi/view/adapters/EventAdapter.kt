package com.example.woopsicredi.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.example.woopsicredi.R
import com.example.woopsicredi.databinding.EventsItemBinding
import com.example.woopsicredi.model.Events
import com.example.woopsicredi.utils.formartLongDate
import com.squareup.picasso.Picasso
import java.util.*

class EventAdapter(
    private val onItemClicked: (Events) -> Unit
) : RecyclerView.Adapter<EventsViewHolder>() {

    var events = mutableListOf<Events>()

    fun setEventsList(events: List<Events>) {
        this.events = events.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = EventsItemBinding.inflate(inflater, parent, false)
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val events = events[position]
        holder.binding.date.text = formartLongDate(Date(events.date!!))
        holder.binding.title.text = events.title
        holder.binding.description.text = events.description
        Picasso.get()
            .load(events.image)
            .placeholder(AvatarGenerator.avatarImage(holder.itemView.context, 200, AvatarConstants.RECTANGLE,
                events.title.toString()
            ))
            .into(holder.binding.image)
        holder.binding.groupClick.setOnClickListener {
            onItemClicked(events)
        }


    }

    override fun getItemCount(): Int {
        return events.size
    }
}

class EventsViewHolder(val binding: EventsItemBinding) : RecyclerView.ViewHolder(binding.root) {}