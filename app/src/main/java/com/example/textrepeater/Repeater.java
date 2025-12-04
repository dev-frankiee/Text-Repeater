package com.example.textrepeater;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;

public class Repeater extends AppCompatActivity {
    EditText valueText, valueNumber, etSeparator;
    MaterialButton repeatButton, copyButton, shareButton;
    CheckBox cbNewLine, cbNumbering, cbEmojiBomb;
    TextView valueView, tvCounter;
    String[] emojis = {"😊", "👍", "🚀", "💯", "🔥", "🎉", "😎", "👏", "💪", "😍"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repeater);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Init views
        valueText = findViewById(R.id.valueTxt);
        valueNumber = findViewById(R.id.valueNum);
        etSeparator = findViewById(R.id.etSeparator);
        repeatButton = findViewById(R.id.repeatBtn);
        copyButton = findViewById(R.id.copyBtn);
        shareButton = findViewById(R.id.shareBtn);
        cbNewLine = findViewById(R.id.cbNewLine);
        cbNumbering = findViewById(R.id.cbNumbering);
        cbEmojiBomb = findViewById(R.id.cbEmojiBomb);
        tvCounter = findViewById(R.id.tvCounter);
        valueView = findViewById(R.id.valueView);

        // Repeat button
        repeatButton.setOnClickListener(view -> {
            String inputText = valueText.getText().toString().trim();
            String inputNumber = valueNumber.getText().toString().trim();
            if (inputText.isEmpty() || inputNumber.isEmpty()) {
                Toast.makeText(this, "Enter text and count", Toast.LENGTH_SHORT).show();
                return;
            }
            int count = Integer.parseInt(inputNumber);
            StringBuilder builder = new StringBuilder();
            String separator = cbNewLine.isChecked() ? "\n" : " ";
            if (!etSeparator.getText().toString().isEmpty()) {
                separator = etSeparator.getText().toString();
            }
            for (int i = 0; i < count; i++) {
                if (cbNumbering.isChecked()) {
                    builder.append((i + 1) + ". ");
                }
                builder.append(inputText);
                if (cbEmojiBomb.isChecked() && i < emojis.length) {
                    builder.append(" " + emojis[i % emojis.length]);
                }
                if (i < count - 1) builder.append(separator);
            }
            valueView.setText(builder.toString());

            // Update counter
            int charCount = builder.toString().length();
            int wordCount = inputText.split("\\s+").length * count; // rough word count
            tvCounter.setText("Chars: " + charCount + " | Words: ~" + wordCount);
        });

        // Copy button
        copyButton.setOnClickListener(View -> {
            String output = valueView.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("Repeated Text", output);
            clipboard.setPrimaryClip(data);
            Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show();
        });

        // Share button
        shareButton.setOnClickListener(v -> {
            String text = valueView.getText().toString();
            if (!text.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setPackage("com.whatsapp"); // WhatsApp
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Intent fallback = new Intent(Intent.ACTION_SEND);
                    fallback.setType("text/plain");
                    fallback.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(fallback, "Share via"));
                }
            }
        });
    }
}
