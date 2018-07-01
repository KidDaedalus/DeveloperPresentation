package com.github.kiddaedalus.presentation

import kotlin.math.roundToLong

class TimelineStage(val animations: List<List<Animated>>): List<List<Animated>> by animations {

    val durationFrames: Long = animations
            .map {
                it.map { it.durationFrames }.sum()
            }.max() ?: 0

    val durationMillis: Long = (durationFrames * Application.framesPerMilli).roundToLong()
}

class AnimationTimeline(val stages:  List<TimelineStage>):  List<TimelineStage> by stages {

    val durationFrames: Long = stages.map { it.durationFrames }.sum()
    val durationMillis: Long = stages.map { it.durationMillis }.sum()
}
