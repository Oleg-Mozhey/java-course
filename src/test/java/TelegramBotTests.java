public class TelegramBotTests {

    /*

    static TelegramBot bot = TelegramBotTest.bot;
    static Long chatId = TelegramBotTest.chatId;

    String testShippingQuery = getProp("TEST_SHIP_QUERY");
    String testPreCheckoutQuery = getProp("TEST_PRECHECKOUT_QUERY");

    @Test
    public void sendInvoice() {
        SendResponse response = bot.execute(new SendInvoice(chatId, "title", "desc", "my_payload",
                "284685063:TEST:NThlNWQ3NDk0ZDQ5", "my_start_param", "USD", new LabeledPrice("label", 200))
                .providerData("{\"foo\" : \"bar\"}")
                .photoUrl("https://telegram.org/img/t_logo.png").photoSize(100).photoHeight(100).photoWidth(100)
                .needPhoneNumber(true).needShippingAddress(true).needEmail(true).needName(true)
                .isFlexible(true)
                .startParameter("start")
                .maxTipAmount(100)
                .suggestedTipAmounts(new Integer[]{1, 2, 5})
                .sendEmailToProvider(true)
                .sendPhoneNumberToProvider(true)
                .replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("just pay").pay(),
                        new InlineKeyboardButton("google it").url("www.google.com")))
        );
        Invoice invoice = response.message().invoice();
        InvoiceCheck.check(response.message().invoice());
        assertEquals("USD", invoice.currency());
        assertEquals(Integer.valueOf(200), invoice.totalAmount());
        assertEquals("start", invoice.startParameter());

        InlineKeyboardButton payButton = response.message().replyMarkup().inlineKeyboard()[0][0];
        assertTrue(payButton.isPay());
        assertEquals("just pay", payButton.text());
    }

     */
}