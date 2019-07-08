package ebookfrenzy.com.scenetransitions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ViewGroup rootContainer;
    Scene scene1;
    Scene scene2;
    Transition transitionMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootContainer = (ViewGroup)findViewById(R.id.rootContainer);

        transitionMgr = TransitionInflater.from(this).inflateTransition(R.transition.transition);

        scene1 = Scene.getSceneForLayout(rootContainer, R.layout.scene1_layout, this);
        scene2 = Scene.getSceneForLayout(rootContainer, R.layout.scene2_layout, this);
        scene1.enter();
    }

    public void goToScene1 (View view) {
        TransitionManager.go(scene1, transitionMgr);


        //TransitionManager.beginDelayedTransition(rootContainer, transitionMgr);
        //Why beginDelayedTransition ko chay???
    }

    public void goToScene2 (View view) {
        TransitionManager.go(scene2, transitionMgr);
    }
}
