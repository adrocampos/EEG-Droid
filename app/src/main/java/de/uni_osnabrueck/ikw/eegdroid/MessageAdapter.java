package de.uni_osnabrueck.ikw.eegdroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class MessageAdapter extends ArrayAdapter<String[]> {

    private static final String TAG = "MessageAdapter";
    int mResource;
    private Context mContext;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String[]> objects) {
        super(context, resource, objects);
        //this.mContext = mContext;
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvEpibot = convertView.findViewById(R.id.AdapterEpibotTextView);
        TextView tvUser = convertView.findViewById(R.id.AdapterUserTextView);
        TextView tvMsj = convertView.findViewById(R.id.AdapterMessageTextView);
        ImageView ivIma = convertView.findViewById(R.id.AdapterImageView);

        String user = getItem(position)[0];
        String type = getItem(position)[2];
        String msj = "";
        String ima = "https://image.ibb.co/jMVGYK/White_Pixels.png";
        Bitmap image;

        if (type.equals("text")) {
            msj = getItem(position)[1];
        } else if (type.equals("image")) {
            ima = getItem(position)[1];
        }


        try {
            image = new DownloadImageTask(ivIma).execute(ima).get();
        } catch (InterruptedException e) {
            image = null;
        } catch (ExecutionException e) {
            image = null;
        }

        if (user.equals("epibot")) {
            tvEpibot.setVisibility(View.VISIBLE);
            tvUser.setVisibility(View.INVISIBLE);
            tvMsj.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        } else {
            tvEpibot.setVisibility(View.INVISIBLE);
            tvUser.setVisibility(View.VISIBLE);
            tvUser.setGravity(Gravity.RIGHT);
            tvMsj.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            tvMsj.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }

        ivIma.setImageBitmap(image);
        //tvUser.setText(user);
        tvUser.setText(mContext.getSharedPreferences("userPreferences", MODE_PRIVATE).getString("username", "user"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvMsj.setText(HtmlCompat.fromHtml(msj, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else tvMsj.setText(Html.fromHtml(msj));
        return convertView;
    }

}
