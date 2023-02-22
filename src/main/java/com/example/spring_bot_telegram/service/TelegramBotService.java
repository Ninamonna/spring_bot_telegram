package com.example.spring_bot_telegram.service;


import com.example.spring_bot_telegram.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotService extends TelegramLongPollingBot {

    @Autowired
    private final BotConfig config;

    public TelegramBotService(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message  = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String nameUser = update.getMessage().getChat().getFirstName();
            switch (message) {
                case "/start" : startCommandReceived(chatId, nameUser);
                break;
                default: sendMessage(chatId, "Sorry, message is not found");
            }

        }

    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Hello" + name + "!";
        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
