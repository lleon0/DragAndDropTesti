package drag.and.drop;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

    Boolean timerRunning = false, previousAlertDone = true, onTrack = true;
    Boolean box1inUse = false, box2inUse = false, box3inUse = false, box4inUse = false, box5inUse = false, box6inUse = false;
    Boolean trackOneInUse = true, trackTwoInUse = false, trackThreeInUse = false, trackFourInUse = false;

    Button timerButton, addTrackButton;
    Button addSwimmerButtonOne, addSwimmerButtonTwo, addSwimmerButtonThree, addSwimmerButtonFour;

    int totalColumns = 2, totalSwimmers = 0, removeTrack = 0;
    int swimmersTrackOne = 0, swimmersTrackTwo = 0, swimmersTrackThree = 0, swimmersTrackFour = 0;

    View viewBox, lastBox;
    ViewGroup ownerViewGroup, lastOwner;

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
        addSwimmerButtonOne = findViewById(R.id.addSwimmerButtonOne);
        addSwimmerButtonTwo = findViewById(R.id.addSwimmerButtonTwo);
        addSwimmerButtonThree = findViewById(R.id.addSwimmerButtonThree);
        addSwimmerButtonFour = findViewById(R.id.addSwimmerButtonFour);

        setSize();
        columnAndTrackManager();
        swimmerBoxManager();
    }

    public void setSize(){
        //get screen resolution
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

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

    public void columnAndTrackManager(){
        //get screen resolution
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        swimmersTrackOne = firstTrack.getChildCount();
        swimmersTrackTwo = secondTrack.getChildCount();
        swimmersTrackThree = thirdTrack.getChildCount();
        swimmersTrackFour = fourthTrack.getChildCount();

        totalSwimmers = swimmersTrackOne + swimmersTrackTwo + swimmersTrackThree + swimmersTrackFour;

        //Toast.makeText(MainActivity.this, "Uimareita radalla: " + totalSwimmers, Toast.LENGTH_SHORT).show();

        //track 1
        if (trackOneInUse){
            if (swimmersTrackOne <= 3){
                firstTrack.setColumnCount(1);
                firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
                firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                firstTrack.requestLayout();
            } else if (swimmersTrackOne >= 4){
                firstTrack.setColumnCount(2);
                firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 1024/2048));
                firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                firstTrack.requestLayout();
            } else if (swimmersTrackOne >= 7){
                firstTrack.setColumnCount(3);
                firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 1536/2048));
                firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                firstTrack.requestLayout();
            } else if (swimmersTrackOne == 10){
                firstTrack.setColumnCount(4);
                firstTrack.getLayoutParams().width = (int) (screenWidth*((double) 2048/2048));
                firstTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                firstTrack.requestLayout();
            }
        }

        //track 2
        if (trackTwoInUse){
            if (swimmersTrackTwo <= 3){
                secondTrack.setColumnCount(1);
                secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
                secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                secondTrack.requestLayout();
            } else if (swimmersTrackTwo >= 4){
                secondTrack.setColumnCount(2);
                secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 1024/2048));
                secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                secondTrack.requestLayout();
            } else if (swimmersTrackTwo >= 7){
                secondTrack.setColumnCount(3);
                secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 1536/2048));
                secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                secondTrack.requestLayout();
            } else if (swimmersTrackTwo == 10){
                secondTrack.setColumnCount(4);
                secondTrack.getLayoutParams().width = (int) (screenWidth*((double) 2048/2048));
                secondTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                secondTrack.requestLayout();
            }
        }

        //track 3
        if (trackThreeInUse){
            if (swimmersTrackThree <= 3){
                thirdTrack.setColumnCount(1);
                thirdTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
                thirdTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                thirdTrack.requestLayout();
            } else if (swimmersTrackThree >= 4){
                thirdTrack.setColumnCount(2);
                thirdTrack.getLayoutParams().width = (int) (screenWidth*((double) 1024/2048));
                thirdTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                thirdTrack.requestLayout();
            } else if (swimmersTrackThree >= 7){
                thirdTrack.setColumnCount(3);
                thirdTrack.getLayoutParams().width = (int) (screenWidth*((double) 1536/2048));
                thirdTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                thirdTrack.requestLayout();
            } else if (swimmersTrackThree == 10){
                thirdTrack.setColumnCount(4);
                thirdTrack.getLayoutParams().width = (int) (screenWidth*((double) 2048/2048));
                thirdTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                thirdTrack.requestLayout();
            }
        }

        //track 4
        if (trackFourInUse){
            if (swimmersTrackFour <= 3){
                fourthTrack.setColumnCount(1);
                fourthTrack.getLayoutParams().width = (int) (screenWidth*((double) 512/2048));
                fourthTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                fourthTrack.requestLayout();
            } else if (swimmersTrackFour >= 4){
                fourthTrack.setColumnCount(2);
                fourthTrack.getLayoutParams().width = (int) (screenWidth*((double) 1024/2048));
                fourthTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                fourthTrack.requestLayout();
            } else if (swimmersTrackFour >= 7){
                fourthTrack.setColumnCount(3);
                fourthTrack.getLayoutParams().width = (int) (screenWidth*((double) 1536/2048));
                fourthTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                fourthTrack.requestLayout();
            } else if (swimmersTrackFour == 10){
                fourthTrack.setColumnCount(4);
                fourthTrack.getLayoutParams().width = (int) (screenWidth*((double) 2048/2048));
                fourthTrack.getLayoutParams().height = (int) (screenHeight*((double) 1380/1536));
                fourthTrack.requestLayout();
            }
        }
    }

    public void checkIfSwimmerDisappeared(){
        if (!onTrack){
            Toast.makeText(MainActivity.this, "Boxi hävisi, siirretään roskakoriin.", Toast.LENGTH_SHORT).show();
            //lastOwner = (ViewGroup) lastBox.getParent();
            //lastOwner.removeView(lastBox);

            //GridLayout container = (GridLayout) firstTrack;
            //container.addView(lastBox);
            // ^ ei toimi , onCreate tyylillä uus boxi

            /*
            ownerViewGroup = (ViewGroup) viewBox.getParent();
            ownerViewGroup.removeView(viewBox);

            GridLayout container = (GridLayout) thirdTrack;
            container.addView(viewBox);
             */

            /*
            if (lastBox == swimmerbox1){
                box1inUse = false;
            } else if (lastBox == swimmerbox2){
                box2inUse = false;
            } else if (lastBox == swimmerbox3){
                box3inUse = false;
            } else if (lastBox == swimmerbox4){
                box4inUse = false;
            } else if (lastBox == swimmerbox5){
                box5inUse = false;
            } else if (lastBox == swimmerbox6){
                box6inUse = false;
            }
            */
        }
    }

    public void testForEmptyTracks(){
        swimmersTrackOne = firstTrack.getChildCount();
        swimmersTrackTwo = secondTrack.getChildCount();
        swimmersTrackThree = thirdTrack.getChildCount();
        swimmersTrackFour = fourthTrack.getChildCount();

        if (swimmersTrackOne == 0 && trackOneInUse && previousAlertDone){
            removeTrack = 1;
            previousAlertDone = false;
            removeTrackPopup();
        } else if (swimmersTrackTwo == 0 && trackTwoInUse && previousAlertDone){
            removeTrack = 2;
            previousAlertDone = false;
            removeTrackPopup();
        } else if (swimmersTrackThree == 0 && trackThreeInUse && previousAlertDone){
            removeTrack = 3;
            previousAlertDone = false;
            removeTrackPopup();
        } else if (swimmersTrackFour == 0 && trackFourInUse && previousAlertDone){
            removeTrack = 4;
            previousAlertDone = false;
            removeTrackPopup();
        }
    }

    public void removeTrackPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Radalla " + String.valueOf(removeTrack) + " ei ole yhtään uimaria.");
        builder.setMessage("Poistetaanko rata?");

        builder.setPositiveButton("KYLLÄ", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (removeTrack == 1){
                    firstTrack.setVisibility(View.GONE);
                    trackOneInUse = false;
                } else if (removeTrack == 2){
                    secondTrack.setVisibility(View.GONE);
                    trackTwoInUse = false;
                } else if (removeTrack == 3){
                    thirdTrack.setVisibility(View.GONE);
                    trackThreeInUse = false;
                } else if (removeTrack == 4){
                    fourthTrack.setVisibility(View.GONE);
                    trackFourInUse = false;
                }
                previousAlertDone = true;
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("EI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
                previousAlertDone = true;
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void timerButtonPressed(View v){
        if (timerRunning){
            timerRunning = false;
            timerButton.setText("Timer paused");
            addSwimmerButtonOne.setVisibility(View.VISIBLE);
            addSwimmerButtonTwo.setVisibility(View.VISIBLE);
            addSwimmerButtonThree.setVisibility(View.VISIBLE);
            addSwimmerButtonFour.setVisibility(View.VISIBLE);
            if (!trackOneInUse || !trackTwoInUse || !trackThreeInUse || !trackFourInUse){
                addTrackButton.setVisibility(View.VISIBLE);
            }
        } else if (!timerRunning) {
            timerRunning = true;
            timerButton.setText("Timer running");
            addTrackButton.setVisibility(View.GONE);
            addSwimmerButtonOne.setVisibility(View.GONE);
            addSwimmerButtonTwo.setVisibility(View.GONE);
            addSwimmerButtonThree.setVisibility(View.GONE);
            addSwimmerButtonFour.setVisibility(View.GONE);
        }
    }

    public void addTrackButtonPressed(View v){
        if (!trackOneInUse){
            trackOneInUse = true;
            firstTrack.setVisibility(View.VISIBLE);
            columnAndTrackManager();
        } else if (!trackTwoInUse){
            trackTwoInUse = true;
            secondTrack.setVisibility(View.VISIBLE);
            columnAndTrackManager();
        } else if (!trackThreeInUse){
            trackThreeInUse = true;
            thirdTrack.setVisibility(View.VISIBLE);
            columnAndTrackManager();
        } else if (!trackFourInUse){
            trackFourInUse = true;
            fourthTrack.setVisibility(View.VISIBLE);
            columnAndTrackManager();
        }
    }

    public void addSwimmerButtonOnePressed(View v){
        ownerViewGroup = (ViewGroup) viewBox.getParent();
        ownerViewGroup.removeView(viewBox);

        GridLayout container = (GridLayout) firstTrack;
        container.addView(viewBox);
        viewBox.setVisibility(View.VISIBLE);
        columnAndTrackManager();
        swimmerBoxManager();
    }

    public void addSwimmerButtonTwoPressed(View v){
        ownerViewGroup = (ViewGroup) viewBox.getParent();
        ownerViewGroup.removeView(viewBox);

        GridLayout container = (GridLayout) secondTrack;
        container.addView(viewBox);
        viewBox.setVisibility(View.VISIBLE);
        columnAndTrackManager();
        swimmerBoxManager();
    }

    public void addSwimmerButtonThreePressed(View v){
        ownerViewGroup = (ViewGroup) viewBox.getParent();
        ownerViewGroup.removeView(viewBox);

        GridLayout container = (GridLayout) thirdTrack;
        container.addView(viewBox);
        viewBox.setVisibility(View.VISIBLE);
        columnAndTrackManager();
        swimmerBoxManager();
    }

    public void addSwimmerButtonFourPressed(View v){
        ownerViewGroup = (ViewGroup) viewBox.getParent();
        ownerViewGroup.removeView(viewBox);

        GridLayout container = (GridLayout) fourthTrack;
        container.addView(viewBox);
        viewBox.setVisibility(View.VISIBLE);
        columnAndTrackManager();
        swimmerBoxManager();
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

        if (totalSwimmers == 6){ //jos totalSwimmers laskenta muuttuu voit testata että onko kaikki boxit käytössä
            addSwimmerButtonOne.setVisibility(View.GONE);
            addSwimmerButtonTwo.setVisibility(View.GONE);
            addSwimmerButtonThree.setVisibility(View.GONE);
            addSwimmerButtonFour.setVisibility(View.GONE);
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

    public void onEntered(){
        onTrack = true;
        Toast.makeText(MainActivity.this, "onTrack: " + onTrack, Toast.LENGTH_SHORT).show();
    }

    public void onExited(){
        onTrack = false;
        Toast.makeText(MainActivity.this, "onTrack: " + onTrack, Toast.LENGTH_SHORT).show();
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
                    onEntered();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    onExited();
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    lastBox = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    lastOwner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    GridLayout container = (GridLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                    columnAndTrackManager();
                    testForEmptyTracks();
                    checkIfSwimmerDisappeared();
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


