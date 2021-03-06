package com.sem.csounds;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("About");
        String about = "The sound board application for student's to peruse words of humour and wisdom from their favorite Computer Science professors." +
                        "\nCreated for Mobile Application Development at Hanover College, fall 2016."
                        + "\n\nCredits to the following creators:\n";

        String mBio = "Maggie Kleiman: The most unconventional computer scientist. I like lots of for loops, pandas, and listening to the " +
                "birds chirping in the morning. When I'm not in the lab working on my codes, I'm either laying on my couch or looking at " +
                "pictures of my dog, or both of those at the same time. My biggest goal is to convince Dr. Skiadas to have class outside one " +
                "day. My last words to all of you are that you only get one go at this life, to find what you're passionate about, strive " +
                "for greatness, and never forget to laugh.\n";

        String sBio = "Slaton Blickman: Computer Science Allstar. *drops mic*\n";

        String eBio = "Evan Miller: Computer Science Major; Design Minor; Business Minor; Quilter; Musician; Pun-Enthusiast; Undecided on Life. " +
                "Favorite Quote: When killing time, don't murder opportunity. " +
                "In the words of Forrest Gump: And that's all I've got to say about that.\n";

        TextView maggie = (TextView) findViewById(R.id.maggie);
        TextView evan = (TextView) findViewById(R.id.evan);
        TextView slaton =  (TextView) findViewById(R.id.slaton);
        TextView aboutText = (TextView) findViewById(R.id.about);

        aboutText.setText(about);
        evan.setText(eBio);
        slaton.setText(sBio);
        maggie.setText(mBio);
    }
}
