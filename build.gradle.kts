// File ini dibiarkan minimalis, karena versi Gradle modern tidak perlu lagi `buildscript {}`

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
