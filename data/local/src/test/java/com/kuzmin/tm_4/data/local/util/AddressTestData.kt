package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.data.local.util.Constants.ADDRESS_PREFIX
import com.kuzmin.tm_4.data.local.util.Constants.BUILDING
import com.kuzmin.tm_4.data.local.util.Constants.CITY
import com.kuzmin.tm_4.data.local.util.Constants.COUNTRY
import com.kuzmin.tm_4.data.local.util.Constants.POSTAL_CODE
import com.kuzmin.tm_4.data.local.util.Constants.REGION
import com.kuzmin.tm_4.data.local.util.Constants.REGION_CODE
import com.kuzmin.tm_4.data.local.util.Constants.SITE_PREFIX
import com.kuzmin.tm_4.data.local.util.Constants.STREET
import com.kuzmin.tm_4.data.local.util.Constants.SUB_REGION
import com.kuzmin.tm_4.feature.api.domain.model.sample.AddressSample
import com.kuzmin.tm_4.feature.api.domain.model.site.Address

object AddressTestData {
    fun createTestAddress(sIndex: Int): Address {
        return Address(
            uuid = "$SITE_PREFIX$sIndex-$ADDRESS_PREFIX$sIndex",
            country = COUNTRY,
            region = REGION,
            regionCode = REGION_CODE,
            subRegion = SUB_REGION,
            city = CITY,
            street = STREET,
            building = BUILDING,
            postalCode = POSTAL_CODE
        )
    }

    fun createTestAddressDb(sIndex: Int): AddressDb {
        return AddressDb(
            uuid = "$SITE_PREFIX$sIndex-$ADDRESS_PREFIX$sIndex",
            siteUuid = "site$sIndex",
            country = COUNTRY,
            region = REGION,
            regionCode = REGION_CODE,
            subRegion = SUB_REGION,
            city = CITY,
            street = STREET,
            building = BUILDING,
            postalCode = POSTAL_CODE
        )
    }

    fun createTestAddressSample(sIndex: Int): AddressSample {
        return AddressSample(
            country = COUNTRY,
            city = CITY,
            street = STREET,
            building = BUILDING,
            region = REGION,
            subRegion = SUB_REGION,
            postalCode = POSTAL_CODE
        )
    }
}