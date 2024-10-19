package com.kuzmin.tm_4.data.local.mapper.fromModelToDb

import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndSectionsDb
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import javax.inject.Inject

class ConstructionAndSectionsToConstructionAndSectionsDbMapper @Inject constructor()
    : SiteModelToSiteDbMapper() {

    fun mapConstructionAndSectionsToConstructionAndSectionsDb(
        constructionAndSections: ConstructionAndSections
    ): ConstructionAndSectionsDb {
        with(constructionAndSections) {
            return ConstructionAndSectionsDb(
                mapConstructionToConstructionDb(construction),
                mapSectionsToSectionsDb(sections, construction.siteUuid)
            )
        }
    }
}