package info.aario.snotepad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;

/**
 * Created by Xie on 11/27/18.
 */

public class SettingsFragment extends Fragment {
    private MainActivity activity;
    private Button btChangePath;
    private TextView tvPath;
    private static final int FILE_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        tvPath = (TextView) view.findViewById(R.id.tvPath);
        tvPath.setText(activity.getPath());
        btChangePath = (Button) view.findViewById(R.id.btChangePath);
        btChangePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity, FilePickerActivity.class);
                // 定义过滤器
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, true);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_DIR);

                // 通过指定字符串来配置初始目录。
                // 您可以指定一个字符串"/storage/emulated/0/",
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, activity.getPath());

                startActivityForResult(i, FILE_CODE);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(activity, android.R.drawable.ic_menu_save));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setPath(tvPath.getText().toString());
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            // 现在路径//PACKAGE-NAME/root/path/to/file
            Uri uri = intent.getData();
            // 一种将URI转换为文件对象的实用方法。
            File file = com.nononsenseapps.filepicker.Utils.getFileForUri(uri);
            // 想获得url能匹配老的返回值
            Uri fileUri = Uri.fromFile(file);
            tvPath.setText(fileUri.getPath());
        }
    }
}
