package com.example.spring_bot_telegram.service;


import com.example.spring_bot_telegram.config.BotConfig;
import com.example.spring_bot_telegram.domain.Joke;
import com.example.spring_bot_telegram.repos.JokeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot {

    private static final String HELP_MESSAGE = "Здесь будет текстовый файл help";
    @Autowired
    private final BotConfig config;
    @Autowired
    private JokeRepo jokeRepo;

    public TelegramBotService(BotConfig config) {
        this.config = config;
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "Запуск бота"));
        commands.add(new BotCommand("/help", "Помощь"));
        commands.add(new BotCommand("/joke", "Выбрать шутку"));
        commands.add(new BotCommand("/setting", "Изменить настройки"));
        try {
            this.execute(new SetMyCommands(commands,new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }



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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (message) {
                    case "/start" -> {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            TypeFactory typeFactory = objectMapper.getTypeFactory();
                            List<Joke> jokeList = objectMapper.readValue(new File("db/stupidstuff.json"),
                                    typeFactory.constructCollectionType(List.class, Joke.class));
                            jokeRepo.saveAll(jokeList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "/help" -> sendMessage(chatId, HELP_MESSAGE);
                    default -> sendMessage(chatId, "Sorry, message is not found");
                }
            }

    }


    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет :bear: " + name + "!");
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



}
