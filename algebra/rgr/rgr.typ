= Группа 191. Поиск ЖНФ матрицы и жорданова базиса. Вариант 44, Федоров Егор.
#let lin(term) = box[Lin(#term)]

Исходная матрица:
$
A = mat(
  2, 0, 0, 0, 0, 0;
  0, 2, 0, 0, 0, 0;
  2, 0, 2, 0, 0, 0;
  0, 3, 0, 2, 0, 0;
  1, 0, 0, 3, 2, 0;
  0, 2, 1, 0, 0, 2;
)
$

Найдем собственные числа оператора $phi$.
$ chi_phi(t) = det(A - t E_6) = det(mat(
  2, 0, 0, 0, 0, 0;
  0, 2, 0, 0, 0, 0;
  2, 0, 2, 0, 0, 0;
  0, 3, 0, 2, 0, 0;
  1, 0, 0, 3, 2, 0;
  0, 2, 1, 0, 0, 2;
) - t mat(
1, 0, 0, 0, 0, 0;
0, 1, 0, 0, 0, 0;
0, 0, 1, 0, 0, 0;
0, 0, 0, 1, 0, 0;
0, 0, 0, 0, 1, 0;
0, 0, 0, 0, 0, 1;
))
= 
$
$ = det(mat(
  2, 0, 0, 0, 0, 0;
  0, 2, 0, 0, 0, 0;
  2, 0, 2, 0, 0, 0;
  0, 3, 0, 2, 0, 0;
  1, 0, 0, 3, 2, 0;
  0, 2, 1, 0, 0, 2;
  )
- mat(
t, 0, 0, 0, 0, 0;
0, t, 0, 0, 0, 0;
0, 0, t, 0, 0, 0;
0, 0, 0, t, 0, 0;
0, 0, 0, 0, t, 0;
0, 0, 0, 0, 0, t;
)
) = det(mat(
  2 - t, 0, 0, 0, 0, 0;
  0, 2 - t, 0, 0, 0, 0;
  2, 0, 2 - t, 0, 0, 0;
  0, 3, 0, 2 - t, 0, 0;
  1, 0, 0, 3, 2 - t, 0;
  0, 2, 1, 0, 0, 2 - t;
))
$

Сверху от главной диагонали все числа -- нули, значит $chi_phi(t) = (2-t)^6$.
Таким образом, $chi_phi(t)$ имеет единственный корень $t = 2$ кратности $6$.

== Построение ядер $W_i$
$W_0 = {0}$. Найдем $W_1$. Для этого решим ОСЛУ $B x = 0$, где $B = A - lambda E_n$, $lambda = 2$.

$ B = A - 2 E_n = mat(
  0, 0, 0, 0, 0, 0;
  0, 0, 0, 0, 0, 0;
  2, 0, 0, 0, 0, 0;
  0, 3, 0, 0, 0, 0;
  1, 0, 0, 3, 0, 0;
  0, 2, 1, 0, 0, 0;
) $

Приведем матрицу $B $ к трапецевидному виду.

$ B = mat(
  2, 0, 0, 0, 0, 0;
  1, 0, 0, 3, 0, 0;
  0, 3, 0, 0, 0, 0;
  0, 2, 1, 0, 0, 0;
  0, 0, 0, 0, 0, 0;
  0, 0, 0, 0, 0, 0;
) ~
  mat(
  2, 0, 0, 0, 0, 0;
  0, 0, 0, 3, 0, 0;
  0, 3, 0, 0, 0, 0;
  0, 2, 1, 0, 0, 0;
  0, 0, 0, 0, 0, 0;
  0, 0, 0, 0, 0, 0;
  ) ~
  mat(
  2, 0, 0, 0, 0, 0;
  0, 3, 0, 0, 0, 0;
  0, 0, 1, 0, 0, 0;
  0, 0, 0, 3, 0, 0;
  0, 0, 0, 0, 0, 0;
  0, 0, 0, 0, 0, 0;
  ) 
$
Очевидно, что $ker(B) = #lin( mat(0, 0, 0, 0, 1, 0), mat(0, 0, 0, 0, 0, 1) )$
