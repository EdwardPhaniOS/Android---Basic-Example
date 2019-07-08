package ebookfrenzy.com.transitiondemo;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.Transition;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.transition.ChangeBounds;
import android.view.animation.BounceInterpolator;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLayout = (ConstraintLayout)findViewById(R.id.myLayout);

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouch();
                return true;
            }
        });
    }

    public void handleTouch(){
        Button button = (Button)findViewById(R.id.myButton);

        Transition changeBounds = new ChangeBounds();
        changeBounds.setDuration(3000);
        changeBounds.setInterpolator(new BounceInterpolator());

        TransitionManager.beginDelayedTransition(myLayout, changeBounds);

        button.setMinimumWidth(500);
        button.setHeight(350);

        ConstraintSet set = new ConstraintSet();

        set.connect(R.id.myButton, ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        set.connect(R.id.myButton, ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
        set.constrainWidth(R.id.myButton, ConstraintSet.WRAP_CONTENT);

        set.applyTo(myLayout);
    }

}
