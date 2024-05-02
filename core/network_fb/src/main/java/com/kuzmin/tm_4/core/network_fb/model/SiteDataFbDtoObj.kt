package com.kuzmin.tm_4.core.network_fb.model

import com.google.firebase.firestore.DocumentSnapshot

data class SiteDataFbDtoObj (
    val siteFbDto: DocumentSnapshot,

    val constructions: Map<String, List<DocumentSnapshot>>,

    val sections: Map<String, List<DocumentSnapshot>>? = null,

    val measurementConstructions: Map<String, List<DocumentSnapshot>>? = null,

    val groups: Map<String, List<DocumentSnapshot>>? = null,

    val measurements: Map<String, List<DocumentSnapshot>>? = null,

    val results: Map<String, List<DocumentSnapshot>>? = null
)