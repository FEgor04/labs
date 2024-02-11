use super::{Matrix, MatrixCell};


pub struct MatrixRowIterator<'a, T: MatrixCell + std::marker::Copy, const M: usize, const N: usize> {
    curr: usize, /// Number of current row
    matrix: &'a Matrix<T, M, N>
}

impl<T: MatrixCell, const M: usize, const N: usize> Iterator for MatrixRowIterator<'_, T, M, N> {
    type Item = [T; N];
    fn next(&mut self) -> Option<Self::Item> {
        if self.curr >= M {
            return None;
        };
        self.curr += 1;
        return Some(self.matrix.data[self.curr - 1]);
    }
}

impl <'a, T: MatrixCell, const M: usize, const N: usize> MatrixRowIterator<'a, T, M, N> {
    pub fn new(matrix: &'a Matrix<T,M,N>) -> Self {
        Self {
            matrix,
            curr: 0
        }
    }
}


pub struct MatrixColumnIterator<'a, T: MatrixCell + std::marker::Copy, const M: usize, const N: usize> {
    curr: usize, /// Number of current row
    matrix: &'a Matrix<T, M, N>
}


impl<T: MatrixCell, const M: usize, const N: usize> Iterator for MatrixColumnIterator<'_, T, M, N> {
    type Item = [T; M];
    fn next(&mut self) -> Option<Self::Item> {
        if self.curr >= M {
            return None;
        };
        self.curr += 1;
        return Some(self.matrix.data.map(|row| row[self.curr - 1]))
    }
}

impl <'a, T: MatrixCell, const M: usize, const N: usize> MatrixColumnIterator<'a, T, M, N> {
    pub fn new(matrix: &'a Matrix<T,M,N>) -> Self {
        Self {
            matrix,
            curr: 0
        }
    }
}


