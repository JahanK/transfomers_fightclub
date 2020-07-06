package com.jnkhan.transfomersfightclub.store

import com.google.gson.annotations.SerializedName

class Transformer() {

    @SerializedName("id")
    var id = 0

    @SerializedName("name")
    var name = ""

    @SerializedName("team")
    var team = ""

    @SerializedName("strength")
    var strength = 0

    @SerializedName("intelligence")
    var intelligence = 0

    @SerializedName("speed")
    var speed = 0

    @SerializedName("endurance")
    var endurance = 0

    @SerializedName("rank")
    var rank = 0

    @SerializedName("courage")
    var courage = 0

    @SerializedName("firepower")
    var firepower = 0

    @SerializedName("skill")
    var skill = 0

    @SerializedName("team_icon")
    var teamIcon = ""

}