package de.koelle.christian.trickytripper.model.modelAdapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import de.koelle.christian.trickytripper.ui.model.SpinnerObject;

public class ReportParticipantSpinnerAdapter extends ArrayAdapter<SpinnerObject>{
    
    

    public ReportParticipantSpinnerAdapter(Context context, int textViewResourceId, List<SpinnerObject> rows) {
        super(context, textViewResourceId, rows);
    }

}
