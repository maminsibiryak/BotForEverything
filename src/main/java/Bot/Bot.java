package Bot;


import classes.ParseCinema;
import classes.Weather;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.jsoup.nodes.Document;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        String msg;
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help"))
            {
                List<String> strings = new LinkedList<>();
                strings.add("/cinema" + "- расписание сеансов кино ");
                sendMes(message, String.valueOf(strings));
            }
            if (message.getText().equals("/cinema"))
            {

                try {


                    Document page = ParseCinema.getPage();
                    sendMes(message, String.valueOf(ParseCinema.printCinema(page)));

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            if (message.getText().contains("/погода"))
            {

                try {
                    String city[] = message.getText().split("/погода ");

                    Weather weather = new Weather();
                  /*  for (int i = 0; i <city.length ; i++) {
                        System.out.println(city[i]);
                    }

                    System.out.println(message.getText());*/

                    msg = "погода на сегодня для тебя, "+message.getFrom().getFirstName();
                    System.out.println(msg);
                    SendMessage sendMessage = new SendMessage().setChatId(message.getChatId());
                    sendMessage.setText(msg+"\n"+String.valueOf(weather.getWeather(city[1])));
                    sendMessage(sendMessage);



                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }


        }

    }


    private void sendMes(Message m, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(m.getChatId().toString());
        sendMessage.setReplyToMessageId(m.getMessageId());
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "BotForEverything";
    }

    public String getBotToken() {
        return "311345541:AAEd7rUNP5Aj5FgxgSGAWVtVCpv8GLNAoBU";
    }
}
