use std::ops::{Add, AddAssign, Mul, SubAssign};

use crate::ring::RingElement;

trait MatrixCell: RingElement + Copy {}

impl<U: RingElement + std::marker::Copy> MatrixCell for U{}

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

impl<T: MatrixCell, const R: usize, const C: usize> Matrix<T, R, C> {
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
    pub fn new(data: [[T; C]; R]) -> Self {
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
    pub fn transpose(&self) -> Matrix<T, C, R> {
        let mut new_data = [[T::zero(); R]; C];
        for i in 0..C {
            for j in 0..R {
                new_data[i][j] = self.data[j][i];
            }
        }
        Matrix { data: new_data }
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
                    data[i][j] = self.data[i][k] * rhs.data[k][j]
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
}
