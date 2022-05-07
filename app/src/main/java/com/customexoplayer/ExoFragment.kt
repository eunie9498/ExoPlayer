package com.customexoplayer

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.customexoplayer.databinding.FragmentExoBinding
import com.customexoplayer.retro.*
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExoFragment : Fragment(R.layout.fragment_exo), View.OnClickListener {

    private var playModel = PlayModel()
    private var binding: FragmentExoBinding? = null
    private lateinit var musicAdapter: ExoAdapter
    private var exoPlayer: SimpleExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentExoBinding = FragmentExoBinding.bind(view)
        binding = fragmentExoBinding

        initPlayView(fragmentExoBinding)
        initPlayListButton(fragmentExoBinding)
        ininPlayBtn(fragmentExoBinding)
        initRecyclerView(fragmentExoBinding)

        val api = ExoApi.initRetro()
        api.getMusics().enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { response ->
                        playModel = response.mapper()
                        setMucisList(playModel.getAdapterModels())
                        musicAdapter.submitList(playModel.getAdapterModels())
                    }
                }
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                //
            }
        })
    }

    private fun setMucisList(list: List<MusicList>) {
        //add list can playing
        exoPlayer!!.addMediaItems(list.map { model ->
            MediaItem.Builder().setMediaId(model.id.toString())
                .setUri(model.streamUrl).build()
        })
        exoPlayer!!.prepare()
    }

    private fun playMusic(model: MusicList) {
        playModel.updateCurrentPosition(model)
        exoPlayer!!.seekTo(playModel.currentPosition, 0)
        exoPlayer!!.play()
    }

    private fun ininPlayBtn(fragmentExoBinding: FragmentExoBinding) {
        fragmentExoBinding.btnPlay.setOnClickListener {
            val player = this.exoPlayer ?: return@setOnClickListener
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }

        fragmentExoBinding.btnBack.setOnClickListener {
            val next = playModel.priMusic() ?: return@setOnClickListener
            playMusic(next)
        }

        fragmentExoBinding.btnNext.setOnClickListener {
            val next = playModel.nextMusic() ?: return@setOnClickListener
            playMusic(next)
        }
    }

    private fun initPlayView(fragmentExoBinding: FragmentExoBinding) {
        exoPlayer = SimpleExoPlayer.Builder(requireContext()).build()

        fragmentExoBinding.playerView.player = exoPlayer

        binding?.let { binding ->
            exoPlayer!!.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    if (isPlaying) {
                        binding.btnPlay.setImageResource(R.drawable.ic_pause)
                    } else {
                        binding.btnPlay.setImageResource(R.drawable.ic_play)
                    }
                }

                //when called media item changed
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)

                    val nexIdx = mediaItem?.mediaId ?: return
                    playModel.currentPosition = nexIdx.toInt()

                    musicAdapter.submitList(playModel.getAdapterModels())
                }
            })
        }
    }

    private fun initRecyclerView(fragmentExoBinding: FragmentExoBinding) {
        musicAdapter = ExoAdapter {
            playMusic(it)
        }

        fragmentExoBinding.recyclerView.apply {
            adapter = musicAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initPlayListButton(fragmentExoBinding: FragmentExoBinding) {
        fragmentExoBinding.btnList.setOnClickListener {

            // 서버에서 데이터 아직 미호출시 -> currentPosition이 -1
            if (playModel.currentPosition == -1) return@setOnClickListener

            fragmentExoBinding.playListViewGroup.isVisible = playModel.isInPlayListView
            fragmentExoBinding.playerViewGroup.isVisible = !playModel.isInPlayListView

            playModel.isInPlayListView = !playModel.isInPlayListView
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