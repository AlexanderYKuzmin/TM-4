package com.kuzmin.tm_4.common.firebase_resource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object FirebaseContainer {
    lateinit var firestore: FirebaseFirestore

    lateinit var fireStorage: FirebaseStorage

    lateinit var fireAuth: FirebaseAuth
}