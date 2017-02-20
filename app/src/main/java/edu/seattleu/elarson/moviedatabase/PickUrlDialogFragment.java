package edu.seattleu.elarson.moviedatabase;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PickUrlDialogFragment extends DialogFragment {

    private OnPickUrlDialogListener mListener;

    public PickUrlDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }


    // Build an AlertDialog that displays a list of web pages
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.url_dialog_title);
        builder.setItems(WebPageList.get().getTitles(), dialogClickListener);
        builder.setNegativeButton(R.string.cancel_dialog, dialogClickListener);

        return builder.create();
    }

    // Set up a listener for the activity that contains this fragment to allow an interaction
    // in this fragment to be communicated to the activity; this method is called as soon as
    // the fragment is associated with the activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPickUrlDialogListener) {
            mListener = (OnPickUrlDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPickUrlDialogListener");
        }
    }

    // Done with the mListener; method called immediately prior to the fragment no longer being
    // associated with its activity
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // This interface must be implemented by activities that contain this fragment
    // to allow an interaction in this fragment to be communicated to the activity.
    public interface OnPickUrlDialogListener {
        void selectUrl(String url);
    }

    // Set up a listener for clicks in the AlertView; set the movie's url based on the
    // title selected in the AlertView
    private final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int numWebPages = WebPageList.get().getWebPages().size();
            String url;
            if (which >= 0 && which < numWebPages) {
                url = WebPageList.get().getUrl(which);
                mListener.selectUrl(url);
            }
        }
    };
}
