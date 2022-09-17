package lab1;
import java.util.Random;
import java.lang.Float;

public class Main {
  /* Первый элемент первого массива a */
  private static final int FIRST_ARRAY_START = 5;
  /* Последний элемент первого массива a */
  private static final int FIRST_ARRAY_END = 15;

  /* Массив a */
  private static long[] a;
  /* Размер второго массива */
  private static int SECOND_ARRAY_SIZE = 12;
  /* Левая граница отрезка для рандома */
  private static float RANDOM_LEFT_BORDER = -6;
  /* Правая граница отрезка для рандома */
  private static float RANDOM_RIGHT_BORDER = +6;
  /* Второй массив */
  private static float[] x;

  /* Количество столбцов в третьем массиве */
  private static final int THIRD_ARRAY_COLUMNS_NUMBER = 12;
  /* Количество строк в третьем массиве */
  private static final int THIRD_ARRAY_ROWS_NUMBER = 6;
  /* Третий массив */
  private static float[][] n = new float[THIRD_ARRAY_ROWS_NUMBER][THIRD_ARRAY_COLUMNS_NUMBER];

  /**
   * Входная точка программы
   */
  public static void main(String[] args) {
    createFirstArray(FIRST_ARRAY_START, FIRST_ARRAY_END);
    x = createSecondArray();
    createThirdArray();

    printFirstArray();
    printSecondArray();
    printThirdArray();
  }

  /**
   * Метод, инициализирующий и заполняюищй первый массив нечетными числами с start до end
   * @param start - первое нечетное число
   * @param end - последнее нечетное число
   */
  private static void createFirstArray(int start, int end) {
    int size = (end - start + 1) / 2 + 1;
    a = new long[size];
    for(int i = 0; i < size; i++) {
      a[i] = start + i * 2;
    }
  }

  /**
   * Метод, выводящий значения первого массива
   */
  private static void printFirstArray() {
    System.out.println("First array:");
    for(int i = 0; i < a.length; i++) {
      System.out.format("%-12d", a[i]);
    }
    System.out.print("\n\n");
  }

  /**
   * Метод, инициализирующий и заполняюищй второй массив 
   * @return второй массив, заполненный согласно заданию
   */
  private static float[] createSecondArray() {
    float[] secondArray = new float[SECOND_ARRAY_SIZE];
    Random rand = new Random();
    for(int i = 0; i < SECOND_ARRAY_SIZE; i++) {
      secondArray[i] = randomInLimits(rand.nextFloat(), RANDOM_LEFT_BORDER, RANDOM_RIGHT_BORDER);
    }
    return secondArray;
  }

  /**
   * Метод, переводящий данное число rnd [0; 1] в отрезок [leftLimit; rightLimit]
   * При заданных leftLimit и rightLimit данная функция будет монотонной
   * @param rnd - значение, которуное нужно перевести в отрезок
   * @param leftLimit - левая граница отрезка
   * @param rightLimit - правая граница отрезка
   * @return значение из отрезка [leftLimit; rightLimit]
   */
  private static float randomInLimits(float rnd, float leftLimit, float rightLimit) {
    return rnd * (rightLimit - leftLimit) + leftLimit;
  }

  /**
   * Метод, выводящий второй массив
   */
  private static void printSecondArray() {
    System.out.println("Second array:");
    for(int i = 0; i < x.length; i++) {
      System.out.format("%-12.4f", x[i]);
    }
    System.out.print("\n\n");
  }

  /**
   * Метод, заполняющий третий массив
   */
  private static void createThirdArray() {
    for(int i = 0; i < THIRD_ARRAY_ROWS_NUMBER; i++) {
      for(int j = 0; j < THIRD_ARRAY_COLUMNS_NUMBER; j++) {
        n[i][j] = (float)(calculateThirdArrayValue(a[i], x[j])); 
      }
    }
  }

  /**
   * Метод, выводящий третий массив в виде таблицы
   */
  private static void printThirdArray() {
    int maxElementLength = 0;
    for(int i = 0; i < THIRD_ARRAY_ROWS_NUMBER; i++) {
      for(int j = 0; j < THIRD_ARRAY_COLUMNS_NUMBER; j++) {
        maxElementLength = Math.max(maxElementLength, String.format("%.4f", n[i][j]).length()); // определение максимальной длины столбца
      }
    }

    printTableSeparator(maxElementLength + 1, THIRD_ARRAY_COLUMNS_NUMBER, "+\n"); // добавляем +1 на символ |
    for(int i = 0; i < THIRD_ARRAY_ROWS_NUMBER; i++) {
      for(int j = 0; j < THIRD_ARRAY_COLUMNS_NUMBER; j++) {
        System.out.format("|%-" + String.valueOf(maxElementLength) + ".4f", n[i][j]);
      }
      System.out.print("|\n");
      printTableSeparator(maxElementLength + 1, THIRD_ARRAY_COLUMNS_NUMBER, "+\n");
    }
  }

  /**
   * Метод, выводящий разделительную строчку у таблицы
   * @param width - ширина каждого элемента
   * @param elementsCount - количество элементов в каждой строчке
   * @param end - что выводить после разделителя
   */
  private static void printTableSeparator(int width, int elementsCount, String end) {
    for(int i = 0; i < elementsCount * width; i++) {
      if(i % width == 0) {
        System.out.print("+");
      }
      else {
        System.out.print("-");
      }
    }
    System.out.print(end);
  }

  /**
   * Метод, вычисляющий значение элемента массива по заданным a и x
   * @param a - значение a для формулы
   * @param x - значение x для формулы
   * @return вычисленное значение ячейки
   */
  private static double calculateThirdArrayValue(long a, float x) {
    double value;
    if(a == 15) {
      value = Math.pow(Math.E, Math.pow(Math.E, Math.pow(2*x, 3)));
    }
    else if(a == 5 || a == 9 || a == 13) {
      value = Math.pow(3 * Math.tan(Math.cbrt(x)), 2);
    }
    else {
      value = Math.sin(Math.asin(Math.PI / 4 * Math.pow(Math.E, -Math.abs(x))));
    }
    return value;
  }

}
