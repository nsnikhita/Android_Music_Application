package com.uic.snaram2.mymusic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
   // int[] images = {R.drawable.perfect, R.drawable.galwaygirl, R.drawable.uptownfunknew, R.drawable.newrules, R.drawable.starboy, R.drawable.uptownfunknew,R.drawable.uptownfunknew};
    ArrayList<String> songtitles = new ArrayList<>(Arrays.asList("Perfect", "Galway Girl", "Uptown Funk", "New Rules", "Starboy","Thunder"));
    ArrayList<String> songartists = new ArrayList<>(Arrays.asList("Edsheeran", "Edsheeran", "Bruno Mars", "Dua Lipa", "The Weeknd","Imagine Dragons"));
    ArrayList<String> weblinks = new ArrayList<>(Arrays.asList("www.youtube.com/watch?v=2Vv-BfVoq4g",
            "www.youtube.com/watch?v=87gWaABqGYs",
            "www.youtube.com/watch?v=OPf0YbXqDm0",
            "www.youtube.com/watch?v=k2qgadSvNyU",
            "www.youtube.com/watch?v=34Na4j8AVgA","www.youtube.com/watch?v=fKopy74weus"));
    ArrayList<String> songwiki =new ArrayList<>(Arrays.asList("en.wikipedia.org/wiki/Perfect_(Ed_Sheeran_song)",
            "en.wikipedia.org/wiki/Galway_Girl_(Ed_Sheeran_song)",
            "en.wikipedia.org/wiki/Uptown_Funk","en.wikipedia.org/wiki/New_Rules_(song)",
            "en.wikipedia.org/wiki/Starboy_(album)","en.wikipedia.org/wiki/Thunder_(Imagine_Dragons_song)"));
    ArrayList<String>artistwiki = new ArrayList<>(Arrays.asList("en.wikipedia.org/wiki/Ed_Sheeran",
            "en.wikipedia.org/wiki/Ed_Sheeran",
            "en.wikipedia.org/wiki/Uptown_Funk","en.wikipedia.org/wiki/Dua_Lipa",
            "en.wikipedia.org/wiki/The_Weeknd",
            "en.wikipedia.org/wiki/Imagine_Dragons"));


    EditText edittextartisturl;
    EditText edittexttitle;
    EditText edittextartist;
    EditText edittextsongwikiurl;
    EditText edittextsongvideourl;
    ListViewAdapter lw;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//     finding the listview and assigning it to the adapter
        ListView listView = (ListView) findViewById(R.id.listofsongs);
        registerForContextMenu(listView);

            lw = new ListViewAdapter(this, songtitles, songartists, weblinks);
            listView.setAdapter(lw);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent webview = new Intent(MainActivity.this, WebviewActivity.class);
                    String item_link = weblinks.get(position);
                    webview.putExtra("URL", item_link);
                    startActivity(webview);
                }
            });


        }
//method to add a song to the listview using dialog popup
    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //inflate the dialog layoutwith widgets using inflater
        LayoutInflater inflater =   LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_window,null);
        edittextartisturl = (EditText)view.findViewById(R.id.artisturl_text);
        edittexttitle = (EditText) view.findViewById(R.id.title);
        edittextartist = (EditText) view.findViewById(R.id.artist);
        edittextsongwikiurl = (EditText) view.findViewById(R.id.songwikiurl);
        edittextsongvideourl = (EditText) view.findViewById(R.id.songvideourl);

        builder.setView(view).setTitle("Enter Details").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//  Adding songs entered by the user into the araylist
                songtitles.add(edittexttitle.getText().toString());
                songartists.add(edittextartist.getText().toString());
                weblinks.add(edittextsongvideourl.getText().toString());
                songwiki.add(edittextsongwikiurl.getText().toString());
                artistwiki.add(edittextartisturl.getText().toString());
                // every time a list is updated we should notify the adapter
                lw.notifyDataSetChanged();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addsong:
                openDialog();
                return true;
            case R.id.removesong:
                return true;
            case R.id.exit:
                System.exit(0);
                return true;

            default:
                int index = item.getItemId(); // Get the item using  itemid
                removeSong(index);
                lw.notifyDataSetChanged();
                return super.onOptionsItemSelected(item);
        }
    }

//
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuitem = menu.findItem(R.id.removesong); //this gives the menu item clicked
        SubMenu s = menuitem.getSubMenu(); // this give the submenu
        s.clear();   //As onprepareoptions is called so many times to declare the submenu once we use this clear method to delete submenu created before
        for(int i=0;i<songtitles.size();i++) //this loop creates the submenu
        {
            s.add(0,i,Menu.NONE,songtitles.get(i));
        }

        return super.onPrepareOptionsMenu(menu);
    }
//remove the song  in submenu with that itemid
    public void removeSong(int pos) {
        if (songtitles.size() == 1) {
            Toast.makeText(getBaseContext(), "Can't delete", Toast.LENGTH_SHORT).show();
        } else {
            songtitles.remove(pos);
            songartists.remove(pos);

        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,view,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        //gives the information about the menu items in the context menu
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int doola = menuInfo.position;   // gives the position of the items in the listview to access them on a click
        switch (item.getItemId())
        {
            case R.id.videoclip:
            {
                Intent vc = new Intent(MainActivity.this,WebviewActivity.class);
                vc.putExtra("URL",weblinks.get(doola));
                startActivity(vc);
                return true;
            }
            case R.id.songwikipage:
            {
                Intent vc = new Intent(MainActivity.this,WebviewActivity.class);
                vc.putExtra("wiki",songwiki.get(doola));
                startActivity(vc);
                return true;
            }
            case R.id.artistwikipage:
            {
                Intent vc = new Intent(MainActivity.this,WebviewActivity.class);
                vc.putExtra("wiki",artistwiki.get(doola));
                startActivity(vc);
                return true;
            }
            default:
                return super.onContextItemSelected(item);

        }

    }
}

