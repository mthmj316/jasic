/**
 * This controller is used to control the slides with a slide container.
 * The following functions are offered:
 * 	- jumpsTo(Document, String) -> jumps to the slide with the given id
 *	- next(Document)			-> makes the next slide visible and all others invisible. 
 *		If the last slide is currently visible, than the next slide will be the index slide.
 *	- previsous(Document)		-> makes the slide before the currently visible slide visible and all others invisible.
 *		If the index page is currently visible, than the last slide will be made visible.
 *	- index(Document)			-> makes the index slide visible, and all other invisible
 * Conditions, that this controller presupposes:
 * - The slide container must have the id slide_container
 * - The index slide must have the id slide_0
 */

/**
 * Sets the slide with given slideId visble and all others invisible.
 */
window.jumpsTo = function jumpsTo(document, slideId) {

	setSlideVisibility(document, slideId);
}

/**
 * Makes the next slide in row visible. If there is no next slide,
 * the index slide will be made visible.
 */
window.next = function next(document) {

	// Get the slide id number of the currently visible slide
	const slideId = getVisibleSlideId(document);

	const extractedSlideIdNr = extractIdNumber(slideId);

	// Increment the slide id number by 1 and check if the number exists. 
	// If so compound the slide of the new visible slide, otherwise the 
	// set new slide id to the index page (slide_0).
	const newSlideIdNr = extractedSlideIdNr + 1;

	var newSlideId;

	if (newSlideIdNr < getSlideCount(document)) {
		newSlideId = compoundSlideId(newSlideIdNr);
	} else {
		newSlideId = "slide_0";
	}

	setSlideVisibility(document, newSlideId);
}

/**
 * Makes the slide before the current slide visible.
 * If the first slide is reached the last slide will be made visible.
 */
window.previous = function previsous(document) {

	//Get the slide id number of the currently visible slide
	const slideId = getVisibleSlideId(document);

	const extractedSlideIdNr = extractIdNumber(slideId);

	// Check if the number of the currently selected slide is 0.
	// If so set the number to the last slide , if not decrement the extracted number.
	var newSlideIdNr;
	if (extractedSlideIdNr == 0) {
		newSlideIdNr = getSlideCount(document) - 1;
	} else {
		newSlideIdNr = extractedSlideIdNr - 1;
	}

	const newSlideId = compoundSlideId(newSlideIdNr);

	setSlideVisibility(document, newSlideId);
}

/**
 * Makes the index slide visible an all others invisible.
 */
window.index = function index(document) {

	setSlideVisibility(document, "slide_0");
}

// --------------------------------------------------------------------------------
// --------------------------------------------------------------------------------
// Private methods ----------------------------------------------------------------
/*
* Iterates the slides with in the slide container.
* It returns the id of the first visible slide.
*/
function getVisibleSlideId(document) {

	//Get child elements of the slide container.
	const slides = document.getElementById("slide_container").children;

	//Iterate over each slide and check if the slide is visible, and if so return its ID
	var slideId;

	var slide;
	for (var i = 0; i < slides.length; i++) {

		slide = slides[i];

		if (slide.style.display == "block") {
			slideId = slide.id;
			break;
		}
	}

	return slideId;
}

/*
* Extracts from the given slide id the slide id number:
* slideId == slide_9 -> return 9
*/
function extractIdNumber(slideId) {

	//Split slide id at "_"
	const splitSlideId = slideId.split(/_/);

	//Convert the second part of the slide id to int
	const slideIdNr = parseInt(splitSlideId[1]);

	return slideIdNr;
}

/*
* Creates an slide Id using the given slideIdNr as follows:
* slideId = "slide_" + slideIdNr;
*/
function compoundSlideId(slideIdNr) {
	return "slide_" + slideIdNr;
}

/*
 * Sets the style attribute display of the slide with the given
 * id idOfVisibleSlide to "block", and of all other to "none".
 */
function setSlideVisibility(document, idOfVisibleSlide) {

	//Get child elements of the slide container.
	const slides = document.getElementById("slide_container").children;

	//Iterate over each slide and check if the slide must be visible,
	//at set the display attribute correspondingly.

	var slide;
	for (var i = 0; i < slides.length; i++) {

		slide = slides[i];

		if (slide.id == idOfVisibleSlide) {
			slide.style.display = "block";
		} else {
			slide.style.display = "none";
		}
	}
}

/*
 * Returns the number of child elements within the slide container.
 */
function getSlideCount(document) {

	return document.getElementById("slide_container").children.length;
}