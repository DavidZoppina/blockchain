package com.andtv.flicknplay.workbrowse.data.remote.model.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Colors (

	@SerializedName("--be-primary-lighter") val bePrimaryLighter : List<Double>,
	@SerializedName("--be-primary-default") val bePrimaryDefault : List<Double>,
	@SerializedName("--be-primary-darker") val bePrimaryDarker : List<Double>,
	@SerializedName("--be-accent-default") val beAccentDefault : List<Double>,
	@SerializedName("--be-accent-lighter") val beAccentLighter : List<Double>,
	@SerializedName("--be-accent-contrast") val beAccentContrast : List<Double>,
	@SerializedName("--be-accent-emphasis") val beAccentEmphasis : List<Double>,
	@SerializedName("--be-background") val beBackground : List<Double>,
	@SerializedName("--be-background-alternative") val beBackgroundAlternative : List<Double>,
	@SerializedName("--be-foreground-base") val beForegroundBase : List<Double>,
	@SerializedName("--be-text") val beText : List<Double>,
	@SerializedName("--be-hint-text") val beHintText : List<Double>,
	@SerializedName("--be-secondary-text") val beSecondaryText : List<Double>,
	@SerializedName("--be-label") val beLabel : List<Double>,
	@SerializedName("--be-disabled-button-text") val beDisabledButtonText : List<Double>,
	@SerializedName("--be-divider-lighter") val beDividerLighter : List<Double>,
	@SerializedName("--be-divider-default") val beDividerDefault : List<Double>,
	@SerializedName("--be-hover") val beHover : List<Double>,
	@SerializedName("--be-selected-button") val beSelectedButton : List<Double>,
	@SerializedName("--be-chip") val beChip : List<Double>,
	@SerializedName("--be-link") val beLink : List<Double>,
	@SerializedName("--be-backdrop") val beBackdrop : List<Double>,
	@SerializedName("--be-raised-button") val beRaisedButton : List<Double>,
	@SerializedName("--be-disabled-toggle") val beDisabledToggle : List<Double>,
	@SerializedName("--be-disabled-button") val beDisabledButton : List<Double>
)