import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ValidationResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import net.objecthunter.exp4j.ExpressionBuilder;


public class Responser {
    private final Watchdog watchdog = new Watchdog(this);
    private final telegramBot bot;

    public Responser(telegramBot bot) {
        this.bot = bot;
    }

    public void parseUpdate(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(message_text.equals("/start")) {
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText("Hello, buddy! I can evaluate your " +
                                "integral. To start just pass me the function :)\n" +
                                "Example: 3*sin(x)");
                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (message_text.equals("/help")) {
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText("The list of allowed functions \n" +
                                "abs(x)\n" +
                                "acos(x)\n" +
                                "asin(x)\n" +
                                "atan(x)\n" +
                                "cos(x)\n" +
                                "exp(x)\n" +
                                "log(x)\n" +
                                "log10(x)\n" +
                                "log2(x)\n" +
                                "sin(x)\n" +
                                "sqrt(x)\n" +
                                "tan(x)\n");
                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                if (watchdog.checkIfUserHasInputFunction(chat_id)) {
                    if (message_text.matches("-?\\d+(.*)\\s(-?)\\d+(.*)")) {
                        String[] p = message_text.split("\\s");
                        Double xmin = Double.parseDouble(p[0]);
                        Double xmax = Double.parseDouble(p[1]);
                        if (xmin <= xmax)
                            watchdog.addPointsToOrder(chat_id, xmin, xmax);
                        else {
                        }

                        //REPORT ERROR

                    } else {
                    }

                    //REPORT ERROR

                } else {
                    Expression e = new ExpressionBuilder(message_text).variable("x").build();
                    ValidationResult res = e.validate();
                    if (res.isValid()) {
                        watchdog.addOrder(chat_id, message_text);
                    } else {
                    }

                    //REPORT ERROR

                }
            }


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
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
        }
    }
}
