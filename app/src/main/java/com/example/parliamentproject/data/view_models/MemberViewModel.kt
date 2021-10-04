package com.example.parliamentproject.data.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.repositories.ReviewRepository

/** A ViewModel subclass which contains all the required data used in the fragment and certain functions for retrieving data.
 * @repository: A reference to the ReviewRepository
 * @member: The currently selected Member object in the MemberFragment.m*/
class MemberViewModel(private val repository: ReviewRepository, val member: Member) : ViewModel() {

    var showReviews = false
        private set
    var memberName = "${member.displayName()} ${member.personNumber}"
        private set
    var ministerInfo = if (member.minister) "Yes" else "No"
        private set
    var seatInfo = member.seatNumber.toString()
        private set
    var ageInfo = member.age.toString()
        private set
    var twitterLink = if (member.twitter != "") member.twitter else "No Twitter"
        private set

    private val _reviewToggleText = MutableLiveData<String>()
    val reviewToggleText : LiveData<String>
        get() = _reviewToggleText

    init {
        _reviewToggleText.value = "Show Reviews"
    }


    /** Retrieves Review objects from the Room Database utilizing the ReviewRepository.
     * @personNumber: the personNumber of the Member object. */
    fun getReviewsByPersonNumber(personNumber: Int) = repository.getReviewsByPersonNumber(personNumber)

    /** Toggles the showReviews Boolean and changes the reviewToggleText String accordingly. */
    fun toggleShowReviews() {
        showReviews = !showReviews
        if (showReviews) _reviewToggleText.value = "Hide Reviews"
        else _reviewToggleText.value = "Show Reviews"
    }

    fun getFullPartyName() : String {
        return when (member.party) {
            "kd" -> "Suomen Kristillisdemokraatit"
            "kesk" -> "Suomen Keskusta"
            "kok" -> "Kansallinen Kokoomus"
            "liik" -> "Liike Nyt"
            "ps" -> "Perussuomalaiset"
            "r" -> "Suomen ruotsalainen kansanpuolue"
            "sd" -> "Suomen Sosialidemokraattinen Puolue"
            "vas" -> "Vasemmistoliitto"
            "vihr" -> "Vihreä liitto"
            else -> ""
        }
    }
}

/** A Factory method for creating or getting an instance of the MemberViewModel object.
 * @repository: Required ReviewRepository type parameter for the MemberViewModel.
 * @member: Required Member type parameter for the MemberViewModel. */
class MemberViewModelFactory(private val repository: ReviewRepository, private val member: Member) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository, member) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}