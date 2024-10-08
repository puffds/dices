package org.example;

public class BotConstants {
    static final int ROLLS_COUNT = 10000;

    public static final String REGEX = "^(\\d+(?:d|д)\\d+[?:n|p|н|п]?)(?: " +
            "(\\d+(?:d|д)\\d+[?:n|p|н|п]?))* *(?: (<=|>=|<|>) \\d+)?$";

    public static final String HELP_MESSAGE = "Здорова, кожаный! Го бросать кубики!" +
            "\n\nНапиши количество кубиков и их грани вот в таком формате. Примеры:" +
            "\n1. 1d12" +
            "\n2. 1д8 2д6" +
            "\n4. 1d8p 2d4 3d7 <= 7" +
            "\n5. 1d8p 2d4 3d7 >= 5" +
            "\n6. 1d8p 2d6 3d7 < 3" +
            "\n7. 1d8н 2d4 2d7 > 4" +
            "\n\nРасшифровка:" +
            "\n1d8 - кубик с восемью гранями." +
            "\n2d4 - два кубика с четырьмя гранями" +
            "\n(p или п) - преимущество, из двух бросков выбрать наилучший результат" +
            "\n(n или н) - помеха, из двух бросков выбрать наихудший результат" +
            "\n(<,>,<=,>=) - подсчёт вероятности" +
            "\nКубик может быть один или их может быть несколько. " +
            "Между кубиками, знаками сравнения должен быть пробел.";

    public static final String ERROR_MESSAGE = "Ну, кожаный, соберись! Ты чего ? Давай ещё разочек!";
}