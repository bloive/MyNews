package com.example.mynews.api

enum class Endpoint {

    TOP_HEADLINES {
        override val url: String
            get() = ""
    },
    BUSINESS {
        override val url: String
            get() = "business"
    },
    ENTERTAINMENT {
        override val url: String
            get() = "entertainment"
    },
    GENERAL {
        override val url: String
            get() = "general"
    },
    HEALTH {
        override val url: String
            get() = "health"
    },
    SCIENCE {
        override val url: String
            get() = "science"
    },
    SPORTS {
        override val url: String
            get() = "sports"
    },
    TECHNOLOGY {
        override val url: String
            get() = "technology"
    };

    abstract val url: String

}