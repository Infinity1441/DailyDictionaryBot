package com.company.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;

public class InlineKeyboardButtonUtil {

    public static InlineKeyboardMarkup getConfirmOrCancel() {

        InlineKeyboardButton confirm = new InlineKeyboardButton(InlineButtonConstants.CONFIRM);
        confirm.setCallbackData(InlineButtonConstants.CONFIRM_CALLBACK);
        InlineKeyboardButton cancel = new InlineKeyboardButton(InlineButtonConstants.CANCEL);
        cancel.setCallbackData(InlineButtonConstants.CANCEL_CALLBACK);

        return new InlineKeyboardMarkup(List.of(
                List.of(confirm, cancel)
        ));

    }

    public static InlineKeyboardMarkup getManualOrAutomatic(){

        InlineKeyboardButton manual = new InlineKeyboardButton(InlineButtonConstants.MANUAL);
        manual.setCallbackData(InlineButtonConstants.MANUAL_CALLBACK);
        InlineKeyboardButton automatic = new InlineKeyboardButton(InlineButtonConstants.AUTOMATIC);
        automatic.setCallbackData(InlineButtonConstants.AUTOMATIC_CALLBACK);

        return new InlineKeyboardMarkup(List.of(
                List.of(manual, automatic)
        ));
    }

}
