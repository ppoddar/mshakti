package org.shakti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.shakti.R;

/**
 * The front page.
 */
public class FrontPageActivity extends AppCompatActivity {
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.frontpage_activity);

        FloatingActionButton read = (FloatingActionButton)findViewById(R.id.read_button);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readIntent = new Intent(FrontPageActivity.this, IndexPageActivity.class);

                startActivity(readIntent);

                FrontPageActivity.this.finish();
            }
        });
    }


    /**
     TaskStackBuilder taskBuilder = TaskStackBuilder.create(activity);

     PendingIntent pendingIntent = taskBuilder
     .addNextIntentWithParentStack(readIntent)
     .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

     NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
     builder.setContentIntent(pendingIntent);

     taskBuilder.startActivities();

     try {
     pendingIntent.send();
     } catch (PendingIntent.CanceledException e) {
     e.printStackTrace();
     }
     */




}
