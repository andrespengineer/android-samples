package com.android.myapplication.ui.posts.adapter.viewholders

import android.animation.Animator
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.R
import com.android.myapplication.ui.posts.adapter.PostsAdapter
import com.android.myapplication.ui.posts.adapter.viewholders.contracts.PostsViewHolderContract
import com.android.myapplication.base.BaseViewHolder
import com.android.myapplication.databinding.PostsItemBinding
import com.android.myapplication.extensions.setOnTouchListenerWithoutPerformClick
import com.android.myapplication.data.models.Post
import com.android.myapplication.util.MyGlideRequestOptions.postRequestOption
import com.android.myapplication.util.MyGlideRequestOptions.profileRequestOption
import com.android.myapplication.util.MyKeySignature
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class PostsAdapterViewHolder constructor(val binding: PostsItemBinding, adapter: PostsAdapter) : BaseViewHolder<Post, PostsAdapter>(binding.root, adapter), PostsViewHolderContract.View {

    private var gestureDetector: GestureDetector = GestureDetector(binding.root.context, this)
    private val animatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
        }

        override fun onAnimationEnd(animation: Animator) {

            binding.lavLikeAnimation.postDelayed({
                binding.lavLikeAnimation.visibility = View.GONE
                binding.lavLikeAnimation.cancelAnimation()
                binding.lavLikeAnimation.setOnTouchListenerWithoutPerformClick(this@PostsAdapterViewHolder)
                notifyLike(liked = model.liked.not(), animated = false)
            }, 100)

        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    }

    init {
        binding.ivPostItem.setOnTouchListenerWithoutPerformClick(this)
        binding.lavLike.setOnClickListener(this)
    }

    private fun notifyLike(liked: Boolean, animated: Boolean) {

        model.setPostLikes(model.getPostLikes().toInt().plus(if (model.liked.not()) 1 else -1).toString())

        if (bindingAdapterPosition != RecyclerView.NO_POSITION && model.liked != liked) {
            if(animated) {
                binding.lavLike.progress = if (liked) 0f else 1f
                binding.lavLike.speed = if (liked) 1f else -1f
                binding.lavLike.postDelayed({binding.lavLike.playAnimation()}, 300);
            }
            else {
                binding.lavLike.progress = if (liked) 1f else 0f
                binding.lavLike.speed = if (liked) -1f else 1f
            }

            model.liked = liked
        }


        binding.tvPostItemLikes.text = model.getPostLikes()
    }

    override fun onBind(model: Post) {
        super.onBind(model)
        binding.tvPostItemName.text = model.username
        binding.tvPostItemLikes.text = model.getPostLikes()

        val spanned = SpannableString(model.username + " " + model.postDescription)
        spanned.setSpan(StyleSpan(Typeface.BOLD), 0, model.username.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        spanned.setSpan(RelativeSizeSpan(.97f), model.username.length, spanned.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        binding.tvPostItemDescription.setText(spanned, TextView.BufferType.SPANNABLE)
        binding.tvPostItemLocation.text = model.postLocation

        Glide.with(binding.root.context)
            .applyDefaultRequestOptions(profileRequestOption.signature(MyKeySignature(model.userId)))
            .load(model.profilePicture)
            .into(binding.ivPostItemProfile)


        Glide.with(binding.root.context)
            .applyDefaultRequestOptions(postRequestOption.signature(MyKeySignature(model.postId)))
            .load(model.postPicture)
            .into(binding.ivPostItem)

        if (model.liked) {
            binding.lavLike.cancelAnimation()
            binding.lavLike.progress = 1f
        } else {
            binding.lavLike.cancelAnimation()
            binding.lavLike.progress = 0f
        }
    }

    fun cancelLikeAnimation(){
        binding.lavLikeAnimation.cancelAnimation()
        binding.lavLikeAnimation.visibility = View.GONE
        binding.lavLikeAnimation.progress = 0f
        binding.lavLikeAnimation.setOnTouchListenerWithoutPerformClick(this@PostsAdapterViewHolder)
    }

    private fun showLikeAnimation(){

        binding.lavLikeAnimation.setOnTouchListenerWithoutPerformClick(null)

        binding.lavLikeAnimation.progress = 0f
        binding.lavLikeAnimation.visibility = View.VISIBLE
        binding.lavLikeAnimation.removeAllAnimatorListeners()
        binding.lavLikeAnimation.addAnimatorListener(animatorListener)
        binding.lavLikeAnimation.postDelayed({ binding.lavLikeAnimation.playAnimation() }, 300)

    }

    override fun onLikePost() {
        if (binding.lavLikeAnimation.composition == null) {
            binding.lavLikeAnimation.setComposition(adapter.lottieLikeAnimationComposition)
        }

        showLikeAnimation()
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        onLikePost()
        return false
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lavLike -> notifyLike(liked = model.liked.not(), animated = true)
        }
    }

}