package edu.uw.stluong.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import edu.uw.stluong.dotify.databinding.ItemSongBinding

class SongListAdapter(private var listOfSong: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    var onSongClickListener: (currentSongObj: Song) -> Unit = {currentSongObj ->}

    class SongViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfSong.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentSong = listOfSong[position]
        with(holder.binding) {
            tvSongTitle.text = currentSong.title
            tvSongArtist.text = currentSong.artist
            ivSmallSongCover.setImageResource(currentSong.smallImageID)
            itemRoot.setOnClickListener {
                onSongClickListener(currentSong)
            }
        }
    }


    fun updateSong(updatedSongList: List<Song>) {
        listOfSong = updatedSongList
        notifyDataSetChanged()
    }
}