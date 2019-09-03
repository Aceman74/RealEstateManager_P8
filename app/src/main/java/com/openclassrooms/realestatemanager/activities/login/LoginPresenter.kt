/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 02/09/19 19:29
 *  
 */

package com.openclassrooms.realestatemanager.activities.login


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.annotations.Nullable
import com.openclassrooms.realestatemanager.utils.base.BasePresenter

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The presenter for Login Activity.
 */
class LoginPresenter : BasePresenter(), LoginContract.LoginPresenterInterface {

    /**
     * Check if user Already Logged
     */
    fun alreadyLogged() {
        if (this.isCurrentUserLogged()!!) {
            //  (getView() as LoginContract.LoginViewInterface).startCoreActivity()
            val name = getCurrentUser()?.displayName
        }
    }

    fun isCurrentUserLogged(): Boolean? {
        return this.getCurrentUser() != null
    }

    @Nullable
    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    /**
     * Create new user on firestore, check if already existing to avoid Erasing data.

    fun createUserInFirestore() {

    if (getCurrentUser() != null) {

    val urlPicture = if (getCurrentUser()?.photoUrl != null) getCurrentUser()?.photoUrl!!.toString() else null
    val username = getCurrentUser()?.displayName
    val userId_fk = getCurrentUser()?.userId_fk
    val email = getCurrentUser()?.email
    val date = DateSetter.formattedDate

    UserHelper.findUserById(getCurrentUser().getUserId_fk()).addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->
    val currentUser = documentSnapshot.toObject<User>(User::class.java)
    if (currentUser == null) {
    UserHelper.createUser(userId_fk, username, urlPicture, email,date)
    }
    })
    }
    }
     */
}

