package ebookfrenzy.com.fragmentexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment
{
    private static TextView textview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.text_fragment, container, false);

        textview = view.findViewById(R.id.textView1);

        return view;
    }

    public void changeTextProperties(int fronsize, String text) {
        textview.setTextSize(fronsize);
        textview.setText(text);
    }
}
