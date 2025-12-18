rootProject.name = "ScanAndTrack"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

// APP
include(":composeApp")

// COMMON
include(":common:mvi:mvi-general")
include(":common:mvi:mvi-koin-voyager")
include(":common:logger")
include(":common:utils")

// CORE
include(":core:recources")
include(":core:database")

// COMPONENTS
include(":components:item")

// FEATURE
include(":feature:items-list-screen:items-list-screen-api")
include(":feature:items-list-screen:items-list-screen-impl")
include(":feature:add-item-screen:add-item-screen-api")
include(":feature:add-item-screen:add-item-screen-impl")
