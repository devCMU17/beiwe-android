package org.beiwe.app.survey;

import org.beiwe.app.R;

import android.view.View;
import android.widget.LinearLayout;

public class AnswerGatherer {

	public static void gatherAllAnswers(LinearLayout surveyLayout) {
		LinearLayout questionsLayout = (LinearLayout) surveyLayout.findViewById(R.id.surveyQuestionsLayout);

		for (int i = 0; i < questionsLayout.getChildCount(); i++) {
			View childView = questionsLayout.getChildAt(i);
			String questionType = childView.getTag().toString();
			
			if (questionType.equals("infoTextbox")) {
				
			}
			else if (questionType.equals("sliderQuestion")) {
				
			}
			else if (questionType.equals("radioButtonQuestion")) {
				
			}
			else if (questionType.equals("checkboxQuestion")) {
				
			}
			else if (questionType.equals("openResponseQuestion")) {
				
			}
		}
	}
	
	
	
}
