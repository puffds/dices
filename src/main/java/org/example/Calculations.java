package org.example;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.example.BotConstants.ROLLS_COUNT;
import static org.example.Utils.*;

public class Calculations {
    /**
     * Произвести расчёты
     *
     * @param messageText - текст сообщения
     * @return - результат вычислений
     */
    public static BigDecimal calculate(String messageText) {
        // Получить кубики с модификаторами (сплит по пробелу)
        String[] dices = messageText.split(" ");

        // Сумма бросков для вычисления среднего значения
        double totalSumOfRolls = 0;
        // Вероятность
        double totalProbability = 0;

        // Флаг вероятности
        boolean isProbability = messageText.contains("<") || messageText.contains(">");
        // Условие для подсчёта вероятности (первая часть)
        String condition = isProbability ? dices[dices.length - 2] : "";
        // Условие для подсчёта вероятности (вторая часть)
        String number = isProbability ? dices[dices.length - 1] : "";

        if (isProbability) {
            dices = Arrays.copyOf(dices, dices.length - 2);
        }

        for (String dice : dices) {
            totalSumOfRolls += processDice(dice);
            if (isProbability) {
                totalProbability += calculateDiceProbability(dice, condition, Integer.parseInt(number));
            }
        }

        totalSumOfRolls = totalSumOfRolls / ROLLS_COUNT;
        totalProbability = totalProbability / ROLLS_COUNT;

        if (isProbability) {
            return shiftDecimal(BigDecimal.valueOf(totalProbability / ROLLS_COUNT), 4);
        } else {
            return BigDecimal.valueOf(totalSumOfRolls);
        }
    }

    /**
     * Посчитать сумму бросков для кубиков
     *
     * @param dice - кубик или кубики
     * @return - сумма всех бросков
     */
    private static double processDice(String dice) {
        int countDice = Integer.parseInt(dice.split("[dд]")[0]);
        String lastSymbol = String.valueOf(dice.charAt(dice.length() - 1));
        int maxDiceMod = getMaxDiceMod(dice);

        double sum = 0;
        for (int i = 0; i < countDice; i++) {
            int[] rolls = generateRollsBasedOnModifier(lastSymbol, maxDiceMod);
            sum += Arrays.stream(rolls).sum();
        }
        return sum;
    }

    /**
     * Посчитать вероятность (соблюдение опредёленного условия)
     *
     * @param dice      - кубик или кубики
     * @param condition - условие (первая часть)
     * @param number    - условие (вторая часть)
     * @return - вероятность
     */
    private static double calculateDiceProbability(String dice, String condition, int number) {
        int countDice = Integer.parseInt(dice.split("[dд]")[0]);
        String lastSymbol = String.valueOf(dice.charAt(dice.length() - 1));
        int maxDiceMod = getMaxDiceMod(dice);

        double probability = 0;
        for (int i = 0; i < countDice; i++) {
            int[] rolls = generateRollsBasedOnModifier(lastSymbol, maxDiceMod);
            probability += countOccurrences(rolls, condition, number);
        }
        return probability;
    }

    /**
     * Получить количество граней кубка в зависимости от присутствия модификатора
     *
     * @param dice - кубик
     * @return - количество граней
     */
    private static int getMaxDiceMod(String dice) {
        String[] parts = dice.split("[dд]");
        String lastSymbol = String.valueOf(dice.charAt(dice.length() - 1));
        return lastSymbol.matches("[npнп]")
                ? Integer.parseInt(parts[1].substring(0, parts[1].length() - 1)) : Integer.parseInt(parts[1]);
    }

    /**
     * Заполнить массив бросками кубика в зависимости от модификатора
     *
     * @param modifier   - модификатор
     * @param maxDiceMod - количество граней кубика
     * @return - массив с бросками
     */
    private static int[] generateRollsBasedOnModifier(String modifier, int maxDiceMod) {
        if (modifier.contains("p") || modifier.contains("п")) {
            return generatePositiveRolls(new int[ROLLS_COUNT], maxDiceMod);
        } else if (modifier.contains("n") || modifier.contains("н")) {
            return generateNegativeRolls(new int[ROLLS_COUNT], maxDiceMod);
        } else {
            return generateRolls(new int[ROLLS_COUNT], maxDiceMod);
        }
    }
}