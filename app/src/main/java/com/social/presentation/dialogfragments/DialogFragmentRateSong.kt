package com.social.presentation.dialogfragments

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import coil.load
import com.social.R
import com.social.base.BaseDialogFragment
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentSongPopupBinding
import com.social.utils.PackageUtil
import java.util.*

class DialogFragmentRateSong : BaseDialogFragment<FragmentSongPopupBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSongPopupBinding
        get() = FragmentSongPopupBinding::inflate

    private lateinit var item: PlaylistModel


    fun setItemData(item: PlaylistModel){
        this.item = item
    }


    override fun setup() {

        binding.PopupSongRatingBar.rating = item.rating
        binding.PopupSongRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(this@DialogFragmentRateSong.requireContext(), requireContext().resources.getString(R.string.popup_song_rated).plus( " ").plus(rating.toString()), Toast.LENGTH_SHORT).show()
        }

        binding.ivPopupSongImage.load(item.image){
            crossfade(true)
            memoryCacheKey(item.key)
        }

        binding.tvPopupSongArtist.text = item.artist
        binding.tvPopupSongName.text = item.name
        binding.btnPopupSongSuggestion.setOnClickListener(this)
        binding.btnPopupSongListenSpotify.setOnClickListener(this)
        binding.btnPopupSongBack.setOnClickListener(this)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.drawable.shape_corner_radius_popup_song)
        return dialog
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPopupSongSuggestion -> {
                Toast.makeText(requireContext(), requireContext().getString(R.string.suggested_song), Toast.LENGTH_SHORT).show()
                dismiss()
            }
            R.id.btnPopupSongListenSpotify -> {
                val pm: PackageManager = requireContext().packageManager
                if (PackageUtil.isPackageInstalled("com.spotify.music", pm)) {
                    val uri = "spotify:track:" + item.spotify
                    val launcher = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(launcher)
                } else {
                    Toast.makeText(requireContext(), resources.getString(R.string.spotify_not_installed), Toast.LENGTH_SHORT).show()
                }
                dismiss()
            }
            else -> {
                dismiss()
            }
        }
    }



    companion object {
        fun newInstance(): DialogFragmentRateSong {
            return DialogFragmentRateSong()
        }
    }
}