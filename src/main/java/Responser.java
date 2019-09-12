import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
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

    public void sendResult(long chatID, double result){
        SendMessage message = new SendMessage()
                .setChatId(chatID)
                .setText("Yeah, here's the result, buddy :)" +
                        "\nResult: " + String.format("%.5f", result));

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void parseUpdate(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            if(message_text.equals("/start")) {
                SendMessage message = new SendMessage()
                        .setChatId(chatID)
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
                        .setChatId(chatID)
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
                if (watchdog.checkIfUserHasInputFunction(chatID)) {
                    if (message_text.matches("-?\\d+(.*)\\s(-?)\\d+(.*)")) {
                        String[] p = message_text.split("\\s");
                        Double xmin = Double.parseDouble(p[0]);
                        Double xmax = Double.parseDouble(p[1]);
                        if (xmin <= xmax) {
                            try {
                                bot.execute(new SendMessage().setChatId(chatID).setText("Let's see..."));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            watchdog.addPointsToOrder(chatID, xmin, xmax);
                        } else {
                            try {
                                bot.execute(new SendMessage().setChatId(chatID).setText("The first point must be the lowest one"));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            bot.execute(new SendMessage().setChatId(chatID).setText("Points must be separated by a whitespace"));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Expression e;
                    try {
                        e = new ExpressionBuilder(message_text).variable("x").build();
                        ValidationResult res = e.validate();
                        if (!res.isValid()) {
                            watchdog.addOrder(chatID, message_text);
                            try {
                                bot.execute(new SendMessage().setChatId(chatID).setText("Now input the points of integration"));
                            } catch (TelegramApiException e2) {
                                e2.printStackTrace();
                            }
                        } else {
                            try {
                                bot.execute(new SendMessage().setChatId(chatID).setText("Ooops... Looks like you've input something wrong"));
                            } catch (TelegramApiException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } catch (UnknownFunctionOrVariableException exp){
                        exp.printStackTrace();
                        try {
                            bot.execute(new SendMessage().setChatId(chatID).setText("Looks like you've input something wrong"));
                        } catch (TelegramApiException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
