package com.example.babybossandroidapp.data.model

import com.example.babybossandroidapp.R

object OnboardingInfoList {
    val onboardingModelList = listOf<OnboardingModel>(
        OnboardingModel(R.drawable.onboarding_1, "Добро пожаловать!", "Мы организовываем безопасные и удобные поездки для вашего ребёнка.",),
        OnboardingModel(R.drawable.onboarding_2, "Прозрачные маршруты", "Следите за перемещением ребёнка в реальном времени на карте."),
        OnboardingModel(R.drawable.onboarding_3, "Надёжные водители", "Все водители проходят тщательную проверку и имеют необходимые документы."),
        OnboardingModel(R.drawable.onboarding_4, "Всегда на связи", "Служба поддержки и чат с менеджером — в любое время.")
    )
}