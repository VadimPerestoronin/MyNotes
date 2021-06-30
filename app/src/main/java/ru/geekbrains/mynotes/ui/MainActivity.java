package ru.geekbrains.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.ui.details.NoteDetailsActivity;
import ru.geekbrains.mynotes.ui.details.NoteDetailsFragment;
import ru.geekbrains.mynotes.ui.list.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteClicked {


    private MainRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = new MainRouter(getSupportFragmentManager());

        if (savedInstanceState == null) {
            router.showNotes();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.all_notes) {

                    router.showNotes();
                    return true;
                }

                if (item.getItemId() == R.id.favourite) {


                    return true;
                }
                if (item.getItemId() == R.id.settings) {

                    return true;
                }
                if (item.getItemId() == R.id.about) {

                    router.showAbout();
                    return true;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });




    }


    @Override
    public void onNoteClicked(Note note) {
        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();


        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (isLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_details_fragment, NoteDetailsFragment.newInstance(note))
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment, NoteDetailsFragment.newInstance(note))
                    .addToBackStack("note")
                    .commit();

        }

    }


}