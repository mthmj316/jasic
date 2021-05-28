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

window.jumpsTo = function jumpsTo(document, slideId){
	
}

window.next = function next(document){
	
}

window.previsous = function previsous(document){
	
}

window.index = function index(document){
	
}

// --------------------------------------------------------------------------------
// --------------------------------------------------------------------------------
// Private methods ----------------------------------------------------------------

function getVisibleSlideId(document){
	
}

function extractIdNumber(slideId){
	
}


function compoundSlideId(slideIdNr){
	
}

/*
 * Sets the style attribute display of the slide with the given
 * id idOfVisibleSlide to "block", and of all other to "none".
 */
function setSlideVisibility(document, idOfVisibleSlide){
	
	//Get child elements of the slide container.
	const slides = document.getElementById("slide_container").childNodes;
	
	//Iterate over each slide and check if the slide must be visible,
	//at set the display attribute correspondingly.
	slides.forEach(function (slide){
		
		if(slide.id == idOfVisibleSlide){
			slide.style.display = "block";
		} else {
			slide.style.display = "none";
		}
	});
}

/*
 * Returns the number of child elements within the slide container.
 */
function getSlideCount(document) {
	
	return document.getElementById("slide_container").childNodes.length;
}