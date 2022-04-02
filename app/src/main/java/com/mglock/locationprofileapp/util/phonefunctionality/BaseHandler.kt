package com.mglock.locationprofileapp.util.phonefunctionality

import com.mglock.locationprofileapp.util.enums.DetailActionOption

interface BaseHandler {

    fun executeTask(option: DetailActionOption, optionValue: String, optionMode: String?)
}