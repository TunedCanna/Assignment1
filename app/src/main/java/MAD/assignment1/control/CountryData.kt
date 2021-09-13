package MAD.assignment1.control

import uni.worksheet3.R

class CountryData {

    companion object {

        private val countryList = mapOf(
            "Andorra" to R.drawable.flag_ad,
            "United Arab Emirates" to R.drawable.flag_ae,
            "Afghanistan" to R.drawable.flag_af,
            "Antigua and Barbuda" to R.drawable.flag_ag,
            "Anguilla" to R.drawable.flag_ai,
            "Albania" to R.drawable.flag_al,
            "Armenia" to R.drawable.flag_am,
            "Argentina" to R.drawable.flag_ar,
            "Austria" to R.drawable.flag_at,
            "Australia" to R.drawable.flag_au,
            "Azerbaijan" to R.drawable.flag_az,
            "Bosnia and Herzegovina" to R.drawable.flag_ba,
            "Belgium" to R.drawable.flag_bd,
            "Burkina Faso" to R.drawable.flag_bf,
            "Bulgaria" to R.drawable.flag_bg,
            "Brazil" to R.drawable.flag_br,
            "Canada" to R.drawable.flag_ca,
            "Switzerland" to R.drawable.flag_ch,
            "China" to R.drawable.flag_cn,
            "Czeck Republic" to R.drawable.flag_cz,
            "Germany" to R.drawable.flag_de,
            "Denmark" to R.drawable.flag_dk,
            "Spain" to R.drawable.flag_es,
            "France" to R.drawable.flag_fr,
            "Great Britain" to R.drawable.flag_gb,
            "Georgia" to R.drawable.flag_ge,
            "Greece" to R.drawable.flag_gr,
            "Hong Kong" to R.drawable.flag_hk,
            "Italy" to R.drawable.flag_it,
            "Japan" to R.drawable.flag_jp,
            "Lithuania" to R.drawable.flag_lt,
            "Mexico" to R.drawable.flag_mx,
            "Malaysia" to R.drawable.flag_my,
            "Neetherlands" to R.drawable.flag_nl,
            "Polland" to R.drawable.flag_pl,
            "Qatar" to R.drawable.flag_qa,
            "Russia" to R.drawable.flag_ru,
            "United Kingdom" to R.drawable.flag_uk,
            "United States of America" to R.drawable.flag_us,
            "Viet Nam" to R.drawable.flag_vn
        )

        fun getCountrySet(): Set<String> {
            return countryList.keys
        }

        fun getFlag(country: String): Int? {
            return countryList[country]
        }
    }

}