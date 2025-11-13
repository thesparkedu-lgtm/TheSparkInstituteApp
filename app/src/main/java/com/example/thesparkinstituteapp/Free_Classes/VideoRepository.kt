package com.example.thesparkinstituteapp.Free_Classes

data class Video(val title: String, val videoId: String)

object VideoRepository {
    val myVideos = listOf(
        Video("Number and Numeric System -JNVST CLASS 6 (Maths in English)", "E4SEcvNdV68"),
        Video("Number and Numeric System -JNVST CLASS 6 (Maths in Hindi)", "thOCgblf7CU"),
        Video("Number and Numeric System -JNVST CLASS 6 (Maths in Bangla)", "wlqEH3xWEUw"),
        Video("Your Mission HCF & LCM -JNVST CLASS 6 (Maths in English)", "b8l3MC3EBo0"),
        Video("Your Mission HCF & LCM -JNVST CLASS 6 (Maths in Hindi)", "eyySr6m9WLA"),
        Video("Your Mission HCF & LCM -JNVST CLASS 6 (Maths in Bangla)", "Dk5pN9HUvmY")
    )
}
