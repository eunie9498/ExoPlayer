package com.customexoplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.customexoplayer.databinding.FragmentExoBinding
import com.customexoplayer.retro.ExoApi
import com.customexoplayer.retro.MusicResponse
import com.customexoplayer.retro.mapper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExoFragment : Fragment(R.layout.fragment_exo), View.OnClickListener {

    private var binding: FragmentExoBinding? = null
    var isPlaying = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentExoBinding = FragmentExoBinding.bind(view)

        initPlayListButton(fragmentExoBinding)

        binding = fragmentExoBinding

        fragmentExoBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ExoAdapter()
        }

        val api = ExoApi.initRetro()
        api.getMusics().enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { response->
                        val list = response.musics?.mapIndexed { idx, item->
                            item.mapper(idx.toLong())
                        }

                        (fragmentExoBinding.recyclerView.adapter as ExoAdapter).submitList(list)
                    }
                }
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                //
            }
        })

    }

    private fun initPlayListButton(fragmentExoBinding: FragmentExoBinding) {
        fragmentExoBinding.btnList.setOnClickListener {

            //todo 서버에서 데이터 아직 미호출시
            fragmentExoBinding.playListViewGroup.isVisible = isPlaying
            fragmentExoBinding.playerViewGroup.isVisible = isPlaying

            isPlaying = !isPlaying
        }
    }

    override fun onClick(v: View) {
        TODO("Not yet implemented")
    }

    companion object {
        // exofragment를 간접적으로 만들어서 전달
        fun newInstance(): ExoFragment {
            return ExoFragment()
        }
    }
}