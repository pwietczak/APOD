plugins {
	id("apod.android.library")
}

android {
	buildTypes {
		release {
			optimization {
				enable = true
				packageScope =
					setOf(
						"androidx.**",
						"kotlin.**",
						"kotlinx.**",
					)
			}
		}
	}
}
