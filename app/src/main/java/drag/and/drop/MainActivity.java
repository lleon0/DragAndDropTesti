package drag.and.drop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridLayout topLeft, topRight, bottomLeft;
    boolean isItgone = true;
    private Button Hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topLeft = findViewById(R.id.topleft);
        topRight = findViewById(R.id.topright);
        bottomLeft = findViewById(R.id.bottomleft);
        Hide = findViewById(R.id.hide);

        findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage5).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());

        /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myimage6 = inflater.inflate(R.layout.swimmer_databox_layout, null);
        // Add the new row before the add field button.

        myimage6.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        myimage6.getLayoutParams().width = (int) 120;
        myimage6.getLayoutParams().height = (int) 120;
        myimage6.requestLayout();

        topLeft.addView(myimage6, topLeft.getChildCount() - 1);
        myimage6.setOnTouchListener(new MyTouchListener());*/
    }

    public void NewDatabox(View view) {
        if (isItgone){
            topRight.setVisibility(View.VISIBLE);
            bottomLeft.setVisibility(View.VISIBLE);
            for (int i = 0; i < bottomLeft.getChildCount(); i++) {
                View child = bottomLeft.getChildAt(i);
                //child.setEnabled(false);
                child.setVisibility(View.VISIBLE);
            }
            isItgone = false;
        }
        else if (!isItgone){
            topRight.setVisibility(View.GONE);
            bottomLeft.setVisibility(View.GONE);
            for (int i = 0; i < bottomLeft.getChildCount(); i++) {
                View child = bottomLeft.getChildAt(i);
                //child.setEnabled(false);
                child.setVisibility(View.GONE);
            }
            isItgone = true;
        }
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Hide.setVisibility(View.GONE);
                    bottomLeft.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    //LinearLayout container = (LinearLayout) v;
                    GridLayout container = (GridLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                    for (int i = 0; i < bottomLeft.getChildCount(); i++) {
                        View child = bottomLeft.getChildAt(i);
                        //child.setEnabled(false);
                        child.setVisibility(View.GONE);
                    }
                    bottomLeft.setVisibility(View.GONE);
                    v.setBackground(normalShape);
                    Hide.setVisibility(View.VISIBLE);
                default:
                    break;
            }
            return true;
        }
    }
}