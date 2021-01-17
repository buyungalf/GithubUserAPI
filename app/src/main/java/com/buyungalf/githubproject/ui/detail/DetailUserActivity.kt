package com.buyungalf.githubproject.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.buyungalf.githubproject.databinding.ActivityDetailUserBinding

class DetailUserActivity: AppCompatActivity() {
    companion object {
        const val USERNAME = "username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    idName.text = it.name
                    idUsername.text = it.login
                    idFollowers.text = "${it.followers} Followers"
                    idFollowing.text = "${it.following} Following"

                    Glide.with(this@DetailUserActivity)
                            .load(it.avatar_url)
                            .into(idProfile)
                }
            }
        })

        val sectionPagerAdaptor = SectionPagerAdaptor(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdaptor
            tabs.setupWithViewPager(viewPager)
        }

    }

}