import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class telegramBot extends TelegramLongPollingBot {
    private long creator = 338022665;

    //@Override
    public String getBotUsername() {
        // TODO
        return "integrattorbot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "735597691:AAENMMTbqcNwvE9SXNmNCmO8yhaUt9r57UA";
    }

    //@Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();


            //***debug
            String user_username = update.getMessage().getChat().getUserName();
            SendMessage forward = new SendMessage()
                    .setChatId(creator)
                    .setText("@" + user_username + " " + message_text);
            try {
                execute(forward);
            } catch(TelegramApiException e){
                e.printStackTrace();
            }
            //***


            if(message_text.equals("/start")) {
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText("Привет, я могу посчитать твой интеграл. Для начала " +
                                "введи функцию и промежуток интегрирования через запятые :)\n" +
                                "Пример: 3*sin(x), -2, 3");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message_text.equals("/help")) {
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText("Список доступных функций \n" +
                                "abs(x): модуль x\n" +
                                "acos(x): arccos(x)\n" +
                                "asin(x): arcsin(x)\n" +
                                "atan(x): arctg(x)\n" +
                                "cos(x): cos(x)\n" +
                                "exp(x): e^x\n" +
                                "log(x): ln(x)\n" +
                                "log10(x): log(x) по основанию 10\n" +
                                "log2(x): log(x) по основанию 2\n" +
                                "sin(x): sin(x)\n" +
                                "sqrt(x): квадр. корень x\n" +
                                "tan(x): tg(x)\n");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage message = new SendMessage();

                try{
                    int firstEnd = message_text.indexOf(',');
                    int secondEnd = message_text.lastIndexOf(',');
                    String function = message_text.substring(0, firstEnd);
                    String left = message_text.substring(firstEnd + 1, secondEnd);
                    String right = message_text.substring(secondEnd + 1);

                    double xmin = Double.parseDouble(left);
                    double xmax = Double.parseDouble(right);

                    IntegrationHub hub = new IntegrationHub(function, xmin, xmax);

                    double result1 = hub.getResult1();
                    double result2 = hub.getResult2();
                    double result3 = hub.getResult3();
                    double result4 = hub.getResult4();
                    double result5 = hub.getResult5();
                    double average = (0.1*result1 + 0.2*result2 + 0.3*result3 + 0.1*result4 + 0.3*result5);


                    message.setChatId(chat_id)
                            .setText("Результат м.прямоугольников: " + String.format("%.5f", result1) +
                                    "\nРезультат м.трапеций: " + String.format("%.5f", result2) +
                                    "\nРезультат м.Симпсона: " + String.format("%.5f", result3) +
                                    "\nРезультат м.3/8: " + String.format("%.5f", result4) +
                                    "\nРезультат м.Гауcса: " + String.format("%.5f", result5) +
                                    "\n\nПредлагаемый результат: " + String.format("%.5f", average));
                } catch (Exception e){
                    message.setChatId(chat_id)
                            .setText("*скрежет шестеренок*\nКажется, бот поломался - опять чинить. Попробуйте что-то другое :/");
                }

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
