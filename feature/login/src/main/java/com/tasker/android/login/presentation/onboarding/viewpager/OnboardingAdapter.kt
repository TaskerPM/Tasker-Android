package com.tasker.android.login.presentation.onboarding.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.login.databinding.LayoutOnboardingImageBinding

class OnboardingAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {

        context = parent.context

        return OnboardingViewHolder(
            LayoutOnboardingImageBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.setData()
    }

    inner class OnboardingViewHolder(private val binding: LayoutOnboardingImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            binding.ivOnboardingImage.setImageResource(images[adapterPosition])
        }
    }
}