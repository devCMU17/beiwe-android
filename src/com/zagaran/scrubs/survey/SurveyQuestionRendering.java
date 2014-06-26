package com.zagaran.scrubs.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zagaran.scrubs.R;

public class SurveyQuestionRendering {

	private Context appContext;
	private LayoutInflater inflater;
	
	public SurveyQuestionRendering(Context applicationContext) {
		appContext = applicationContext;
		inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		/* XML views inflated by an Activity render with the app's default
		 * style (set in the Manifest.XML), but for some reason, XML views
		 * inflated by this class don't render with the app's default style,
		 * unless we set it manually: */
		appContext.setTheme(R.style.AppTheme);
	}

	
	/**
	 * Creates an informational text view that does not have an answer type
	 * @param infoText The informational text
	 * @return TextView (to be displayed as question text)
	 */
	public TextView createInfoTextbox(String infoText) {
		TextView infoTextbox = (TextView) inflater.inflate(R.layout.survey_info_textbox, null);
		
		// Clean inputs
		if (infoText == null) {
			infoText = appContext.getResources().getString(R.string.question_error_text);
		}
		
		// Set the question text
		infoTextbox.setText(infoText);
		
		return infoTextbox;
	}


	/**
	 * Creates a slider with a range of discrete values
	 * @param questionText The text of the question to be asked
	 * @param numberOfValues A range of "0-4" has 5 values
	 * @param defaultValue Starts at 0; can be as high as (numberOfValues - 1)
	 * @return LinearLayout A slider bar
	 */
	public LinearLayout createSliderQuestion(String questionText, int numberOfValues, int defaultValue) {
		LinearLayout question = (LinearLayout) inflater.inflate(R.layout.survey_slider_question, null);
		SeekBar slider = (SeekBar) question.findViewById(R.id.slider);
		
		// Set the text of the question itself
		TextView questionTextView = (TextView) question.findViewById(R.id.questionText);
		if (questionText != null) {
			questionTextView.setText(questionText);
		}
		
		// Clean inputs/force them to be usable numbers
		if (numberOfValues < 2) { numberOfValues = 2; }
		if (numberOfValues > 100) {	numberOfValues = 100; }
		if (defaultValue < 0) {	defaultValue = 0; }
		if (defaultValue > numberOfValues - 1) { defaultValue = numberOfValues - 1; }
		
		// Set the slider's range and default/starting value
		slider.setMax(numberOfValues - 1);
		slider.setProgress(defaultValue);
		
		return question;
	}
	
	
	/**
	 * Creates a group of radio buttons
	 * @param questionText The text of the question
	 * @param answers An array of strings that are options matched with radio buttons
	 * @return RadioGroup A vertical set of radio buttons 
	 */
	public LinearLayout createRadioButtonQuestion(String questionText, String[] answers) {
		LinearLayout question = (LinearLayout) inflater.inflate(R.layout.survey_radio_button_question, null);
		RadioGroup radioGroup = (RadioGroup) question.findViewById(R.id.radioGroup);
		
		// Set the text of the question itself
		TextView questionTextView = (TextView) question.findViewById(R.id.questionText);
		if (questionText != null) {
			questionTextView.setText(questionText);
		}
		
		// If the array of answers is null or too short, replace it with an error message
		if ((answers == null) || (answers.length < 2)) {
			String replacementAnswer = appContext.getResources().getString(R.string.question_error_text);
			String[] replacementAnswers = {replacementAnswer, replacementAnswer};
			answers = replacementAnswers;
		}
		
		// Loop through the answer strings, and make each one a radio button option
		for (int i = 0; i < answers.length; i++) {
			RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.survey_radio_button, null);
			if (answers[i] != null) {
				radioButton.setText(answers[i]);
			}
			radioGroup.addView(radioButton);
		}
		
		return question;
	}
	

	/**
	 * Creates a question with an array of checkboxes
	 * @param questionText The text of the question
	 * @param options Each string in options[] will caption one checkbox
	 * @return LinearLayout a question with a list of checkboxes
	 */
	public LinearLayout createCheckboxQuestion(String questionText, String[] options) {
		LinearLayout question = (LinearLayout) inflater.inflate(R.layout.survey_checkbox_question, null);
		LinearLayout checkboxesList = (LinearLayout) question.findViewById(R.id.checkboxesList);
		
		// Set the text of the question itself
		TextView questionTextView = (TextView) question.findViewById(R.id.questionText);
		if (questionText != null) {
			questionTextView.setText(questionText);
		}
		
		// Loop through the options strings, and make each one a checkbox option
		if (options != null) {
			for (int i = 0; i < options.length; i++) {
				CheckBox checkbox = (CheckBox) inflater.inflate(R.layout.survey_checkbox, null);
				if (options[i] != null) {
					checkbox.setText(options[i]);
				}
				checkboxesList.addView(checkbox);
			}			
		}
		
		return question;		
	}
	
	
	/**
	 * Creates a question with an open-response, text-input field
	 * @param questionText The text of the question
	 * @param inputTextType The type of answer (number, text, etc.)
	 * @return LinearLayout question and answer
	 */
	public LinearLayout createFreeResponseQuestion(String questionText, SurveyTextFieldType.Type inputTextType) {
		LinearLayout question = (LinearLayout) inflater.inflate(R.layout.survey_open_response_question, null);

		// Set the text of the question itself
		TextView questionTextView = (TextView) question.findViewById(R.id.questionText);
		if (questionText != null) {
			questionTextView.setText(questionText);
		}
		
		EditText editText = null;
		switch (inputTextType) {
		case NUMERIC:
			editText = (EditText) inflater.inflate(R.layout.survey_free_number_input, null);
			break;
			
		case SINGLE_LINE_TEXT:
			editText = (EditText) inflater.inflate(R.layout.survey_free_text_input, null);			
			break;
			
		case MULTI_LINE_TEXT:
			editText = (EditText) inflater.inflate(R.layout.survey_multiline_text_input, null);			
			break;

		default:
			editText = (EditText) inflater.inflate(R.layout.survey_free_text_input, null);			
			break;
		}
		LinearLayout textFieldContainer = (LinearLayout) question.findViewById(R.id.textFieldContainer);
		textFieldContainer.addView(editText);
		
		// TODO: prevent the EditText from gaining focus- see here: http://stackoverflow.com/questions/1555109/stop-edittext-from-gaining-focus-at-activity-startup
		// TODO: on press carriage return, move to next question
		// TODO: add date and time pickers as input types: http://stackoverflow.com/a/14933515
		// TODO: when you rotate the screen, the EditTexts get wiped clear, and some of the sliders jump to 100%. Debug this.
		
		return question;
	}

}