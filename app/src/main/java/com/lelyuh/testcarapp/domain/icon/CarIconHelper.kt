package com.lelyuh.testcarapp.domain.icon

import com.lelyuh.testcarapp.R

/**
 * As API doesn't contain information about icons of manufacturers, use local mapping for few popular brands
 * It is not very good for apk size, but nice to user
 *
 * @author Leliukh Aleksandr
 */
object CarIconHelper {

    private val carIconsMap = mapOf(
        "Abarth" to R.mipmap.ic_abarth_logo,
        "Bentley" to R.mipmap.ic_bentley,
        "BMW" to R.mipmap.ic_bmw_logo,
        "Caterham" to R.mipmap.ic_caterham,
        "Fiat" to R.mipmap.ic_fiat,
        "Ford" to R.mipmap.ic_ford,
        "Honda" to R.mipmap.ic_honda,
        "Hyundai" to R.mipmap.ic_hyundai,
        "Jaguar" to R.mipmap.ic_jaguar,
        "Kia" to R.mipmap.ic_kia,
        "Lada" to R.mipmap.ic_lada,
        "Lamborghini" to R.mipmap.ic_lamborgini,
        "Land Rover" to R.mipmap.ic_landrover,
        "Lexus" to R.mipmap.ic_lexus,
        "Maserati" to R.mipmap.ic_mazerati,
        "Mercedes-Benz" to R.mipmap.ic_mercedes,
        "MINI" to R.mipmap.ic_mini,
        "Mitsubishi" to R.mipmap.ic_mitsubishi,
        "Nissan" to R.mipmap.ic_nissan,
        "Peugeot" to R.mipmap.ic_peugeot,
        "Pontiac" to R.mipmap.ic_pontiac,
        "Porsche" to R.mipmap.ic_porsche,
        "Renault" to R.mipmap.ic_renault,
        "Rolls Royce" to R.mipmap.ic_rollsroyce,
        "Saab" to R.mipmap.ic_saab,
        "Skoda" to R.mipmap.ic_scoda,
        "Seat" to R.mipmap.ic_seat,
        "Subaru" to R.mipmap.ic_subaru,
        "Suzuki" to R.mipmap.ic_suzuki,
        "Tesla" to R.mipmap.ic_tesla,
        "Toyota" to R.mipmap.ic_toyota,
        "Volkswagen" to R.mipmap.ic_volkswagen,
        "Volvo" to R.mipmap.ic_volvo
        )

    /**
     * Get car icon by [manufacturerName]
     * If icon didn't find, uses default icon
     */
    fun carIcon(manufacturerName: String) = carIconsMap[manufacturerName] ?: R.mipmap.ic_default_car
}
