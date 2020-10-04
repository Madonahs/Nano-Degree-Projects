package com.madonasyombua.bakingapp.fragment

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.models.Step
import com.orhanobut.logger.Logger
import java.util.*

class RecipeStepFragment : Fragment() {
    @BindView(R.id.instructions_container)
    var nestedScrollView: NestedScrollView? = null

    @BindView(R.id.exo_player_view)
    var playerView: PlayerView? = null

    @BindView(R.id.step_thumbnail_image)
    var imageView: ImageView? = null

    @BindView(R.id.instruction_text)
    var instructionTextview: TextView? = null
    private var mExoPlayer: SimpleExoPlayer? = null
    private var mStep: Step? = null
    private var unbinder: Unbinder? = null
    private var mCurrentPosition: Long = 0
    private var mPlayWhenReady = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments?.containsKey(STEP_KEY)!!) {
            mStep = arguments?.getParcelable(STEP_KEY)
        }
    }

    override fun onCreateView(@NonNull inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.recipe_step_detail, container, false)
        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)) {
            mCurrentPosition = savedInstanceState.getLong(POSITION_KEY)
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY)
        }
        unbinder = ButterKnife.bind(this, rootView)
        instructionTextview!!.text = mStep?.description
        if (mStep?.thumbnailURL?.isNotEmpty()!!) {
            imageView?.let {
                Glide.with(this)
                        .load(mStep?.thumbnailURL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.cake_image)
                        .into(it)
            }
            imageView?.visibility = View.VISIBLE
        }
        return rootView
    }

    //using the TextUtil
    override fun onResume() {
        super.onResume()
        if (!TextUtils.isEmpty(mStep!!.videoURL)) initializePlayer(Uri.parse(mStep!!.videoURL)) else {
            nestedScrollView?.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
        Logger.d("onDestroyView")
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(POSITION_KEY, mCurrentPosition)
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady)
    }

    //stack overflow reference.
    private fun initializePlayer(mediaUri: Uri) {
        if (mExoPlayer == null) {
            // Create a default TrackSelector
            val bandwidthMeter = DefaultBandwidthMeter()
            val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

            // Create the player
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

            // Bind the player to the view.
            playerView!!.player = mExoPlayer
            // Measures bandwidth during playback. Can be null if not required.
            // Produces DataSource instances through which media data is loaded.
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(Objects.requireNonNull(context), Util.getUserAgent(context, getString(R.string.app_name)), bandwidthMeter)
            // This is the MediaSource representing the media to be played.
            val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri)
            // Prepare the player with the source.
            mExoPlayer!!.prepare(videoSource)

            // onRestore
            if (mCurrentPosition != 0L) mExoPlayer?.seekTo(mCurrentPosition)
            mExoPlayer?.playWhenReady = mPlayWhenReady
            playerView?.visibility = View.VISIBLE
        }
    }

    /**
     * Release ExoPlayer.
     */
    private fun releasePlayer() {
        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer?.playWhenReady!!
            mCurrentPosition = mExoPlayer?.currentPosition!!
            mExoPlayer?.stop()
            mExoPlayer?.release()
            mExoPlayer = null
        }
    }

    companion object {
        const val STEP_KEY = "step_k"
        private const val POSITION_KEY = "pos_k"
        private const val PLAY_WHEN_READY_KEY = "play_when_ready_k"
    }
}