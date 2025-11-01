package com.example.interview_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;

public class SimpleBarChart extends View {

    private Paint barPaint;
    private Paint textPaint;
    private Paint labelPaint;

    private List<Float> values = Arrays.asList(10f, 85f, 78f);
    private List<String> labels = Arrays.asList("Interviews", "Accuracy", "Performance");
    private List<Integer> colors = Arrays.asList(
            Color.parseColor("#FFD600"), // yellow
            Color.parseColor("#2979FF"), // blue
            Color.parseColor("#00C853")  // green
    );

    public SimpleBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary));
        textPaint.setTextSize(36f);

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(ContextCompat.getColor(getContext(), R.color.muted_foreground));
        labelPaint.setTextSize(34f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values == null || values.isEmpty()) return;

        float barWidth = getWidth() / (values.size() * 2f);
        float maxVal = 0;
        for (float v : values) {
            if (v > maxVal) maxVal = v;
        }

        float chartHeight = getHeight() * 0.7f;

        for (int i = 0; i < values.size(); i++) {
            float x = barWidth + i * (barWidth* 1.7f);
            float barHeight = (values.get(i) / maxVal) * chartHeight;
            float top = getHeight() - barHeight - 60f;

            barPaint.setColor(colors.get(i % colors.size()));
            canvas.drawRoundRect(x, top, x + barWidth, getHeight() - 60f, 20f, 20f, barPaint);

            // Label below bar
            String label = labels.get(i);
            float labelWidth = labelPaint.measureText(label);
            canvas.drawText(label, x + (barWidth - labelWidth) / 2, getHeight() - 15f, labelPaint);

            // Value text above bar
            String valueText = String.valueOf(values.get(i).intValue());
            float textWidth = textPaint.measureText(valueText);
            canvas.drawText(valueText, x + (barWidth - textWidth) / 2, top - 10f, textPaint);
        }
    }

    // Optional setter methods if you want to update chart later
    public void setData(List<Float> newValues, List<String> newLabels) {
        this.values = newValues;
        this.labels = newLabels;
        invalidate();
    }

    public void setColors(List<Integer> newColors) {
        this.colors = newColors;
        invalidate();
    }
}
