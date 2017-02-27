package com.example.naylo.hymnbookconverter;

import android.content.ClipData;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    private EditText oldNumber, newNumber, oldName, newName;
    private TextView Suggestion0, Suggestion1, Suggestion2, Suggestion3, Suggestion4;

    //List of book index items
    List<BookIndexItem> songs = new ArrayList<BookIndexItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the Text Fields For Manipulation
        oldNumber = (EditText) findViewById(R.id.oldNumber);
        newNumber = (EditText) findViewById(R.id.newNumber);
        oldName = (EditText) findViewById(R.id.oldName);
        newName = (EditText) findViewById(R.id.newName);

        //Get the Search Suggestions Text Views
        Suggestion0 = (TextView) findViewById(R.id.Suggestion0);
        Suggestion1 = (TextView) findViewById(R.id.Suggestion1);
        Suggestion2 = (TextView) findViewById(R.id.Suggestion2);
        Suggestion3 = (TextView) findViewById(R.id.Suggestion3);
        Suggestion4 = (TextView) findViewById(R.id.Suggestion4);

        //Build List of Suggestion Boxes
        final ArrayList<TextView> Suggestions = new ArrayList<TextView>();
        Suggestions.add(Suggestion0);
        Suggestions.add(Suggestion1);
        Suggestions.add(Suggestion2);
        Suggestions.add(Suggestion3);
        Suggestions.add(Suggestion4);

        //Set the focus to the old number and pull up the key board
        oldNumber.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //Set the focus Listeners for the EditText to change the layout and placeholder
        newNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    newNumber.setText("");
                    newName.setText("New Name");
                    oldNumber.setText("Old Number");
                    oldName.setText("Old Name");
                    setForOldOrNumber();
                }
            }
        });
        newName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    newNumber.setText("New Number");
                    newName.setText("");
                    oldNumber.setText("Old Number");
                    oldName.setText("Old Name");
                    setForOldOrNumber();
                }
            }
        });
        oldName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    newNumber.setText("New Number");
                    newName.setText("New Name");
                    oldNumber.setText("Old Number");
                    oldName.setText("");
                    setForOldOrNumber();
                }
            }
        });
        oldNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    newNumber.setText("New Number");
                    newName.setText("New Name");
                    oldNumber.setText("");
                    oldName.setText("Old Name");
                    setForOldOrNumber();
                }
            }
        });
        //Set Value Change Listeners
        oldNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (oldNumber.hasFocus()) {
                    if (s.length() > 0) {
                        BookIndexItem found = OldNumberSearch(Integer.parseInt(s.toString()));
                        if (found.newNumber != 0) {
                                newNumber.setText(String.valueOf(found.newNumber));
                                newName.setText(found.newName);
                                oldName.setText(found.oldName);
                        }
                        else {
                            newNumber.setText("Unknown");
                            newName.setText("Unknown");
                            oldName.setText("Unknown");
                        }
                    } else {
                        newNumber.setText("New Number");
                        newName.setText("New Name");
                        oldName.setText("Old Name");
                    }
                }
            }
        });
        newNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (newNumber.hasFocus()) {
                    if (s.length() > 0) {
                            BookIndexItem found = NewNumberSearch(Integer.parseInt(s.toString()));
                            if (found.newNumber != 0) {
                                oldNumber.setText(String.valueOf(found.oldNumber));
                                newName.setText(found.newName);
                                oldName.setText(found.oldName);
                            }
                            else {
                                oldNumber.setText("Unknown");
                                newName.setText("Unknown");
                                oldName.setText("Unknown");
                            }
                    } else {
                        oldNumber.setText("Old Number");
                        newName.setText("New Name");
                        oldName.setText("Old Name");
                    }
                }
            }
        });
        oldName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (oldName.hasFocus()) {
                    if (s.length() > 0) {
                        Boolean Exact = false;
                        oldNumber.setText(String.valueOf("Unknown"));
                        newName.setText("Unknown");
                        newNumber.setText("Unknown");
                        for (BookIndexItem posiblematch: songs
                                ) {
                            if(s.toString().toLowerCase().equals(posiblematch.oldName.toLowerCase())){
                                oldNumber.setText(String.valueOf(posiblematch.oldNumber));
                                newName.setText(posiblematch.newName);
                                newNumber.setText(String.valueOf(posiblematch.newNumber));
                                Exact = true;
                                break;
                            }
                        }
                        //Seperate each word
                        String[] arr = s.toString().toLowerCase().split(" ");

                        //Create a list of search results
                        List<SearchItem> results = new ArrayList<SearchItem>();

                        // Iteration Variable
                        SearchItem result = new SearchItem();

                        //Go through each song and search for each word
                        for (BookIndexItem SongItem:songs
                                ) {
                            result = new SearchItem();
                            result.name = SongItem.oldName;
                            for (String word: arr
                                    ) {
                                Boolean found = SongItem.oldName.contains(word.toLowerCase());
                                if(found){
                                    result.score++;
                                }
                            }
                            if(SongItem.oldName.toLowerCase().contains((s.toString().toLowerCase()))){
                                result.score +=10;
                            }
                            if(result.score > 0){
                                results.add(result);
                            }
                        }
                        Collections.sort(results, new SearchItemComp());
                        for (int i = 0; i < Suggestions.size(); i++) {
                            if (results.size() >
                                    i) {
                                Suggestions.get(i).setText(results.get(i).name);
                                Log.d("Score", String.valueOf(results.get(i).score));
                            } else Suggestions.get(i).setText("");
                        }
                        if(results.size() == 1 && Exact){

                            Suggestion0.setText("");
                            Suggestion1.setText("");
                            Suggestion2.setText("");
                            Suggestion3.setText("");
                            Suggestion4.setText("");
                        }
                    } else {
                        oldNumber.setText("Old Number");
                        newName.setText("New Name");
                        newNumber.setText("New Number");
                        Suggestion0.setText("");
                        Suggestion1.setText("");
                        Suggestion2.setText("");
                        Suggestion3.setText("");
                        Suggestion4.setText("");
                    }
                }
            }
        });

        newName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (newName.hasFocus()) {
                    //Suggestion0
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)Suggestion0.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, newName.getId());

                    Suggestion0.setLayoutParams(params);


                    if (s.length() > 0) {
                        Boolean Exact = false;
                        oldNumber.setText(String.valueOf("Unknown"));
                        oldName.setText("Unknown");
                        newNumber.setText("Unknown");
                        for (BookIndexItem posiblematch: songs
                                ) {
                            if(s.toString().toLowerCase().equals(posiblematch.newName.toLowerCase())){
                                oldNumber.setText(String.valueOf(posiblematch.oldNumber));
                                oldName.setText(posiblematch.oldName);
                                newNumber.setText(String.valueOf(posiblematch.newNumber));
                                Exact = true;
                                break;
                            }
                        }
                        //Seperate each word
                        String[] arr = s.toString().toLowerCase().split(" ");

                        //Create a list of search results
                        List<SearchItem> results = new ArrayList<SearchItem>();

                        // Iteration Variable
                        SearchItem result = new SearchItem();

                        //Go through each song and search for each word
                        for (BookIndexItem SongItem:songs
                                ) {
                            result = new SearchItem();
                            result.name = SongItem.newName;
                            for (String word: arr
                                    ) {
                                Boolean found = SongItem.newName.contains(word.toLowerCase());
                                if(found){
                                    result.score++;
                                }
                            }
                            if(SongItem.newName.toLowerCase().contains((s.toString().toLowerCase()))){
                                result.score +=10;
                            }
                            if(result.score > 0){
                                results.add(result);
                            }
                        }
                        Collections.sort(results, new SearchItemComp());
                        Log.d("Score", "new");
                        for (int i = 0; i < Suggestions.size(); i++) {
                            if (results.size() >
                                    i) {
                                Suggestions.get(i).setText(results.get(i).name);
                                Log.d("Score", String.valueOf(results.get(i).score));
                                //Old Name
                                params = (RelativeLayout.LayoutParams)oldName.getLayoutParams();
                                params.addRule(RelativeLayout.BELOW, Suggestions.get(i).getId());
                                oldName.setLayoutParams(params);
                            } else Suggestions.get(i).setText("");
                        }
                        if(results.size() == 1 && Exact){

                            Suggestion0.setText("");
                            Suggestion1.setText("");
                            Suggestion2.setText("");
                            Suggestion3.setText("");
                            Suggestion4.setText("");
                        }
                    } else {
                        oldNumber.setText("Old Number");
                        oldName.setText("Old Name");
                        newNumber.setText("New Number");
                        Suggestion0.setText("");
                        Suggestion1.setText("");
                        Suggestion2.setText("");
                        Suggestion3.setText("");
                        Suggestion4.setText("");
                        setForOldOrNumber();
                    }
                }
            }
        });
        //region BookIndex
        songs.add(SetItem("Come, Rejoice",1,"Come, Rejoice",9));
        songs.add(SetItem("Abide With Me; 'Tis Eventide",2,"Abide With Me; 'Tis Eventide",165));
        songs.add(SetItem("A Mighty Fortress",3,"A Mighty Fortress Is Our God", 68));
        songs.add(SetItem("All Creatures of Our God and King",4,"All Creatures of Our God and King",62));
        //songs.add(SetItem("As Swiftly My Days Go Out On the Wing",5,"",26));
        songs.add(SetItem("Beautiful Zion for Me",6,"Beautiful Zion, Built Above",44));
        songs.add(SetItem("Behold! A Royal Army",7,"Behold! A Royal Army",251));
        songs.add(SetItem("God, Our Father, Hear Us Pray",8,"God, Our Father, Hear Us Pray",170));
        songs.add(SetItem("In Hymns of Praise",9,"In Hymns of Praise",75));
        songs.add(SetItem("Christ the Lord is Risen Today",10,"Christ the Lord Is Risen Today",200));
        songs.add(SetItem("Come All Ye Saints and Sing His Praise",11,"Come, All Ye Saints of Zion",38));
        songs.add(SetItem("Come, All Ye Saints Who Dwell on Earth",12,"Come, All Ye Saints Who Dwell on Earth",65));
        songs.add(SetItem("Come, Come, Ye Saints",13,"Come, Come, Ye Saints",30));
        songs.add(SetItem("Come Follow Me",14,"Come, Follow Me",116));
       // songs.add(SetItem("Come, Go With Me, Beyond the Sea",15,"",));
        //songs.add(SetItem("Come Hail the Cause of Zion's Youth",16,"",));
        songs.add(SetItem("Come, Let Us Anew",17,"Come, Let Us Anew",217));
        songs.add(SetItem("Come, Ye Disconsolate",18,"Come, Ye Disconsolate",115));
        songs.add(SetItem("Come Along, Come Along",19,"Come Along, Come Along",244));
        songs.add(SetItem("Come, O Thou King of Kings",20,"Come, O Thou King of Kings",59));
      //  songs.add(SetItem("Think not, When You Gather to Zion",21,"",));
        songs.add(SetItem("Come Unto Jesus",22,"Come unto Jesus",117));
        songs.add(SetItem("Come, Ye Children of the Lord",23,"Come, Ye Children of the Lord",58));
        songs.add(SetItem("Behold Thy Sons and Daughters, Lord",24,"Behold Thy Sons and Daughters, Lord",238));
        songs.add(SetItem("Come, We That Love the Lord",25,"Come, We That Love the Lord",119));
        songs.add(SetItem("Dear to the Heart of the Shepherd",26,"Dear to the Heart of the Shepherd",221));
        songs.add(SetItem("Do What is Right",27,"Do What Is Right",237));
        songs.add(SetItem("The Lord Be with Us",28,"The Lord Be with Us",161));
        songs.add(SetItem("Come, Ye Thankful People",29,"Come, Ye Thankful People",94));
       // songs.add(SetItem("Earth, With Her Thousand Flowers",30,"",));
        songs.add(SetItem("Ere You Left Your Room This Morning",31,"Did You Think to Pray?",140));
        songs.add(SetItem("Come, Sing to the Lord",32,"Come, Sing to the Lord",10));
        songs.add(SetItem("Far, Far Away on Judea's Plains",33,"Far, Far Away on Judea's Plains",212));
        songs.add(SetItem("Father in Heaven",34,"Father in Heaven",133));
      //  songs.add(SetItem("Farewell, All Earthly Honors",35,"",));
        songs.add(SetItem("God of Power, God of Right",36,"God of Power, God of Right",20));
        songs.add(SetItem("Up, Awake, Ye Defenders of Zion",37,"Up, Awake, Ye Defenders of Zion",248));
      //  songs.add(SetItem("Each Cooing Dove",38,"",));
        songs.add(SetItem("The First Noel",39,"The First Noel",213));
      //  songs.add(SetItem("From Greenland's Icy Mountains",40,"",));
        songs.add(SetItem("Father in Heaven, We Do Believe",41,"Father in Heaven, We Do Believe",180));
        songs.add(SetItem("Firm as the Mountains Around Us",42,"Carry On",255));
        songs.add(SetItem("Father, Thy Children to Thee Now Raise",43,"Father, Thy Children to Thee Now Raise",91));
        songs.add(SetItem("Glory to God on High",44,"Glory to God on High",67));
        songs.add(SetItem("The Glorious Gospel Light Has Shone",45,"The Glorious Gospel Light Has Shone",283));
        songs.add(SetItem("Come, Listen to a Prophet's Voice",46,"Come, Listen to a Prophet's Voice",21));
        songs.add(SetItem("God be with You",47,"God Be with You Till We Meet Again",152));
        songs.add(SetItem("God Moves in a Mysterious Way",48,"God Moves in a Mysterious Way",285));
        songs.add(SetItem("In Humility, Our Savior",49,"In Humility, Our Savior",172));
        songs.add(SetItem("God of Our Fathers, We Come Unto Thee",50,"God of Our Fathers, We Come unto Thee",76));
        songs.add(SetItem("Abide With Me!",51,"Abide with Me!",166));
        songs.add(SetItem("From All That Dwell Below the Skies",52,"From All That Dwell below the Skies",90));
        songs.add(SetItem("Great King of Heaven, Our Hearts We Raise",53,"Great King of Heaven",63));
        songs.add(SetItem("God of Our Fathers, Whose Almighty Hand",54,"God of Our Fathers, Whose Almighty Hand",78));
       // songs.add(SetItem("Down by the River's Verdant Side",55,"",));
        songs.add(SetItem("Guide Us, O Thou Great Jehovah",56,"Guide Us, O Thou Great Jehovah",83));
        songs.add(SetItem("Guide Us, O Thou Great Jehovah",57,"Guide Us, O Thou Great Jehovah", 83));
        songs.add(SetItem("Have I done Any Good?",58,"Have I Done Any Good?",223));
        songs.add(SetItem("Great God, to Thee My Evening Song",59,"Great God, to Thee My Evening Song",164));
        songs.add(SetItem("Hark! The Herald Angels Sing",60,"Hark! The Herald Angels Sing",209));
        songs.add(SetItem("He Is Risen",61,"He Is Risen!",199));
        songs.add(SetItem("High on the Mountain Top",62,"High on the Mountain Top",5));
        songs.add(SetItem("Holy Temples on Mount Zion",63,"Holy Temples on Mount Zion",289));
        songs.add(SetItem("Hope of Israel",64,"Hope of Israel",259));
        songs.add(SetItem("How Beautiful Thy Temples, Lord",65,"How Beautiful Thy Temples, Lord",288));
        songs.add(SetItem("How Firm a Foundation",66,"How Firm a Foundation",85));
        songs.add(SetItem("How Gentle God's Commands",67,"How Gentle God's Commands",125));
        songs.add(SetItem("How Great the Wisdom and the Love",68,"How Great the Wisdom and the Love",195));
        songs.add(SetItem("How Long, O Lord, Most Holy and True",69,"How Long, O Lord Most Holy and True",126));
       // songs.add(SetItem("Come, Thou Fount of Every Blessing",70,"",));
        songs.add(SetItem("I Have Work Enough to Do",71,"I Have Work Enough to Do",224));
       // songs.add(SetItem("There Is a Land Whose Sunny Vales",72,"",));
        songs.add(SetItem("Improve the Shining Moments",73,"Improve the Shining Moments",226));
      //  songs.add(SetItem("In a World Where Sorrow",74,"",));
        songs.add(SetItem("It May not Be on the Mountain Height",75,"I'll Go Where You Want Me to Go",270));
        songs.add(SetItem("God of Our Fathers, Known of Old",76,"God of Our Fathers, Known of Old",80));
        songs.add(SetItem("God of Our Fathers, Known of Old",77,"God of Our Fathers, Known of Old",80));
        songs.add(SetItem("Beautiful Zion, Built Above",78,"Beautiful Zion, Built Above",44));
        songs.add(SetItem("I Need Thee Every Hour",79,"I Need Thee Every Hour",98));
        songs.add(SetItem("I Stand All Amazed",80,"I Stand All Amazed",193));
        songs.add(SetItem("Israel, Israel, God Is Calling",81,"Israel, Israel, God Is Calling",7));
        songs.add(SetItem("It Came Upon the Midnight Clear",82,"It Came upon the Midnight Clear", 207));
        songs.add(SetItem("Jehovah, Lord of Heaven and Earth",83,"Jehovah, Lord of Heaven and Earth",269));
        songs.add(SetItem("Jesus, Lover of My Soul",84,"Jesus, Lover of My Soul",102));
       // songs.add(SetItem("Jesus, My Savior True",85,"",));
        songs.add(SetItem("Jesus of Nazareth, Savior and King",86,"Jesus of Nazareth, Savior and King",181));
        songs.add(SetItem("Oh What Songs of the Heart",87,"Oh, What Songs of the Heart",286));
        songs.add(SetItem("Jesus, Once of Humble Birth",88,"Jesus, Once of Humble Birth",196));
        songs.add(SetItem("Joy to the World",89,"Joy to the World",201));
        songs.add(SetItem("Know This, That Every Soul is Free",90,"Know This, That Every Soul Is Free",240));
        //songs.add(SetItem("Let Each Man Learn to Know Himself",91,"",));
        songs.add(SetItem("Gently, Raise the Sacred Strain",92,"Gently Raise the Sacred Strain",146));
        songs.add(SetItem("Let Earth's Inhabitants Rejoice",93,"Let Earth's Inhabitants Rejoice",53));
        songs.add(SetItem("Let Us Oft Speak Kind Words",94,"Let Us Oft Speak Kind Words",232));
        songs.add(SetItem("I Know That My Redeemer Lives",95,"I Know That My Redeemer Lives",136));
        songs.add(SetItem("Hear Thou Our Hymn, O Lord",96,"Hear Thou Our Hymn, O Lord",222));
      //  songs.add(SetItem("Lo! On the Water's Bring We Stand",97,"",));
        songs.add(SetItem("Let Us All Press On",98,"Let Us All Press On",243));
        songs.add(SetItem("In Memory of the Crucified",99,"In Memory of the Crucified",190));
        songs.add(SetItem("Lord, Accept Into Thy Kingdom",100,"Lord, Accept into Thy Kingdom",236));
        songs.add(SetItem("Lord, Accept Our True Devotion",101,"Lord, Accept Our True Devotion",107));
       // songs.add(SetItem("Thought in the Outward Church Below",102,"",));
        songs.add(SetItem("The Lord Is My Light",103,"The Lord Is My Light",89));
        songs.add(SetItem("The Lord Is My Shepherd",104,"The Lord Is My Shepherd",108));
        songs.add(SetItem("Lord, Dismiss Us With Thy Blessing",105,"Lord, Dismiss Us with Thy Blessing",163));
        songs.add(SetItem("Master, The Tempest Is Raging",106,"Master, the Tempest Is Raging",105));
        //songs.add(SetItem("For Our Devotions, Father",107,"",));
        songs.add(SetItem("Jesus, Mighty King In Zion",108,"Jesus, Mighty King in Zion",234));
        songs.add(SetItem("Precious Savior, Dear Redeemer",109,"Precious Savior, Dear Redeemer",103));
        songs.add(SetItem("Choose the Right",110,"Choose the Right",239));
        //songs.add(SetItem("M.I.A., We Hail Thee",111,"",));
        songs.add(SetItem("Lead, Kindly Light",112,"Lead, Kindly Light",97));
        songs.add(SetItem("The Lord My Pasture Will Prepare",113,"The Lord My Pasture Will Prepare",109));
        songs.add(SetItem("More Holiness Give Me",114,"More Holiness Give Me",131));
        songs.add(SetItem("My Country, 'Tis of Thee",115,"My Country, 'Tis of Thee",339));
        songs.add(SetItem("Nay, Speak No Ill",116,"Nay, Speak No Ill",233));
        songs.add(SetItem("Nearer, Dear Savior, to Thee",117,"Nearer, Dear Savior, to Thee",99));
        songs.add(SetItem("Now Let Us Rejoice",118,"Now Let Us Rejoice",3));
        songs.add(SetItem("Lord, We Ask Thee Ere We Part",119,"Lord, We Ask Thee Ere We Part",153));
        songs.add(SetItem("Now Thank We All Our God",120,"Now Thank We All Our God",95));
        songs.add(SetItem("Jesus, Savior, Pilot Me",121,"Jesus, Savior, Pilot Me",104));
        songs.add(SetItem("Now the Day Is Over",122,"Now the Day Is Over",159));
        songs.add(SetItem("O God, Our Help In Ages Past",123,"O God, Our Help in Ages Past",31));
        songs.add(SetItem("Nearer, My God to Thee",124,"Nearer, My God, to Thee",100));
        songs.add(SetItem("O God, The Eternal Father",125,"O God, the Eternal Father",175));
        songs.add(SetItem("Oh Beautiful for Spacious Skies",126,"America the Beautiful",338));
        //songs.add(SetItem("O'er the Gloomy Hills of Darkness",127,"",));
        songs.add(SetItem("Onward, Christan Soldiers",128,"Onward, Christian Soldiers",246));
        songs.add(SetItem("Oh Come, All Ye Faithful",129,"Oh, Come, All Ye Faithful",202));
        songs.add(SetItem("O Thou Rock of Our Salvation",130,"O Thou Rock of Our Salvation",258));
        //songs.add(SetItem("Oh Say, Can You See",131,"",));
        songs.add(SetItem("Now We'll Sing with One Accord",132,"Now We'll Sing with One Accord",25));
        //songs.add(SetItem("O Happy Home! O Blest Abode",133,"",));
        //songs.add(SetItem("O Hark! A Glorious Sound Is Heard",134,"",));
        songs.add(SetItem("O Holy Words of Truth and Love",135,"Oh, Holy Words of Truth and Love",271));
        songs.add(SetItem("Oh, How Lovely Was the Morning",136,"Joseph Smith's First Prayer",26));
        //songs.add(SetItem("Oh Give Me Back My Prophet Dear",137,"",));
        songs.add(SetItem("O My Father",138,"O My Father",292));
        songs.add(SetItem("O My Father",139,"O My Father",292));
        songs.add(SetItem("Land of the Mountains High",140,"O Ye Mountains High",34));
        songs.add(SetItem("Lead Me Into Life Eternal",141,"Lead Me into Life Eternal",45));
        songs.add(SetItem("Lord, We Come Thee Now",142,"Lord, We Come before Thee Now",162));
        songs.add(SetItem("Oh Say, What Is Truth?",143,"Oh Say, What Is Truth?",272));
        songs.add(SetItem("Our Mountains Home So Dear",144,"Our Mountain Home So Dear",33));
        songs.add(SetItem("O Ye Mountains High",145,"O Ye Mountains High",34));
        songs.add(SetItem("How Wondrous and Great",146,"How Wondrous and Great",267));
        songs.add(SetItem("Praise to the Man",147,"Praise to the Man",27));
        songs.add(SetItem("Jesus, the Very Thought of Thee",148,"Jesus, the Very Thought of Thee",141));
        songs.add(SetItem("Praise the Lord with Hear and Voice",149,"Praise the Lord with Heart and Voice",73));
        songs.add(SetItem("Praise to the Lord",150,"Praise to the Lord, the Almighty",72));
        songs.add(SetItem("Rejoice, the Lord Is King",151,"Rejoice, the Lord Is King!",66));
      //  songs.add(SetItem("O Sons of Zion",152,"",));
        songs.add(SetItem("A Poor Wayfaring Man of Grief",153,"A Poor Wayfaring Man of Grief",29));
        songs.add(SetItem("Raise Your Voices to the Lord",154,"Raise Your Voices to the Lord",61));
        songs.add(SetItem("Savior, Redeemer of My Soul",155,"Savior, Redeemer of My Soul",112));
        //songs.add(SetItem("Shall We Meet",156,"",));
        songs.add(SetItem("Shall the Youth of Zion Falter?",157,"True to the Faith",254));
        songs.add(SetItem("Sing Praise to Him",158,"Sing Praise to Him",70));
        songs.add(SetItem("Should You Feel Inclined to Censure",159,"Should You Feel Inclined to Censure",235));
        songs.add(SetItem("Silent Night",160,"Silent Night",204));
        songs.add(SetItem("Sing We Now at Parting",161,"Sing We Now at Parting",156));
        songs.add(SetItem("Softly Now the Light of Day",162,"Softly Now the Light of Day",160));
        songs.add(SetItem("Sons of Michael, He Approaches",163,"Sons of Michael, He Approaches",51));
        //songs.add(SetItem("Stars of Morning, Shout for Joy",164,"",));
        songs.add(SetItem("O Little Town of Bethlehem",165,"O Little Town of Bethlehem",208));
        songs.add(SetItem("Sweet Hour of Prayer",166,"Sweet Hour of Prayer",142));
       // songs.add(SetItem("Take Courage, Saints, and Faint Not by the Way",167,"",));
        songs.add(SetItem("Sweet Is the Work, My God, My King",168,"Sweet Is the Work",147));
        songs.add(SetItem("There Is Beauty All Around",169,"Love at Home",194));
        songs.add(SetItem("Dearest Children, God is Near You",170,"Dearest Children, God Is Near You",96));
       // songs.add(SetItem("Now to Heaven Our Prayer",171,"",));
        songs.add(SetItem("There Is and Hour of Peace and Rest",172,"Secret Prayer",144));
        songs.add(SetItem("They the Builders of the Nation",173,"They, the Builders of the Nation",36));
        songs.add(SetItem("There's Sunshine in My Soul Today",174,"There Is Sunshine in My Soul Today",277));
        songs.add(SetItem("Who's on The Lord's Side?",175,"Who's on the Lord's Side?",260));
        songs.add(SetItem("This House We Dedicate to Thee",176,"This House We Dedicate to Thee",245));
        songs.add(SetItem("Thanks for the Sabbath School",177,"Thanks for the Sabbath School",278));
        songs.add(SetItem("God Loved Us, So He Sent His Son",178,"God Loved Us, So He Sent His Son",187));
        songs.add(SetItem("The Day Dawn Is Breaking",179,"The Day Dawn Is Breaking",52));
        songs.add(SetItem("We Give Thee But Thine Own",180,"We Give Thee But Thine Own",218));
      //  songs.add(SetItem("Thou Dost Not Weep Alone",181,"",));
        songs.add(SetItem("Hail to the Brightness of Zion's Glad Morning",182,"Hail to the Brightness of Zion's Glad Morning!",42));
        //songs.add(SetItem("Awake! O Ye People, the Savior is Coming",183,"",));
        songs.add(SetItem("The Time is Far Spent",184,"The Time Is Far Spent",266));
        //songs.add(SetItem("Mid Pleasures and Palaces",185,"",));
        songs.add(SetItem("To Nephi, Seer of Olden Time",186,"The Iron Rod",274));
        songs.add(SetItem("'Tis Sweet to Sing the Matchless Love",187,"'Tis Sweet To Sing the Matchless Love",177));
        songs.add(SetItem("Truth Reflects Upon Our Senses",188,"Truth Reflects upon Our Senses",273));
        songs.add(SetItem("Truth Eternal",189,"Truth Eternal",4));
        songs.add(SetItem("Welcome, Welcome Sabbath Morning",190,"Welcome, Welcome, Sabbath Morning",280));
        songs.add(SetItem("Sweet is the Peace the Gospel Brings",191,"Sweet Is the Peace the Gospel Brings",14));
        songs.add(SetItem("We Are Sowing",192,"We Are Sowing",216));
        songs.add(SetItem("We Meet Again in Sabbath School",193,"We Meet Again in Sabbath School",282));
        songs.add(SetItem("We're Marching On To Glory",194,"We Are Marching On to Glory",225));
        songs.add(SetItem("Redeemer of Israel",195,"Redeemer of Israel",6));
        songs.add(SetItem("We Thank Thee, O God, for a Prophet",196,"We Thank Thee, O God, for a Prophet",19));
        songs.add(SetItem("What Glorious Scenes Mine Eyes Behold",197,"What Glorious Scenes Mine Eyes Behold",16));
      //  songs.add(SetItem("When First the Glorious Light of Truth",198,"",));
        songs.add(SetItem("When in the Wondrous Realms Above",199,"Thy Will, O Lord, Be Done",188));
        songs.add(SetItem("When the Rosy Light of Morning",200,"Come Away to the Sunday School",276));
        songs.add(SetItem("There is a Green Hill Far Away",201,"There Is a Green Hill Far Away",194));
        songs.add(SetItem("When Upon Life's Billows",202,"Count Your Blessings",241));
        songs.add(SetItem("We Love Thy House, O God",203,"We Love Thy House, O God",247));
        songs.add(SetItem("The Spirit, Lord, Has Stirred Our Souls",204,"Thy Spirit, Lord, Has Stirred Our Souls",157));
        //songs.add(SetItem("We'll Sing the Songs of Zion",205,"",));
        songs.add(SetItem("The World Has Need of Willing Men",206,"Put Your Shoulder to the Wheel",252));
        songs.add(SetItem("Rejoice, Ye Saints of Latter-days",207,"Rejoice, Ye Saints of Latter Days",290));
        songs.add(SetItem("You Can Make the Pathway Bright",208,"You Can Make the Pathway Bright",228));
        songs.add(SetItem("With Wondering Awe",209,"With Wondering Awe",210));
        songs.add(SetItem("We Are All Enlisted",210,"We Are All Enlisted",250));
        //songs.add(SetItem("Ye Chosen Twelve, To You are Given",211,"",));
        songs.add(SetItem("Zion Stands With Hills Surrounded",212,"Zion Stands with Hills Surrounded",43));
        songs.add(SetItem("The Spirit of God Like a Fire",213,"The Spirit of God",2));
        songs.add(SetItem("Praise God From Whom All Blessing Flow",214,"Praise God, from Whom All Blessings Flow",242));
        songs.add(SetItem("Today, While the Sun Shines",215,"Today, While the Sun Shines",229));
        songs.add(SetItem("With All the Power of Heart and Tongue",216,"With All the Power of Heart and Tongue",79));
        songs.add(SetItem("While of These Emblems We Partake",217,"While of These Emblems We Partake",174));
        songs.add(SetItem("We'll Sing All Hail to Jesus' Name",218,"We'll Sing All Hail to Jesus' Name",182));
        songs.add(SetItem("I Heard the Bells on Christmas Day",219,"I Heard the Bells on Christmas Day",214));
        songs.add(SetItem("Prayer Is the Soul's Sincere Desire",220,"Prayer Is the Soul's Sincere Desire",145));
        songs.add(SetItem("Upon the Cross of Calvary",221,"Upon the Cross of Calvary",184));
        songs.add(SetItem("While Shepherds Watched Their Flocks by Night",222,"While Shepherds Watched Their Flocks",211));
        //songs.add(SetItem("All Hail the Glorious Day",223,"",));
        songs.add(SetItem("An Angel From on High",224,"An Angel from on High",13));
        songs.add(SetItem("Arise, O Glorious Zion",225,"Arise, O Glorious Zion",40));
        songs.add(SetItem("While of These Emblems We Partake",226,"While of These Emblems We Partake",174));
       // songs.add(SetItem("Arise, My Shoulder, Arise",227,"",));
      //  songs.add(SetItem("Author of Faith, Eternal Word",228,"",));
        songs.add(SetItem("Awake, Ye Saints of God Awake",229,"Awake, Ye Saints of God, Awake!",17));
        songs.add(SetItem("Behold the Great Redeemer Die",230,"Behold the Great Redeemer Die",191));
        songs.add(SetItem("Before Thee, Lord, I Bow My Head",231,"Before Thee, Lord, I Bow My Head",158));
        songs.add(SetItem("As the Dew From Heaven Distilling",232,"As the Dew from Heaven Distilling",149));
        //songs.add(SetItem("Blessed Are They That Have the Faith",233,"",));
        songs.add(SetItem("Great Is the Lord; 'Tis Good to Praise",234,"Great Is the Lord",77));
        songs.add(SetItem("Cast Thy Burden Upon the Lord",235,"Cast Thy Burden upon the Lord",110));
       // songs.add(SetItem("Captain of Israel's Host",236,"",));
      //  songs.add(SetItem("Come, Dearest Lord",237,"",));
        songs.add(SetItem("Come, Let Us Sing and Evening Hymn",238,"Come, Let Us Sing an Evening Hymn",167));
        //songs.add(SetItem("Break Forth, O Beauteous Heavenly Light",239,"",));
        songs.add(SetItem("Come, Thou Glorious Day of Promise",240,"Come, Thou Glorious Day of Promise",50));
        songs.add(SetItem("For the Strength of the Hills",241,"For the Strength of the Hills",35));
        songs.add(SetItem("Again We Meet Around the Board",242,"Again We Meet around the Board",186));
        songs.add(SetItem("Glorious Things Are Sung of Zion",243,"Glorious Things Are Sung of Zion",48));
        //songs.add(SetItem("Glorious Things of Thee Are Spoken",244,"",));
        songs.add(SetItem("Does the Journey Seem Long",245,"Does the Journey Seem Long?",127));
        songs.add(SetItem("God Is In His Holy Temple",246,"God Is in His Holy Temple",132));
        songs.add(SetItem("Go, Ye Messengers of Glory",247,"Go, Ye Messengers of Glory",262));
        songs.add(SetItem("Great God, Attend While Zion Sings",248,"Great God, Attend While Zion Sings",88));
       // songs.add(SetItem("Hark, Ten Thousand Thousand Voices",249,"",));
        //songs.add(SetItem("The Happy Day Has Rolled On",250,"",));
        songs.add(SetItem("Again, Our Dear Redeeming Lord",251,"Again, Our Dear Redeeming Lord",179));
       // songs.add(SetItem("Hushed Was the Evening Hymn",252,"",));
        //songs.add(SetItem("Hark! Listen to the Trumpeters",253,"",));
       // songs.add(SetItem("I'll Praise My Maker While I've Breath",254,"",));
        songs.add(SetItem("I Saw a Mighty Angel Fly",255,"I Saw a Mighty Angel Fly",15));
        //songs.add(SetItem("Give Us Room That We May Dwell",256,"",));
        songs.add(SetItem("If You Could Hie to Kolob",257,"If You Could Hie to Kolob",284));
        songs.add(SetItem("In Remembrance Of Thy Suffering",258,"In Remembrance of Thy Suffering",183));
        songs.add(SetItem("Jesus, Lover of My Soul",259,"Jesus, Lover of My Soul",102));
        songs.add(SetItem("Lean on My Ample Arm",260,"Lean on My Ample Arm",120));
        songs.add(SetItem("I'm a Pilgrim; I'm a Stranger",261,"I'm a Pilgrim, I'm a Stranger",121));
        songs.add(SetItem("Let Zion in Her Beauty Rise",262,"Let Zion in Her Beauty Rise",41));
        songs.add(SetItem("He died! The Great Redeemer Died",263,"He Died! The Great Redeemer Died",192));
        songs.add(SetItem("Lo! The Mighty God Appearing",264,"Lo, the Mighty God Appearing!",55));
        //songs.add(SetItem("Lord, Thou Wilt Hear Me",265,"",));
        songs.add(SetItem("We're Not Ashamed to Own Our Lord",266,"We're Not Ashamed to Own Our Lord",57));
        //songs.add(SetItem("Not Now, But in the Coming Years",267,"",));
        //songs.add(SetItem("O Awake! My Slumbering Minstrel",268,"",));
        songs.add(SetItem("The Morning Breaks; the Shadows Flee",269,"The Morning Breaks",1));
        songs.add(SetItem("O My Father",270,"O My Father",292));
        songs.add(SetItem("O Lord of Hosts",271,"O Lord of Hosts",178));
       // songs.add(SetItem("One Sweetly Solemn Thought",272,"",));
        //songs.add(SetItem("On The Mountain's Top Appearing",273,"",));
        songs.add(SetItem("O Thou, Before the World Began",274,"O Thou, Before the World Began",189));
        //songs.add(SetItem("What Voice Salutes the Startled Ear?",275,"",));
        songs.add(SetItem("O Thou Kind and Gracious Father",276,"O Thou Kind and Gracious Father",150));
        songs.add(SetItem("Praise Ye the Lord",277,"Praise Ye the Lord",74));
        //songs.add(SetItem("Rest, Rest for the Weary Soul",278,"",));
        //songs.add(SetItem("Ring Out, Wild Bells",279,"",));
        songs.add(SetItem("Reverently and Meekly Now",280,"Reverently and Meekly Now",185));
        songs.add(SetItem("Sacred the Place of Prayer and Song",281,"Our Chapel Is a Sacred Place",30));
        songs.add(SetItem("Savior, Redeemer of My Soul",282,"Savior, Redeemer of My Soul",112));
       // songs.add(SetItem("Up! Arouse Thee, O Beautiful Zion",283,"",));
        songs.add(SetItem("Softly Beams the Sacred Dawning",284,"Softly Beams the Sacred Dawning",56));
        songs.add(SetItem("Though Deepening Trials",285,"Though Deepening Trials",122));
        //songs.add(SetItem("Unanswered Yet? The Prayer",286,"",));
        //songs.add(SetItem("Lord of All Being, Throned Afar",287,"",));
        //songs.add(SetItem("Ye Children of Our God",288,"",));
        songs.add(SetItem("The Voice of God Again Is Heard",289,"The Voice of God Again Is Heard",18));
        songs.add(SetItem("Ye Simple Souls Who Stray",290,"Ye Simple Souls Who Stray",118));
        songs.add(SetItem("A Voice Hath Spoken From the Dust",291,"Men Are That They Might Have Joy",275));
        songs.add(SetItem("The Wintry Day, Descending to Its Close",292,"The Wintry Day, Descending to Its Close",37));
        //songs.add(SetItem("When Dark and Drear the Skies Appear",293,"",));
        //songs.add(SetItem("I Wander Through the Stilly Night",294,"",));
        //songs.add(SetItem("When Christ Was Born in Bethlehem",295,"",));
        //songs.add(SetItem("The Seer, Joseph, The Seer",296,"",));
        songs.add(SetItem("Behold, the Mountain of the Lord",297,"Behold, the Mountain of the Lord",54));
        //songs.add(SetItem("The Lord Imparted from Above",298,"",));
        songs.add(SetItem("What Was Witnessed in the Heavens",299,"What Was Witnessed in the Heavens?",11));
        songs.add(SetItem("An Angel From on High",300,"An Angel from on High",13));
        songs.add(SetItem("Brightly Beams Our Father's Mercy",301,"Brightly Beams Our Father's Mercy ",335));
        songs.add(SetItem("Come, All Ye Sons of God",302,"Come, All Ye Sons of God",322));
        //songs.add(SetItem("Come, All Ye Sons of Zion",303,"",));
        songs.add(SetItem("Come, O Thou King of Kings",304,"Come, O Thou King of Kings",59));
        songs.add(SetItem("Come, Come Ye Saints",305,"Come, Come, Ye Saints",30));
        songs.add(SetItem("Come, Let Us Anew",306,"Come, Let Us Anew",217));
        songs.add(SetItem("Sweet Is the Work, My God, My King",307,"Sweet Is the Work",147));
        songs.add(SetItem("Come, Ye Children of the Lord",308,"Come, Ye Children of the Lord",58));
        songs.add(SetItem("Jesus, my Savior True",309,"Guide Me to Thee",101));
        songs.add(SetItem("For the strength of the Hills",310,"For the Strength of the Hills",35));
        songs.add(SetItem("Jesus, Lover of My Soul",311,"Jesus, Lover of My Soul",102));
        songs.add(SetItem("High on the Mountain Top",312,"High on the Mountain Top",5));
        songs.add(SetItem("How Firm a Foundation",313,"How Firm a Foundation",85));
        songs.add(SetItem("Do What Is Right",314,"Do What Is Right",237));
        songs.add(SetItem("Jehovah, Lord of Heaven and Earth",315,"Jehovah, Lord of Heaven and Earth",269));
        songs.add(SetItem("How Great the Wisdom and the Love",316,"How Great the Wisdom and the Love",195));
        songs.add(SetItem("I Know That My redeemer Lives",317,"I Know That My Redeemer Lives",136));
        songs.add(SetItem("It May Not Be on the Mountain Height",318,"I'll Go Where You Want Me to Go",270));
        songs.add(SetItem("Glory to God on High",319,"Glory to God on High",67));
        songs.add(SetItem("I Need thee Every Hour",320,"I Need Thee Every Hour",98));
        songs.add(SetItem("The Lord is My Shepherd",321,"The Lord Is My Shepherd",108));
        songs.add(SetItem("Lord Dismiss Us With Thy Blessing",322,"Lord, Dismiss Us with Thy Blessing",163));
        songs.add(SetItem("Now Let Us Rejoice",323,"Now Let Us Rejoice",3));
        songs.add(SetItem("Prayer Is the Soul's Sincere Desire",324,"Prayer Is the Soul's Sincere Desire",145));
        songs.add(SetItem("O Ye Mountains High",325,"O Ye Mountains High",34));
        songs.add(SetItem("Praise to the Man",326,"Praise to the Man",27));
        songs.add(SetItem("The Spirit of God Like a Fire Is Burning",327,"The Spirit of God",2));
        songs.add(SetItem("Sweet Hour of Prayer",328,"Sweet Hour of Prayer",142));
        songs.add(SetItem("Jesus, Once of Humble Birth",329,"Jesus, Once of Humble Birth",196));
        songs.add(SetItem("We Thank Thee, O God, For a Prophet",330,"We Thank Thee, O God, for a Prophet",19));
        songs.add(SetItem("God Moves in Mysterious Way",331,"God Moves in a Mysterious Way",185));
        songs.add(SetItem("Rise Up, O Men of God",332,"Rise Up, O Men of God ",324));
        songs.add(SetItem("Redeemer of Israel",333,"Redeemer of Israel",6));
        //songs.add(SetItem("Not Now, But in the Coming Years",334,"",));
        songs.add(SetItem("O Home Beloved, Where'er I Wander",335,"O Home Beloved ",337));
        songs.add(SetItem("O My Father",336,"O My Father",292));
        //songs.add(SetItem("O Happy Homes Among the Hills",337,"",));
        //songs.add(SetItem("Come, Lay His Books and Papers By",338,"",));
        songs.add(SetItem("Oh Say, What is Truth?",339,"Oh Say, What Is Truth?",272));
        songs.add(SetItem("School Thy Feelings",340,"School Thy Feelings ",336));
        songs.add(SetItem("Nearer, My God, to Thee",341,"Nearer, My God, to Thee",100));
        songs.add(SetItem("See, The Mighty Angel Flying!",342,"See, the Mighty Angel Flying",330));
        songs.add(SetItem("Reverently and Meekly Now",343,"Reverently and Meekly Now",185));
        songs.add(SetItem("Ye Elders of Israel",344,"Ye Elders of Israel ",319));
        songs.add(SetItem("Ye Who Are Called to Labor",345,"Ye Who Are Called to Labor",321));
        songs.add(SetItem("Zion Stands With hills Surrounded",346,"Zion Stands with Hills Surrounded",43));
        songs.add(SetItem("Abide With Me",347,"Abide with Me!",166));
        songs.add(SetItem("As the Dew From Heaven Distilling",348,"As the Dew from Heaven Distilling",149));
        //songs.add(SetItem("Bring Heavy Heart, Your Grief to Me",349,"",));
        //songs.add(SetItem("Captain of Israel's Host",350,"",));
        songs.add(SetItem("Cast Thy Burden Upon the Lord",351,"Cast Thy Burden upon the Lord",110));
        songs.add(SetItem("Come, Let Us Sing an Evening Hymn",352,"Come, Let Us Sing an Evening Hymn",167));
        songs.add(SetItem("Come, O Thou King of Kings",353,"Come, O Thou King of Kings",59));
       // songs.add(SetItem("Earth With Her Ten Thousand Flowers",354,"",));
        songs.add(SetItem("How Gentle God's Commands",355,"How Gentle God's Commands",125));
        songs.add(SetItem("Far, Far Away on Judea's Plains",356,"Far, Far Away on Judea's Plains",212));
        songs.add(SetItem("Gently Raise the Sacred Strain",357,"Gently Raise the Sacred Strain",146));
        songs.add(SetItem("How Great the Wisdom and the Love",358,"How Great the Wisdom and the Love",195));
        //songs.add(SetItem("Glory Be to God in the Highest",359,"",));
        songs.add(SetItem("God, Our Father, Hear us Pray",360,"God, Our Father, Hear Us Pray",170));
        songs.add(SetItem("I Know That My Redeemer Lives",361,"I Know That My Redeemer Lives",136));
        songs.add(SetItem("Jesus, Once of Humble Birth",362,"Jesus, Once of Humble Birth",196));
        songs.add(SetItem("Jehovah, Lord of Heaven and Earth",363,"Jehovah, Lord of Heaven and Earth",269));
        songs.add(SetItem("How Wondrous and Great",364,"How Wondrous and Great",267));
        songs.add(SetItem("Jesus Saviour, Pilot Me",365,"Jesus, Savior, Pilot Me",104));
        songs.add(SetItem("Lead, Kindly Light",366,"Lead, Kindly Light",97));
        songs.add(SetItem("Jesus, The Very Thought of Thee",367,"Jesus, the Very Thought of Thee",141));
        songs.add(SetItem("Let Us Oft Speak Kind Words",368,"Let Us Oft Speak Kind Words",232));
        songs.add(SetItem("Lord, We Ask Thee Ere We Part",369,"Lord, We Ask Thee Ere We Part",153));
        //songs.add(SetItem("Lift Thine Eyes to the Mountains",370,"",));
        songs.add(SetItem("The Lord is My Shepherd",371,"The Lord Is My Shepherd",108));
        //songs.add(SetItem("'Mid Pleasures and Palaces",372,"",));
        songs.add(SetItem("The Morning Breaks; The Shadows Flee",373,"The Morning Breaks",1));
        songs.add(SetItem("Nay, Speak No Ill",374,"Nay, Speak No Ill",233));
        //songs.add(SetItem("Not Now, But in the Coming Years",375,"",));
        songs.add(SetItem("More Holiness Give Me",376,"More Holiness Give Me",131));
        songs.add(SetItem("Now the Day Is Over",377,"Now the Day Is Over",159));
        songs.add(SetItem("Oh Beautiful For Spacious Skies",378,"America the Beautiful",338));
        songs.add(SetItem("O Lord of Hosts",379,"O Lord of Hosts",178));
        songs.add(SetItem("Prayer is the Soul's Sincere Desire",380,"Prayer Is the Soul's Sincere Desire",145));
        //songs.add(SetItem("Sister, Thou Wast Mild and Lovely",381,"",));
        songs.add(SetItem("Rock of Ages",382,"Rock of Ages",111));
        songs.add(SetItem("There Is Beauty All Around",383,"Love at Home",294));
        songs.add(SetItem("Sweet Is the Work, My God, My King",384,"Sweet Is the Work",147));
        songs.add(SetItem("The Wintry Day Descending to Its Close",385,"The Wintry Day, Descending to Its Close",37));
        songs.add(SetItem("We Ever Pray For Thee",386,"We Ever Pray for Thee",23));
        songs.add(SetItem("Ye Simple Souls Who Stray",387,"Ye Simple Souls Who Stray",118));
        songs.add(SetItem("Who's On the Lord's Side",388,"Who's on the Lord's Side?",260));
        songs.add(SetItem("This Earth Was Once A Garden Place",389,"Adam-ondi-Ahman",49));
//endregion

        Suggestion0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Suggestion0.getText().equals("")) {
                    if (newName.hasFocus()) {
                        newName.setText(Suggestion0.getText());
                    } else if (oldName.hasFocus()) {
                        oldName.setText(Suggestion0.getText());
                    }
                }
            }
        });
        Suggestion1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Suggestion1.getText().equals("")) {
                    if (newName.hasFocus()) {
                        newName.setText(Suggestion1.getText());
                    } else if (oldName.hasFocus()) {
                        oldName.setText(Suggestion1.getText());
                    }
                }
            }
        });
        Suggestion2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Suggestion2.getText().equals("")) {
                    if (newName.hasFocus()) {
                        newName.setText(Suggestion2.getText());
                    } else if (oldName.hasFocus()) {
                        oldName.setText(Suggestion2.getText());
                    }
                }
            }
        });
        Suggestion3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Suggestion3.getText().equals("")) {
                    if (newName.hasFocus()) {
                        newName.setText(Suggestion3.getText());
                    } else if (oldName.hasFocus()) {
                        oldName.setText(Suggestion3.getText());
                    }
                }
            }
        });
        Suggestion4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Suggestion4.getText().equals("")) {
                    if (newName.hasFocus()) {
                        newName.setText(Suggestion4.getText());
                    } else if (oldName.hasFocus()) {
                        oldName.setText(Suggestion4.getText());
                    }
                }
            }
        });
    }
    void setForOldOrNumber(){
        //Empty All Suggested Songs
        Suggestion0.setText("");
        Suggestion1.setText("");
        Suggestion2.setText("");
        Suggestion3.setText("");
        Suggestion4.setText("");

        //Change The Layout

        //Old Name
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)oldName.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, newName.getId());
        oldName.setLayoutParams(params);

        //Suggestion0
        params = (RelativeLayout.LayoutParams)Suggestion0.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, oldName.getId());
        Suggestion0.setLayoutParams(params);
    }

    void setForNew(){
        //Empty All Suggested Songs
        Suggestion0.setText("");
        Suggestion1.setText("");
        Suggestion2.setText("");
        Suggestion3.setText("");
        Suggestion4.setText("");

        //Change The Layout

        //Old Name
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)oldName.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, Suggestion4.getId());
        oldName.setLayoutParams(params);

        //Suggestion0
        params = (RelativeLayout.LayoutParams)Suggestion0.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, newName.getId());
        Suggestion0.setLayoutParams(params);
    }

    public BookIndexItem SetItem(String OldName, int OldNumber, String NewName, int NewNumber){
        BookIndexItem thing = new BookIndexItem();
        thing.oldName = OldName;
        thing.oldNumber = OldNumber;
        thing.newName = NewName;
        thing.newNumber = NewNumber;
        return thing;
    }
    //Used to find the song item with the given number
    public BookIndexItem OldNumberSearch(int OldNumber) {
        for (BookIndexItem I : songs
                ) {
            if(I.oldNumber == OldNumber){
                return I;
            }
        }
        return new BookIndexItem();
    }
    public BookIndexItem NewNumberSearch(int NewNumber) {
        for (BookIndexItem I : songs
                ) {
            if(I.newNumber == NewNumber){
                return I;
            }
        }
        return new BookIndexItem();
    }
}
