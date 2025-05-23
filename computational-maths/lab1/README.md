# Лабораторная работа #1 (вариант 16)
## Решение системы линейных алгебраических уравнений СЛАУ

1. № варианта определяется как номер в списке группы согласно ИСУ.
2. В программе численный метод должен быть реализован в виде отдельной
подпрограммы/метода/класса, в который исходные/выходные данные передаются
в качестве параметров.
3. Размерность матрицы n<=20
(задается из файла или с клавиатуры - по выбору конечного пользователя).
4. Должна быть реализована возможность ввода коэффициентов матрицы, как
с клавиатуры, так и из файла (по выбору конечного пользователя).

### Метод Гаусса-Зейделя
Для итерационных методов должно быть реализовано:
- Точность задается с клавиатуры/файла
- Проверка диагонального преобладания (в случае, если диагональное
преобладание в исходной матрице отсутствует, сделать перестановку
строк/столбцов до тех пор, пока преобладание не будет достигнуто).
В случае невозможности достижения диагонального преобладания - выводить
соответствующее сообщение.
- Вывод вектора неизвестных: $ x_1, x_2, \ldots, x_n $
- Вывод количества итераций, за которое было найдено решение.
-  Вывод вектора погрешностей: $ | x_i^{(k)} - x_i^{(k-1)} | $
