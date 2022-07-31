

import model.Model;
import model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    private Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi =
                    new TelegramBotsApi(
                            DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
//Для приема сообщений


    //Ответ на сообщение клиента

    public void sendMsg(Message message, String text) {
        SendMessage sendMesage = new SendMessage(); //инициализация сообщения
        sendMesage.enableMarkdown(true);
        sendMesage.setChatId(message.getChatId().toString());  //Бот должен знать на какой чат отвечать
        sendMesage.setReplyToMessageId(message.getMessageId()); //Ответ на сообщение
        sendMesage.setText(text);
        try {
            sendButtons(sendMesage);
            execute(sendMesage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        Model model= new Model();
        var message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "/settings":
                    sendMsg(message, "Что будем настраивать?");
                    break;
                default:
                    try
                    {
                        sendMsg(message, Weather.getWeather(message.getText(),model));
                    }
                    catch (IOException e)
                    {
                        sendMsg(message,"Город не найден!");
                    }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "Alisher'sBot";
    }

    @Override
    public String getBotToken() {
        return "5565834892:AAHZeESp2BjtUMeFzErzdg9-tCT0x8ARdd8";
    }

    //Метод создающий клавиатуру под панелью

    public void sendButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(); //инициализация клавиатуры
        sendMessage.setReplyMarkup(keyboardMarkup); //Связывание клавиатуры с сообщением
        keyboardMarkup.setSelective(true); // определяет всем нужно показывать или опред пользователям клавиатуру
        keyboardMarkup.setResizeKeyboard(true); //подгоняет размер клавиатуры
        keyboardMarkup.setOneTimeKeyboard(false); //определяет скрывать клаву после использования(тут нет)

        List<KeyboardRow> buttons = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/help"));
        row1.add(new KeyboardButton("/settings"));
        buttons.add(row1);
        keyboardMarkup.setKeyboard(buttons);

    }
}
