package com.kuzmin.tm_4.core.network_fb.fb_service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.kuzmin.tm_4.core.network_fb.model.uer.AuthFbTaskResult
import com.kuzmin.tm_4.core.network_fb.model.uer.UserFb
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseLoginService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val fireAuth: FirebaseAuth
) {

    suspend fun registerUser(userFb: UserFb): UserFb? {
        var userFbCreated: UserFb? = null
        val authResult = fireAuth.createUserWithEmailAndPassword(userFb.email, userFb.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    val uid = fireAuth.currentUser!!.uid

                    userFbCreated = userFb.copy(
                        uid = uid,
                        teamId = if (userFb.isAdmin) uid else NO_TEAM,
                    )
                }
            }.await()

        if (verifyEmail()) {
            firestore.collection("users")
                .document(userFbCreated!!.uid)
                .set(userFbCreated!!)
                .addOnSuccessListener { }
        }
        return userFbCreated
    }

    suspend fun signIn(userFb: UserFb): AuthFbTaskResult? {
        var authFbTaskResult: AuthFbTaskResult? = null
        fireAuth.signInWithEmailAndPassword(userFb.email, userFb.password)
            .addOnSuccessListener {
                authFbTaskResult = AuthFbTaskResult.Success(
                    userFb.copy(
                        uid = it.user!!.uid,
                        teamId = if (userFb.isAdmin) it.user!!.uid else NO_TEAM
                    )
                )
            }
            .addOnFailureListener {
                authFbTaskResult = AuthFbTaskResult.Error(it)
            }
        return authFbTaskResult
    }

    private suspend fun verifyEmail(): Boolean {
        var isEmailVerified = false
        val fireUser = fireAuth.currentUser
        fireUser!!.sendEmailVerification()
            .addOnSuccessListener {
                isEmailVerified = true
            }
            .await()

        return isEmailVerified
    }

    companion object {
        private const val EMAIL = "email"
        private const val USER_UID = "uid"
        private const val TEAM_ID = "team_id"
        private const val IS_ADMIN = "is_admin"

        private const val NO_TEAM = "no_team"
    }

}