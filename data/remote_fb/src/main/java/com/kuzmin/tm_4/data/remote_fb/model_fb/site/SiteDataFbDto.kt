package com.kuzmin.tm_4.data.remote_fb.model_fb.site

import com.kuzmin.tm_4.core.network_fb.model.site.ConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.GroupFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.MeasurementConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.MeasurementFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.ResultFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.SectionFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.SiteFbDto

data class SiteDataFbDto(
    val siteFbDto: SiteFbDto,
    val constructions: Map<String, List<ConstructionFbDto>>,
    val measurementConstructions: Map<String, List<MeasurementConstructionFbDto>>? = null,
    //val siteEquipment: Map<String, List<SiteEquipmentDto>>? = null
    val sections: Map<String, List<SectionFbDto>>? = null,
    val groups: Map<String, List<GroupFbDto>>? = null,
    val measurements: Map<String, List<MeasurementFbDto>>? = null,
    val results: Map<String, List<ResultFbDto>>? = null
)