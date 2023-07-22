package com.example.assignment.presentation.feeds

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.databinding.ItemFeedBinding
import com.example.assignment.domain.model.Feed

class FeedAdapter(
    private val feedList: ArrayList<Feed>,
    val context: Context,
    val viewModel: FeedViewModel
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    private var doubleClickLastTime = 0L

    inner class FeedViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        with(holder.binding) {
            with(feedList[position]) {
                postDesc.text = this.alt_description
                userName.text = this.user.name
                userSubTitle.text = this.user.username
                Glide.with(context).load(this.user.profile_image.medium).into(accountImg)
                Glide.with(context).load(this.urls.regular).into(postImg)
                commentsTv.text =
                    String.format(context.resources.getString(R.string.comments, this.comments))
                tvLikesCounter.setCurrentText(
                    String.format(
                        context.resources.getString(
                            R.string.likes,
                            this.likes
                        )
                    )
                )
                shareTv.setOnClickListener {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = this.urls.regular
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
                }
                if (this.isLiked) likeIcon.setImageResource(R.drawable.ic_like_red)
                likeIcon.setOnClickListener {
                    if (!isLiked) {
                        updateLikesCounter(holder, true, position)
                        updateHeartButton(holder, true)
                    } else {
                        updateLikesCounter(holder, false, position)
                        updateHeartButton(holder, false)
                    }
                }
                postImg.setOnClickListener {
                    if (System.currentTimeMillis() - doubleClickLastTime < 300) {
                        doubleClickLastTime = 0
                        animatePhotoLike(holder)
                        if (!isLiked) {
                            updateLikesCounter(holder, true, position)
                            updateHeartButton(holder, true)
                        }
                    } else
                        doubleClickLastTime = System.currentTimeMillis()
                }
            }
        }
    }

    override fun getItemCount(): Int = feedList.size
    fun getFeedAtPosition(position: Int): Feed = feedList[position]
    fun clear() {
        val size = feedList.size
        feedList.clear()
        notifyItemRangeChanged(0, size - 1)
    }

    fun updateFeed(list: List<Feed>) {
        feedList.addAll(list)
        notifyDataSetChanged()
    }

    private fun updateLikesCounter(holder: FeedViewHolder, animated: Boolean, position: Int) {
        with(holder.binding) {
            with(feedList[position]) {
                if (animated) {
                    tvLikesCounter.setText(
                        String.format(
                            context.resources.getString(
                                R.string.likes,
                                likes + 1
                            )
                        )
                    )
                    feedList[position].likes = likes + 1
                } else {
                    tvLikesCounter.setCurrentText(
                        String.format(
                            context.resources.getString(
                                R.string.likes,
                                likes - 1
                            )
                        )
                    )
                    feedList[position].likes = likes - 1
                }
                isLiked = !isLiked
                viewModel.updateFeed(this)
            }
        }
    }

    private fun updateHeartButton(holder: FeedViewHolder, animated: Boolean) {
        val ACCELERATE_INTERPOLATOR = AccelerateInterpolator()
        val OVERSHOOT_INTERPOLATOR = OvershootInterpolator(4f)

        with(holder.binding) {
            if (animated) {
                val animatorSet = AnimatorSet()
                val rotationAnim = ObjectAnimator.ofFloat(likeIcon, "rotation", 0f, 360f)
                rotationAnim.duration = 300
                rotationAnim.interpolator = ACCELERATE_INTERPOLATOR
                val bounceAnimX = ObjectAnimator.ofFloat(likeIcon, "scaleX", 0.2f, 1f)
                bounceAnimX.duration = 300
                bounceAnimX.interpolator = OVERSHOOT_INTERPOLATOR
                val bounceAnimY = ObjectAnimator.ofFloat(likeIcon, "scaleY", 0.2f, 1f)
                bounceAnimY.duration = 300
                bounceAnimY.interpolator = OVERSHOOT_INTERPOLATOR
                bounceAnimY.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        likeIcon.setImageResource(R.drawable.ic_like_red)
                    }
                })
                animatorSet.play(rotationAnim)
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim)
                animatorSet.start()
            } else
                likeIcon.setImageResource(R.drawable.ic_like)
        }
    }

    private fun animatePhotoLike(holder: FeedViewHolder) {
        val ACCELERATE_INTERPOLATOR = AccelerateInterpolator()
        val DECCELERATE_INTERPOLATOR = DecelerateInterpolator()
        with(holder.binding) {
            val animatorSet = AnimatorSet()
            val imgScaleUpYAnim = ObjectAnimator.ofFloat(bgLikeIcon, "scaleY", 0.1f, 1f)
            imgScaleUpYAnim.duration = 300
            imgScaleUpYAnim.interpolator = DECCELERATE_INTERPOLATOR
            val imgScaleUpXAnim = ObjectAnimator.ofFloat(bgLikeIcon, "scaleX", 0.1f, 1f)
            imgScaleUpXAnim.duration = 300
            imgScaleUpXAnim.interpolator = DECCELERATE_INTERPOLATOR
            val imgScaleDownYAnim = ObjectAnimator.ofFloat(bgLikeIcon, "scaleY", 1f, 0f)
            imgScaleDownYAnim.duration = 300
            imgScaleDownYAnim.interpolator = ACCELERATE_INTERPOLATOR
            val imgScaleDownXAnim = ObjectAnimator.ofFloat(bgLikeIcon, "scaleX", 1f, 0f)
            imgScaleDownXAnim.duration = 300
            imgScaleDownXAnim.interpolator = ACCELERATE_INTERPOLATOR
            animatorSet.playTogether(
                imgScaleUpYAnim,
                imgScaleUpXAnim
            )
            animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim)
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    bgLikeIcon.visibility = View.INVISIBLE
                }
            })
            imgScaleUpYAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    bgLikeIcon.visibility = View.VISIBLE
                }
            })
            animatorSet.start()
        }
    }

}