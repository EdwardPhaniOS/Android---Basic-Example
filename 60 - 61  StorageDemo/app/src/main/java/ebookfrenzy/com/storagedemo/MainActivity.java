package ebookfrenzy.com.storagedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private static EditText textView;
    private static int CREATE_REQUEST_CODE = 40;
    private static int OPEN_REQUEST_CODE = 41;
    private static int SAVE_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == CREATE_REQUEST_CODE)
            {
                if (data != null) {
                    textView.setText("");
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {
                if (data != null) {
                    currentUri = data.getData();
                    writeFileContent(currentUri);
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {
                if (data != null) {
                    currentUri = data.getData();

                    try {
                        String content = readFileContent(currentUri);
                        textView.setText(content);

                    } catch (IOException e) {
                        //Handle error here
                    }
                }
            }
        }
    }

    private String readFileContent(Uri uri) throws IOException
    {
        InputStream inputStream = this.getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            stringBuilder.append(currentLine + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    private void writeFileContent(Uri uri)
    {
        try {
            ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());

            String textContent = textView.getText().toString();

            fileOutputStream.write(textContent.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newFile(View view) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt");

        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    public void saveFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, SAVE_REQUEST_CODE);
    }

    public void openFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }
}
