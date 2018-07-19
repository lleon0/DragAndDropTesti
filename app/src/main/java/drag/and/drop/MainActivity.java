package drag.and.drop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridLayout firstTrack, secondTrack, thirdTrack, fourthTrack, trashcan;

    LinearLayout swimmerbox1, swimmerbox2, swimmerbox3, swimmerbox4, swimmerbox5, swimmerbox6;

    Boolean timerRunning = false;
    Boolean box1inUse = false, box2inUse = false, box3inUse = false, box4inUse = false, box5inUse = false, box6inUse = false;

    Button timerButton, addTrackButton, deleteTrackButton;
    Button addSwimmerButtonOne, addSwimmerButtonTwo, addSwimmerButtonThree, addSwimmerButtonFour;

    int tracks = 1, totalColumns = 2, totalSwimmers = 0;
    int swimmersTrackOne = 0, swimmersTrackTwo = 0, swimmersTrackThree = 0, swimmersTrackFour = 0;

    View viewBox;
    ViewGroup ownerViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.swimmerbox1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.swimmerbox2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.swimmerbox3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.swimmerbox4).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.swimmerbox5).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.swimmerbox6).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.firstTrack).setOnDragListener(new MyDragListener());
        findViewById(R.id.secondTrack).setOnDragListener(new MyDragListener());
        findViewById(R.id.thirdTrack).setOnDragListener(new MyDragListener());
        findViewById(R.id.fourthTrack).setOnDragListener(new MyDragListener());
        findViewById(R.id.trashcan).setOnDragListener(new MyDragListener());

        firstTrack = findViewById(R.id.firstTrack);
        secondTrack = findViewById(R.id.secondTrack);
        thirdTrack = findViewById(R.id.thirdTrack);
        fourthTrack = findViewById(R.id.fourthTrack);
        trashcan = findViewById(R.id.trashcan);

        swimmerbox1 = findViewById(R.id.swimmerbox1);
        swimmerbox2 = findViewById(R.id.swimmerbox2);
        swimmerbox3 = findViewById(R.id.swimmerbox3);
        swimmerbox4 = findViewById(R.id.swimmerbox4);
        swimmerbox5 = findViewById(R.id.swimmerbox5);
        swimmerbox6 = findViewById(R.id.swimmerbox6);

        timerButton = findViewById(R.id.timerButton);
        addTrackButton = findViewById(R.id.addTrackButton);
        deleteTrackButton = findViewById(R.id.deleteTrackButton);
        addSwimmerButtonOne = findViewById(R.id.addSwimmerButtonOne);
        addSwimmerButtonTwo = findViewById(R.id.addSwimmerButtonTwo);
        addSwimmerButtonThree = findViewById(R.id.addSwimmerButtonThree);
        addSwimmerButtonFour = findViewById(R.id.addSwimmerButtonFour);

        setSize();
    }

    public void setSize(){
        //get screen resolution
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        if (tracks == 1){
            //ratoja on 1
            firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 2048/2048));
            firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            firstTrack.requestLayout();
        } else if (tracks == 2){
            //ratoja on 2
            firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 1024/2048));
            firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            firstTrack.requestLayout();

            secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 1024/2048));
            secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            secondTrack.requestLayout();
        } else if (tracks == 3){
            //ratoja on 3
            firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 682/2048));
            firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            firstTrack.requestLayout();

            secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 682/2048));
            secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            secondTrack.requestLayout();

            thirdTrack.getLayoutParams().width = (int) (screenWidth*((double) 682/2048));
            thirdTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            thirdTrack.requestLayout();
        } else if (tracks == 4){
            //ratoja on 4
            firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
            firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            firstTrack.requestLayout();

            secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
            secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            secondTrack.requestLayout();

            thirdTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
            thirdTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            thirdTrack.requestLayout();

            fourthTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
            fourthTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
            fourthTrack.requestLayout();
        }

        swimmerbox1.getLayoutParams().width = (int) (screenWidth*((double) 470/2048));
        swimmerbox1.getLayoutParams().height = (int) (screenHeight*((double) 420/1536));
        swimmerbox1.requestLayout();

        swimmerbox2.getLayoutParams().width = (int) (screenWidth*((double) 470/2048));
        swimmerbox2.getLayoutParams().height = (int) (screenHeight*((double) 420/1536));
        swimmerbox2.requestLayout();

        swimmerbox3.getLayoutParams().width = (int) (screenWidth*((double) 470/2048));
        swimmerbox3.getLayoutParams().height = (int) (screenHeight*((double) 420/1536));
        swimmerbox3.requestLayout();

        swimmerbox4.getLayoutParams().width = (int) (screenWidth*((double) 470/2048));
        swimmerbox4.getLayoutParams().height = (int) (screenHeight*((double) 420/1536));
        swimmerbox4.requestLayout();

        swimmerbox5.getLayoutParams().width = (int) (screenWidth*((double) 470/2048));
        swimmerbox5.getLayoutParams().height = (int) (screenHeight*((double) 420/1536));
        swimmerbox5.requestLayout();

        swimmerbox6.getLayoutParams().width = (int) (screenWidth*((double) 470/2048));
        swimmerbox6.getLayoutParams().height = (int) (screenHeight*((double) 420/1536));
        swimmerbox6.requestLayout();
    }

    public void timerButtonPressed(View v){
        if (timerRunning){
            timerRunning = false;
            timerButton.setText("Timer paused");
            addSwimmerButtonOne.setVisibility(View.VISIBLE);
            addSwimmerButtonTwo.setVisibility(View.VISIBLE);
            addSwimmerButtonThree.setVisibility(View.VISIBLE);
            addSwimmerButtonFour.setVisibility(View.VISIBLE);
            if (tracks > 1){
                deleteTrackButton.setVisibility(View.VISIBLE);
            }
            if (tracks < 4){
                addTrackButton.setVisibility(View.VISIBLE);
            }
        } else if (!timerRunning) {
            timerRunning = true;
            timerButton.setText("Timer running");
            addTrackButton.setVisibility(View.GONE);
            deleteTrackButton.setVisibility(View.GONE);
            addSwimmerButtonOne.setVisibility(View.GONE);
            addSwimmerButtonTwo.setVisibility(View.GONE);
            addSwimmerButtonThree.setVisibility(View.GONE);
            addSwimmerButtonFour.setVisibility(View.GONE);
        }
    }

    public void addTrackButtonPressed(View v){
        if (tracks == 1){
            tracks++;
            setSize();
            deleteTrackButton.setVisibility(View.VISIBLE);
        } else if (tracks == 2){
            tracks++;
            setSize();
        } else if (tracks == 3){
            tracks++;
            setSize();
            addTrackButton.setVisibility(View.GONE);
        }
    }

    public void deleteTrackButtonPressed(View v){
        if (tracks == 2){
            tracks--;
            setSize();
            deleteTrackButton.setVisibility(View.GONE);
        } else if (tracks == 3){
            tracks--;
            setSize();
        } else if (tracks == 4){
            tracks--;
            setSize();
            addTrackButton.setVisibility(View.VISIBLE);
        }
    }

    public void addSwimmerButtonOnePressed(View v){
        if (totalSwimmers == 6){
            Toast.makeText(MainActivity.this, "Maksimi määrä uimareita radoilla.", Toast.LENGTH_LONG).show();
        } else {
            swimmerBoxManager();
            ownerViewGroup = (ViewGroup) viewBox.getParent();
            ownerViewGroup.removeView(viewBox);

            GridLayout container = (GridLayout) firstTrack;
            container.addView(viewBox);
            viewBox.setVisibility(View.VISIBLE);
            totalSwimmers++;
        }
    }

    public void addSwimmerButtonTwoPressed(View v){
        if (totalSwimmers == 6){
            Toast.makeText(MainActivity.this, "Maksimi määrä uimareita radoilla.", Toast.LENGTH_LONG).show();
        } else {
            swimmerBoxManager();
            ownerViewGroup = (ViewGroup) viewBox.getParent();
            ownerViewGroup.removeView(viewBox);

            GridLayout container = (GridLayout) secondTrack;
            container.addView(viewBox);
            viewBox.setVisibility(View.VISIBLE);
            totalSwimmers++;
        }
    }

    public void addSwimmerButtonThreePressed(View v){
        if (totalSwimmers == 6){
            Toast.makeText(MainActivity.this, "Maksimi määrä uimareita radoilla.", Toast.LENGTH_LONG).show();
        } else {
            swimmerBoxManager();
            ownerViewGroup = (ViewGroup) viewBox.getParent();
            ownerViewGroup.removeView(viewBox);

            GridLayout container = (GridLayout) thirdTrack;
            container.addView(viewBox);
            viewBox.setVisibility(View.VISIBLE);
            totalSwimmers++;
        }
    }

    public void addSwimmerButtonFourPressed(View v){
        if (totalSwimmers == 6){
            Toast.makeText(MainActivity.this, "Maksimi määrä uimareita radoilla.", Toast.LENGTH_LONG).show();
        } else {
            swimmerBoxManager();
            ownerViewGroup = (ViewGroup) viewBox.getParent();
            ownerViewGroup.removeView(viewBox);

            GridLayout container = (GridLayout) fourthTrack;
            container.addView(viewBox);
            viewBox.setVisibility(View.VISIBLE);
            totalSwimmers++;
        }
    }

    public void swimmerBoxManager(){
        //täällä testataan onko boxi käytössä (radalla) vai ei (roskakorissa)
        //ensimmäinen vapaa box asetetaan view:ksi, eli sitten kun lisätään radalle uimari se ottaa vapaan view:in jos niitä on
        if (!box1inUse) {
            viewBox = (View) swimmerbox1;
            box1inUse = true;
        } else if (!box2inUse){
            viewBox = (View) swimmerbox2;
            box2inUse = true;
        } else if (!box3inUse){
            viewBox = (View) swimmerbox3;
            box3inUse = true;
        } else if (!box4inUse){
            viewBox = (View) swimmerbox4;
            box4inUse = true;
        } else if (!box5inUse){
            viewBox = (View) swimmerbox5;
            box5inUse = true;
        } else if (!box6inUse){
            viewBox = (View) swimmerbox6;
            box6inUse = true;
        }
    }

    /*
    eli radat tietää kokoajan montako uimaria niillä on, päivittyy liikuttaessa, lisätessä, poistettaessa
    uimareiden määrä vaikuttaa columnien määrään, joka taas vaikuttaa radan leveyteen
    max 4 columns per rata ja total
    on entered voisi jo kasvattaa radan kokoa kun siinä on 3 uimaria ja neljäs viedään kohdalle

    joko joku metodi jossa katsotaan montako uimaria milläkin radalla on,
    ja metodia kutsuttaisiin kun uimareida liikutetaan, lisätään tai poistetaan.

    tai sitten joka paikkaan swimmersTrackOne--; swimmersTrackTwo++; etc... mutta metodi ois parempi.

    TEE VIELÄ NÄMÄ ^ ja että boxi vapautuu kun se poistetaan/heitetään ulos.
     */

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
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    GridLayout container = (GridLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
    /*
    ROSKAKORIIN LISÄÄMINEN JOS HEITTÄÄ BOXIN GRIDIN ULKOPUOLELLE - EI TOIMI VIELÄ

                        view = (View) event.getLocalState();
                        owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        container = (GridLayout) trashcan;
                        container.addView(view);
                        view.setVisibility(View.GONE);
     */
}


