package org.example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Utils {
    /**
     * Переместить запятую на {@param shiftPlaces} знаков в право
     *
     * @param value       - число, в котором нужно переместить знаки
     * @param shiftPlaces - на сколько знаков переместить запятую
     * @return - изменённое число
     */
    public static BigDecimal shiftDecimal(BigDecimal value, int shiftPlaces) {
        return value.movePointRight(shiftPlaces);
    }

    /**
     * Проверка строки на соответствие регулярному выражению
     *
     * @param input - входная строка
     * @param regex - регулярОчка
     * @return - да или нет (true/false)
     */
    public static boolean checkInput(String input, String regex) {
        return Pattern.compile(regex).matcher(input).matches();
    }

    /**
     * Посчитать количество повторений подходящих под условие
     *
     * @param rolls     - пустой массив
     * @param condition - условие (первая часть)
     * @param number    - условие (вторая часть)
     * @return - количество повторов
     */
    public static int countOccurrences(int[] rolls, String condition, int number) {
        return switch (condition) {
            case "<" -> (int) Arrays.stream(rolls).filter(x -> x < number).count();
            case ">" -> (int) Arrays.stream(rolls).filter(x -> x > number).count();
            case "<=" -> (int) Arrays.stream(rolls).filter(x -> x <= number).count();
            case ">=" -> (int) Arrays.stream(rolls).filter(x -> x >= number).count();
            default -> 0;
        };
    }

    /**
     * Заполнить массив размерностью {@link BotConstants#ROLLS_COUNT} бросками кубиков
     *
     * @param rolls   - пустой массив
     * @param maxDice - количество граней кубика
     * @return - массив, содержащий броски
     */
    public static int[] generateRolls(int[] rolls, int maxDice) {
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = roll(maxDice);
        }
        return rolls;
    }

    /**
     * Заполнить массив размерностью {@link BotConstants#ROLLS_COUNT} бросками кубиков с помехой
     *
     * @param rolls   - пустой массив
     * @param maxDice - количество граней кубика
     * @return - массив, содержащий броски
     */
    public static int[] generateNegativeRolls(int[] rolls, int maxDice) {
        for (int i = 0; i < rolls.length; i++) {
            int firstRoll = roll(maxDice);
            int secondRoll = roll(maxDice);
            rolls[i] = Math.min(firstRoll, secondRoll);
        }
        return rolls;
    }

    /**
     * Заполнить массив размерностью {@link BotConstants#ROLLS_COUNT} бросками кубиков с преимуществом
     *
     * @param rolls   - пустой массив
     * @param maxDice - количество граней кубика
     * @return - массив, содержащий броски
     */
    public static int[] generatePositiveRolls(int[] rolls, int maxDice) {
        for (int i = 0; i < rolls.length; i++) {
            int firstRoll = roll(maxDice);
            int secondRoll = roll(maxDice);
            rolls[i] = Math.max(firstRoll, secondRoll);
        }
        return rolls;
    }

    /**
     * Бросить кубик
     *
     * @param max - количество граней кубика
     * @return - результат броска
     */
    public static int roll(int max) {
        max -= 1;
        return (int) (Math.random() * ++max) + 1;
    }
}