package com.hh.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO {

	private String name;
	private Integer patientId;
	private Integer packageId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer price;
	private Integer amountPaid;
	private String paymentMethod;
	@Min(0)
	private int totalSessions;
	private int completedSessions;
	private boolean fullyPaid;
	
	
	//Enquiry : SPIRIT
	private String complexion;
	private String eyes;
	private String stateOfMind;
	
	
	//Enquiry : Appearance
	private boolean movementAndTranquility;
	private boolean coughAndPanting;
	private boolean convulsiveSpasm;
	private boolean paralysis;
	private boolean chestAbdomenPattern;
	private boolean delirious;
	private boolean overweight;
	private boolean skinny;
	private boolean violentMovementsForLimbs;
	private boolean face;
	
	//Enquiry : Listening Voice;
	private boolean deliriousSpeak;
	private boolean mumbling;
	private boolean lossOfVoice;
	
	//Enquiry : Respiration
	private boolean breathlessNess;
	private boolean wheezing;
	private boolean shortnessOfBreath;
	private boolean panting;
	
	//Enquiry : Smelling
	private String breathOdor;
	private String sweatOdod;
	private boolean odorUnderArmpit;
	
	//Enquiry : Food and appetite
	private boolean poorAppetite;
	private boolean lackOfAppetiteWithFullnessOfStomach;
	private boolean hunderWithNoDesireToEat;
	private boolean getHungryEasy;
	private boolean inabilityToFeelTaste;
	private String taste;
	
	//Enquiry : Chills and fever
	private boolean patternOfExteriorInteriorOrBoth;
	private boolean alternateChillsOrFever;
	private boolean persistentHighFever;
	private boolean middlefever;
	private boolean tidalFever;
	private boolean irritableVexingFever;
	
	//Enquiry : Stool and urine
	private String stoolConsistency;
	private boolean flatulency;
	private boolean prolapseAnus;
	private boolean hemorrhoids;
	private String urine;
	
	//Enquiry : Sweating and perspiration
	private boolean spontaneousSweating;
	private boolean nightSweating;
	private boolean yellowSweatingStainOnClothes;
	private boolean expiringSweating;
	
	//Enquiry : insomnia
	private boolean difficultToSleep;
	private boolean sleepButKeepWakingUp;
	private boolean fearToFallSleep;
	private boolean sleepSomethingPullBack;
	private boolean sleepButWakeupInMiddleNightSameTime;
	private boolean tooManyThoughtsBeforeSleep;
	private boolean tooMuchDreams;
	private String dreamsAboutWhat;
	
	//Enquiry: Tinnitus
	private boolean loudHighPitch;
	private boolean lowPichNoise;
	private boolean deafnessOrHearingImpairment;
	private boolean redPainFullEyes;
	private boolean blurryCloudyVision;
	
	//Enquiry : Menstruration
	private boolean mensturalDisorder;
	private boolean mensturalPain;
	private boolean ysmenorrhea;
	private boolean leucorrhea;
	private boolean vaginalDischarge;
	private boolean frequency;
	private boolean mensBloodQuantity;
	private boolean mensBloodColor;
	private boolean mensBloodSmell;
	
	//Enquiry : others
	private String pain;
	private boolean dizziness;
	
	
	
	
	
}
