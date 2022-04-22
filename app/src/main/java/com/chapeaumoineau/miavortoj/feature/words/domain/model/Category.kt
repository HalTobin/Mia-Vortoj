package com.chapeaumoineau.miavortoj.feature.words.domain.model

import com.chapeaumoineau.miavortoj.R

data class Category(
    val title: String,
    val icon: Int,
    val id: Int
) {
    companion object {
        val defaultCategories = listOf(
            Category("Other", R.drawable.theme_other, 1),
            Category("Numbers", R.drawable.theme_number, 2),
            Category("House", R.drawable.theme_house, 3),
            Category("Food & Drink", R.drawable.theme_food_drink, 4),
            Category("Greetings", R.drawable.theme_greetings, 5),
            Category("Family", R.drawable.theme_family, 6),
            Category("Religion", R.drawable.theme_religion, 7),
            Category("Body", R.drawable.theme_body, 8),
            Category("Clothes", R.drawable.theme_clothes, 9),
            Category("Weather", R.drawable.theme_weather, 10),
            Category("Date & Time", R.drawable.theme_date_time, 11),
            Category("Animal", R.drawable.theme_animal, 12),
            Category("Space", R.drawable.theme_space, 13),
            Category("Nature", R.drawable.theme_nature, 14),
            Category("Music", R.drawable.theme_music, 15),
            Category("Art", R.drawable.theme_art, 16),
            Category("Color", R.drawable.theme_color, 17),
            Category("Adjective", R.drawable.theme_adjective, 18),
            Category("Verb", R.drawable.theme_verb, 19),
            Category("Communication", R.drawable.theme_communication, 20),
            Category("Health", R.drawable.theme_health, 21),
            Category("Movies", R.drawable.theme_movie, 22),
            Category("Work", R.drawable.theme_work, 23),
            Category("Sport", R.drawable.theme_sport, 24),
            Category("Country & Language", R.drawable.theme_country_language, 25),
            Category("Transport", R.drawable.theme_transport, 26),
            Category("Object", R.drawable.theme_object, 27),
            Category("Place", R.drawable.theme_place, 28)
        )

        fun getDefaultCategory(): Category {
            return defaultCategories[0]
        }

        fun getCategoryById(id: Int): Category {
            var selectCategory: Category = defaultCategories[0]
            var keepLooking = true
            var index = 0

            while(keepLooking) {
                if(defaultCategories[index].id == id) {
                    keepLooking = false
                    selectCategory = defaultCategories[index]
                }
                if(index >= Language.languagesList.size -1) keepLooking = false
                else index++
            }

            return selectCategory
        }
    }
}