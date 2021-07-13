package de.uni_osnabrueck.ikw.eegdroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import de.uni_osnabrueck.ikw.eegdroid.utilities.SessionAdapter;

public class ManageSessions extends AppCompatActivity {

    private SessionAdapter adapter;
    private String saveDir;
    private ArrayList<File> arrayListOfFiles;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sessions);
        readDirectory(MainActivity.getDirSessions());

        //List <Files> to save the current state directory
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SessionAdapter(arrayListOfFiles, getApplicationContext());
        recyclerView.setAdapter(adapter);

        // Add line between items of RecyclerView
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 1);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        // Receive the directory of the EEG Sessions
        Intent intent = getIntent();
        saveDir = intent.getExtras().getString("dirString");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage, menu);
        MenuItem item = menu.findItem(R.id.send_session);
        MenuItem launchFileManager = menu.findItem(R.id.launch_file_manager);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        final int position = adapter.getSelectedPos();

        //Handles if no session has been selected
        if (position == -1) {

            if (item.getItemId() == R.id.launch_file_manager){
                launchFileManager();
                return true;
            }

            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }
            Toast.makeText(getApplicationContext(), R.string.warning_select_session, Toast.LENGTH_LONG).show();
            return super.onOptionsItemSelected(item);

        } else {

            switch (item.getItemId()) {

                case android.R.id.home:
                    onBackPressed();
                    return true;

                case R.id.send_session:

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, TextUtils.concat("EEG Session:", " ", arrayListOfFiles.get(position).getName()));
                    Log.d("Auth", getApplicationContext().getPackageName());
                    Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", arrayListOfFiles.get(position));
                    intent.putExtra(Intent.EXTRA_STREAM, uri);

                    if (shareActionProvider != null) {
                        shareActionProvider.setShareIntent(intent);
                    }

                    adapter.resetSelectedPos();
                    return true;

                case R.id.rename_session:

                    LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
                    View mView = layoutInflaterAndroid.inflate(R.layout.input_dialog_string, null);
                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
                    alertDialogBuilderUserInput.setView(mView);
                    final EditText userInputDialogEditText = mView.findViewById(R.id.input_dialog_string_Input);

                    alertDialogBuilderUserInput
                            .setCancelable(false)
                            .setTitle(R.string.rename_title)
                            .setMessage(getResources().getString(R.string.ask_new_name) + " " + arrayListOfFiles.get(position).getName() + ":")
                            .setPositiveButton(R.string.rename, (dialogBox, id) -> {
                                String oldName = arrayListOfFiles.get(position).getName().substring(0, 20);
                                File newName = new File(saveDir, oldName + userInputDialogEditText.getText().toString() + ".csv");
                                //Check if exist another file with this name
                                if (arrayListOfFiles.contains(newName)) {
                                    Toast.makeText(getApplicationContext(), R.string.warning_rename, Toast.LENGTH_LONG).show();
                                    dialogBox.cancel();
                                    adapter.resetSelectedPos();
                                } else {
                                    arrayListOfFiles.get(position).renameTo(newName);
                                    arrayListOfFiles.set(position, newName);
                                    Collections.sort(arrayListOfFiles, Collections.reverseOrder());
                                    //adapter.notifyItemChanged(position);
                                    adapter.notifyDataSetChanged();
                                    adapter.resetSelectedPos();
                                }
                            })
                            .setNegativeButton(R.string.cancel,
                                    (dialogBox, id) -> {
                                        dialogBox.cancel();
                                        adapter.resetSelectedPos();
                                    });

                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                    alertDialogAndroid.show();
                    return true;

                case R.id.delete_session:

                    //Handles the Dialog to confirm the file delete
                    AlertDialog.Builder alert = new AlertDialog.Builder(this)
                            .setTitle(R.string.dialog_title)
                            .setMessage(getResources().getString(R.string.confirmation_delete) + " " + arrayListOfFiles.get(position).getName() + "?");
                    alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                        arrayListOfFiles.get(position).delete();
                        arrayListOfFiles.remove(position);
                        adapter.notifyItemRemoved(position);
                        //adapter.resetSelectedPos();
                    });
                    alert.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                        // close dialog
                        dialog.cancel();
                        adapter.resetSelectedPos();
                    });
                    alert.show();
                    adapter.resetSelectedPos();
                    return true;


                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

    private void launchFileManager(){
        // Construct an intent for opening a folder
        Uri selectedUri = Uri.parse(MainActivity.getDirSessions().toString());
        Log.d("ManageSessions: ", MainActivity.getDirSessions().toString());
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
        {
            startActivity(intent);
        }
        else
        {
            // if you reach this place, it means there is no any file
            // explorer app installed on your device
        }
    }

    public void createDirectory(File dir) {
        if (!dir.exists()) {
            dir.mkdirs(); // creates needed dirs
        }
    }

    //Returns a list of recordings in directory
    public void readDirectory(File dir) {
        arrayListOfFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
        Collections.sort(arrayListOfFiles, Collections.reverseOrder());
        //Add if here?
    }

    //Returns a ListArray of the files in the directory
    public ArrayList<File> getArrayListOfFiles() {
        readDirectory(MainActivity.getDirSessions());
        return arrayListOfFiles;
    }


}
