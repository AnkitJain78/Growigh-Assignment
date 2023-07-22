package com.example.assignment.presentation.videos

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
import com.example.assignment.R
import com.example.assignment.databinding.ItemVideoBinding
import com.example.assignment.domain.model.Video

class VideoAdapter(
    private val videoList: List<Video>,
    private val context: Context
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private var doubleClickLastTime = 0L

    inner class VideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setVideo(video: Video) {
            binding.videoView.setVideoPath(video.link);
            binding.videoView.setOnPreparedListener { mediaPlayer ->
                binding.progressVideo.visibility = View.GONE;
                mediaPlayer.start();
                // getting video ratio/screen ratio so that it can fix in screen
                val videoRatio = mediaPlayer.videoWidth / mediaPlayer.videoHeight.toFloat()
                val screenRatio = binding.videoView.width / binding.videoView.height.toFloat()
                val scale = videoRatio / screenRatio
                if (scale >= 1f) {
                    binding.videoView.scaleX = scale
                } else {
                    binding.videoView.scaleY = 1f / scale
                }
            }

            binding.videoView.setOnCompletionListener {
                it.start()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        with(holder.binding) {
            with(videoList[position]) {
                sourceName.text = this.source
                videoTitle.text = this.title
                likeIcon.setOnClickListener {
                    if (!isLiked) {
                        this.isLiked = true
                        updateHeartButton(holder, true)
                    } else {
                        this.isLiked = false
                        updateHeartButton(holder, false)
                    }
                }
                holder.setVideo(this)
                shareIcon.setOnClickListener {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = this.link
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
                }
                videoView.setOnClickListener {
                    if (System.currentTimeMillis() - doubleClickLastTime < 300) {
                        doubleClickLastTime = 0
                        animatePhotoLike(holder)
                        if (!isLiked) {
                            isLiked = true
                            updateHeartButton(holder, true)
                        }
                    } else
                        doubleClickLastTime = System.currentTimeMillis()
                }
            }
        }
    }

    override fun getItemCount(): Int = videoList.size

    private fun updateHeartButton(holder: VideoViewHolder, animated: Boolean) {
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
                likeIcon.setImageResource(R.drawable.ic_video_heart)
        }
    }

    private fun animatePhotoLike(holder: VideoViewHolder) {
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