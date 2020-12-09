package com.spreedly.threedssecure;

import com.seglan.threeds.sdk.customization.ButtonCustomization;
import com.seglan.threeds.sdk.customization.LabelCustomization;
import com.seglan.threeds.sdk.customization.TextBoxCustomization;
import com.seglan.threeds.sdk.customization.ToolbarCustomization;
import com.seglan.threeds.sdk.customization.UiCustomization;

import static com.seglan.threeds.sdk.customization.UiCustomization.ButtonType.CANCEL;
import static com.seglan.threeds.sdk.customization.UiCustomization.ButtonType.CONTINUE;
import static com.seglan.threeds.sdk.customization.UiCustomization.ButtonType.NEXT;
import static com.seglan.threeds.sdk.customization.UiCustomization.ButtonType.RESEND;
import static com.seglan.threeds.sdk.customization.UiCustomization.ButtonType.VERIFY;

public class SpreedlyThreeDSTheme {
    public Integer buttonCornerRadius;
    public String buttonFontName;
    public Integer buttonFontSize;
    public String buttonPositiveTextColor;
    public String buttonNeutralTextColor;
    public String buttonPositiveBackgroundColor;
    public String buttonNeutralBackgroundColor;

    public String toolbarColor;
    public String toolbarTextColor;
    public String toolbarHeaderText;
    public String toolbarButtonText;

    public String textFontName;
    public String textColor;
    public Integer textFontSize;

    public String headingTextFontName;
    public String headingTextColor;
    public Integer headingTextFontSize;

    public Integer textBoxBorderWidth;
    public String textBoxBorderColor;
    public Integer textBoxCornerRadius;

    public UiCustomization toUiCustomization() {
        UiCustomization ui = new UiCustomization();

        ButtonCustomization button = toPositiveButton();
        if (button != null) {
            ui.setButtonCustomization(button, NEXT);
            ui.setButtonCustomization(button, CONTINUE);
            ui.setButtonCustomization(button, VERIFY);
        }
        button = toNeutralButton();
        if (button != null) {
            ui.setButtonCustomization(button, CANCEL);
            ui.setButtonCustomization(button, RESEND);
        }
        LabelCustomization label = toLabel();
        if (label != null) {
            ui.setLabelCustomization(label);
        }
        TextBoxCustomization textbox = toTextBox();
        if (textbox != null) {
            ui.setTextBoxCustomization(textbox);
        }
        ToolbarCustomization toolbar = toToolbar();
        if (toolbar != null) {
            ui.setToolbarCustomization(toolbar);
        }
        return ui;
    }

    LabelCustomization toLabel() {
        if (!(headingTextColor != null || headingTextFontName != null || headingTextFontSize != null || textColor != null || textFontName != null || textFontSize != null)) {
            return null;
        }

        LabelCustomization l = new LabelCustomization();
        String color = textColor;
        if (color != null) {
            l.setTextColor(color);
        }
        String font = textFontName;
        if (font != null) {
            l.setTextFontName(font);
        }
        Integer size = textFontSize;
        if (size != null) {
            l.setTextFontSize(size);
        }
        color = headingTextColor;
        if (color != null) {
            l.setHeadingTextColor(color);
        }
        font = headingTextFontName;
        if (font != null) {
            l.setHeadingTextFontName(font);
        }
        size = headingTextFontSize;
        if (size != null) {
            l.setHeadingTextFontSize(size);
        }
        return l;
    }

    ToolbarCustomization toToolbar() {
        if (!(toolbarButtonText != null || toolbarHeaderText != null || toolbarColor != null || toolbarTextColor != null || textFontName != null || textFontSize != null)) {
            return null;
        }

        ToolbarCustomization l = new ToolbarCustomization();
        String color = toolbarTextColor;
        if (color != null) {
            l.setTextColor(color);
        }
        String font = textFontName;
        if (font != null) {
            l.setTextFontName(font);
        }
        Integer size = textFontSize;
        if (size != null) {
            l.setTextFontSize(size);
        }
        color = toolbarColor;
        if (color != null) {
            l.setBackgroundColor(color);
        }
        String text = toolbarButtonText;
        if (text != null) {
            l.setButtonText(text);
        }
        text = toolbarHeaderText;
        if (text != null) {
            l.setHeaderText(text);
        }
        return l;
    }

    TextBoxCustomization toTextBox() {
        if (!(textBoxBorderColor != null || textBoxBorderWidth != null || textBoxCornerRadius != null || textColor != null || textFontName != null || textFontSize != null)) {
            return null;
        }

        TextBoxCustomization l = new TextBoxCustomization();
        String color = textColor;
        if (color != null) {
            l.setTextColor(color);
        }
        String font = textFontName;
        if (font != null) {
            l.setTextFontName(font);
        }
        Integer size = textFontSize;
        if (size != null) {
            l.setTextFontSize(size);
        }
        color = textBoxBorderColor;
        if (color != null) {
            l.setBorderColor(color);
        }
        Integer width = textBoxBorderWidth;
        if (width != null) {
            l.setBorderWidth(width);
        }
        Integer radius = textBoxCornerRadius;
        if (radius != null) {
            l.setCornerRadius(radius);
        }
        return l;
    }

    ButtonCustomization toPositiveButton() {
        if (!(buttonCornerRadius != null || buttonPositiveBackgroundColor != null || buttonPositiveTextColor != null || buttonFontName != null || buttonFontSize != null)) {
            return null;
        }

        ButtonCustomization b = new ButtonCustomization();
        Integer radius = buttonCornerRadius;
        if (radius != null) {
            b.setCornerRadius(radius);
        }
        String color = buttonPositiveTextColor;
        if (color != null) {
            b.setTextColor(color);
        }
        color = buttonPositiveBackgroundColor;
        if (color != null) {
            b.setBackgroundColor(color);
        }
        String font = buttonFontName;
        if (font != null) {
            b.setTextFontName(font);
        }
        Integer size = buttonFontSize;
        if (size != null) {
            b.setTextFontSize(size);
        }
        return b;
    }

    ButtonCustomization toNeutralButton() {
        if (!(buttonCornerRadius !=
                null || buttonNeutralBackgroundColor != null || buttonNeutralTextColor != null || buttonFontName != null || buttonFontSize != null)) {
            return null;
        }

        ButtonCustomization b = new ButtonCustomization();
        Integer radius = buttonCornerRadius;
        if (radius != null) {
            b.setCornerRadius(radius);
        }
        String color = buttonNeutralTextColor;
        if (color != null) {
            b.setTextColor(color);
        }
        color = buttonNeutralBackgroundColor;
        if (color != null) {
            b.setBackgroundColor(color);
        }
        String font = buttonFontName;
        if (font != null) {
            b.setTextFontName(font);
        }
        Integer size = buttonFontSize;
        if (size != null) {
            b.setTextFontSize(size);
        }
        return b;
    }
}
