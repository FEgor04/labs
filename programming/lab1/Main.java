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
  private static final int THIRD_ARRAY_COLUMNS_NUMBER = 6;
  /* Количество строк в третьем массиве */
  private static final int THIRD_ARRAY_ROWS_NUMBER = 12;
  /* Третий массив */
  private static float[][] n = new float[THIRD_ARRAY_COLUMNS_NUMBER][THIRD_ARRAY_ROWS_NUMBER];

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
      System.out.format("%10d", a[i]);
    }
    System.out.print("\n\n");
  }

  /**
   * Метод, инициализирующий и заполняюищй второй массив 
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
      System.out.format("%10.4f", x[i]);
    }
    System.out.print("\n\n");
  }

  /**
   * Метод, заполняющий третий массив
   */
  private static void createThirdArray() {
    for(int i = 0; i < THIRD_ARRAY_COLUMNS_NUMBER; i++) {
      for(int j = 0; j < THIRD_ARRAY_ROWS_NUMBER; j++) {
        n[i][j] = (float)(calculateThirdArrayValue(a[i], x[j])); 
      }
    }
  }

  /**
   * Метод, выводящий третий массив в виде таблицы
   */
  private static void printThirdArray() {
    for(int i = 0; i < THIRD_ARRAY_COLUMNS_NUMBER; i++) {
      for(int j = 0; j < THIRD_ARRAY_ROWS_NUMBER; j++) {
        System.out.format("%10.4f ", n[i][j]);
      }
      System.out.print("\n");
    }
  }

  /**
   * Метод, вычисляющий значение элемента массива по заданным a и x
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
