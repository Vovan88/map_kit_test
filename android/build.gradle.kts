allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val newBuildDir: Directory =
    rootProject.layout.buildDirectory
        .dir("../../build")
        .get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
}
subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {

            if (requested.group == "com.yandex.android" && requested.name == "maps.mobile") {
                // Принудительно подставляем Full-версию 4.33.1
                useVersion("4.33.1-full")
            }
        }
    }
}