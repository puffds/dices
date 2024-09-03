package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import static org.example.BotConstants.*;
import static org.example.Calculations.calculate;
import static org.example.Utils.checkInput;

public class Bot {
    // Вот с токеном
    private static final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

    // Точка входа в программу
    public static void main(String[] args) {
        listener();
    }

    // Обработчик сообщений
    private static void listener() {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null && update.message().text() != null) {
                    long chatId = update.message().chat().id();
                    String messageText = update.message().text();

                    if (messageText.equals("/start")) {
                        handleStart(chatId);
                        continue;
                    }

                    if (messageText.equals("/help")) {
                        handleHelp(chatId);
                        continue;
                    }

                    checkMessageAndCalculate(chatId, messageText);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    /**
     * Проверить сообщение от кожаного и выполнить расчёты
     *
     * @param chatId      - чат Id
     * @param messageText - текст сообщения
     */
    private static void checkMessageAndCalculate(long chatId, String messageText) {
        if (checkInput(messageText, REGEX)) {
            sendMessage("Результат: " + calculate(messageText), chatId);
        } else {
            sendMessage(ERROR_MESSAGE, chatId);
        }
    }

    /**
     * Обработать команду start
     *
     * @param chatId - чат Id
     */
    private static void handleStart(long chatId) {
        sendMessage(HELP_MESSAGE + "\n\nНабери /help что бы вывести справку ещё раз", chatId);
    }

    /**
     * Обработать команду help
     *
     * @param chatId - чат Id
     */
    private static void handleHelp(long chatId) {
        sendMessage(HELP_MESSAGE, chatId);
    }

    /**
     * Отправить сообщение
     *
     * @param messageText - текст сообщения
     * @param chatId      - чат Id
     */
    private static void sendMessage(String messageText, long chatId) {
        SendMessage request = new SendMessage(chatId, messageText);
        SendResponse sendResponse = bot.execute(request);

        if (!sendResponse.isOk()) {
            System.out.println("Ошибка отправки сообщения: "
                    + sendResponse.errorCode() + " - " + sendResponse.description());
        }
    }
}