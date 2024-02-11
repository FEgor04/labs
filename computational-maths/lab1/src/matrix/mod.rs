mod matrix_iterator;

use std::ops::{Add, AddAssign, Mul, SubAssign};

use crate::ring::RingElement;

use self::matrix_iterator::{MatrixColumnIterator, MatrixRowIterator};

pub trait MatrixCell: RingElement + Copy {}

impl<U: RingElement + std::marker::Copy> MatrixCell for U {}

/// T - type of data
/// M - number of rows
/// N - number of columns
#[derive(Debug, PartialEq, Eq)]
pub struct Matrix<T, const M: usize, const N: usize>
where
    T: MatrixCell,
{
    data: [[T; N]; M],
}

pub type SquareMatrix<T, const D: usize> = Matrix<T, D, D>;

/// Vector is just a matrix with only 1 column
pub type Vector<T, const N: usize> = Matrix<T, N, 1>;

impl<T: MatrixCell, const N: usize> Vector<T, N> {
    /// Creates vector from array
    pub fn new_from_arr(arr: [T; N]) -> Self {
        Vector {
            data: arr.map(|it| [it]),
        }
    }
}

impl<T: MatrixCell, const M: usize, const N: usize> Matrix<T, M, N> {
    /// Creates new matrix from array of arrays
    ///
    ///
    /// Example:
    /// ```rust
    /// Matrix::new([[1,2,3], [4,5,6], [7, 8, 9]])
    /// ```
    /// creates new 3x3 matrix:
    /// ```
    /// | 1 2 3 |
    /// | 4 5 6 |
    /// | 7 8 9 |
    /// `
    pub fn new(data: [[T; N]; M]) -> Self {
        Self { data }
    }

    /// Transposes a matrix.
    ///
    /// Example:
    ///
    /// ```rust
    /// Matrix::new([1,2,3],[4,5,6],[7,8,9]).trasponse()
    /// ```
    /// returns 3x3 matrix of form:
    /// ```
    /// | 1 4 7 |
    /// | 2 5 8 |
    /// | 3 6 9 |
    /// ````
    pub fn transpose(&self) -> Matrix<T, N, M> {
        let mut new_data = [[T::zero(); M]; N];
        for i in 0..N {
            for j in 0..M {
                new_data[i][j] = self.data[j][i];
            }
        }
        Matrix { data: new_data }
    }

    /// Swaps i-th column with j-th column inplace.
    pub fn swap_columns(&mut self, i: usize, j: usize) {
        assert!(i < N);
        assert!(j < N);
        self.data.iter_mut().for_each(|row| row.swap(i, j));
    }

    /// Swaps i-th row with j-th row inplace.
    pub fn swap_rows(&mut self, i: usize, j: usize) {
        assert!(i < M);
        assert!(j < M);
        self.data.swap(i, j)
    }

    pub fn iter_rows(&self) -> MatrixRowIterator<'_, T, M, N> {
        MatrixRowIterator::new(self)
    }

    pub fn iter_columns(&self) -> MatrixColumnIterator<'_, T, M, N> {
        MatrixColumnIterator::new(self)
    }

}

impl<T: MatrixCell, const R: usize, const C: usize> Add<Matrix<T, R, C>> for Matrix<T, R, C> {
    type Output = Matrix<T, R, C>;
    fn add(self, rhs: Matrix<T, R, C>) -> Self::Output {
        let mut new_data: [[T; C]; R] = [[T::zero(); C]; R];
        for i in 0..new_data.len() {
            for j in 0..new_data[i].len() {
                new_data[i][j] = self.data[i][j] + rhs.data[i][j]
            }
        }
        Self { data: new_data }
    }
}

impl<T: MatrixCell, const M: usize, const N: usize, const R: usize> Mul<Matrix<T, N, R>>
for Matrix<T, M, N>
{
    type Output = Matrix<T, M, R>;
    fn mul(self, rhs: Matrix<T, N, R>) -> Matrix<T, M, R> {
        let mut data = [[T::zero(); R]; M];
        for i in 0..data.len() {
            for j in 0..data[i].len() {
                for k in 0..N {
                    data[i][j] = data[i][j] + self.data[i][k] * rhs.data[k][j]
                }
            }
        }
        Matrix { data }
    }
}

#[cfg(test)]
mod tests {

    use super::{Matrix, Vector};

    #[test]
    fn add_matrix() {
        let a = Matrix::new([[0, 1, 2], [3, 4, 5], [6, 7, 8]]);
        let b = Matrix::new([[9, 8, 7], [6, 5, 4], [3, 2, 1]]);
        let c = a + b;
        for i in 0..3 {
            for j in 0..3 {
                assert_eq!(c.data[i][j], 9)
            }
        }
    }

    #[test]
    fn multiply_matricies() {
        let a = Matrix::new([[1, 2, 3], [4, 5, 6]]);
        let b = Matrix::new([[7, 8], [9, 10], [11, 12]]);
        let c_expected = Matrix::new([[58, 64], [139, 154]]);
        let c_acutal = a * b;
        assert_eq!(c_expected, c_acutal)
    }

    #[test]
    fn multiply_vectors() {
        let a = Vector::new_from_arr([1, 2, 3]).transpose();
        let b = Vector::new_from_arr([4, 5, 6]);
        let c_expected = Vector::new_from_arr([4 + 10 + 18]);
        let c_actual = a * b;
        assert_eq!(c_actual, c_expected);
    }

    #[test]
    fn transpose_matrix() {
        let m = Matrix::new([[1, 2, 3], [4, 5, 6], [7, 8, 9]]);
        let transposed = Matrix::new([[1, 4, 7], [2, 5, 8], [3, 6, 9]]);
        let actual = m.transpose();
        assert_eq!(transposed, actual);
    }

    #[test]
    fn swap_columns() {
        let mut m = Matrix::new([[1, 2, 3], [4, 5, 6], [7, 8, 9]]);
        let swapped = Matrix::new([[3, 2, 1], [6, 5, 4], [9, 8, 7]]);
        m.swap_columns(0, 2);
        assert_eq!(swapped, m);
    }

    #[test]
    fn swap_rows() {
        let m_start = Matrix::new([[1, 2, 3], [4, 5, 6], [7, 8, 9]]);
        let mut m = Matrix::new([[1, 2, 3], [4, 5, 6], [7, 8, 9]]);
        let swapped = Matrix::new([[1, 2, 3], [7, 8, 9], [4, 5, 6]]);
        m.swap_rows(1, 2);
        assert_eq!(swapped, m);
        m.swap_rows(1, 2);
        assert_eq!(m, m_start);
    }

    #[test]
    fn row_iterator() {
        let m = Matrix::new([[1, 2, 3], [4, 5, 6], [7, 8, 9]]);
        let mut iter = m.iter_rows();
        assert_eq!(Some([1,2,3]), iter.next());
        assert_eq!(Some([4,5,6]), iter.next());
        assert_eq!(Some([7,8,9]), iter.next());
        assert_eq!(None, iter.next())
    }

    #[test]
    fn column_iterator() {
        let m = Matrix::new([[1, 2, 3], [4, 5, 6], [7, 8, 9]]);
        let mut iter = m.iter_columns();
        assert_eq!(Some([1,4,7]), iter.next());
        assert_eq!(Some([2,5,8]), iter.next());
        assert_eq!(Some([3,6,9]), iter.next());
        assert_eq!(None, iter.next())
    }
}
