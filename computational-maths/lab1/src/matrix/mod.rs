use std::{
    fmt::Display,
    ops::{Add, Neg},
};

use crate::ring::RingElement;

#[derive(Debug, PartialEq, Eq, Clone)]
pub struct DMatrix<T> {
    nrows: usize,
    ncols: usize,
    data: Vec<Vec<T>>,
}

impl<T> DMatrix<T> {
    pub fn swap_rows(&mut self, i: usize, j: usize) {
        assert!(i < self.nrows);
        assert!(j < self.nrows);
        self.data.swap(i, j)
    }

    pub fn set(&mut self, i: usize, j: usize, value: T) {
        self.data[i][j] = value;
    }

    pub fn get_ncols(&self) -> usize {
        return self.ncols;
    }

    pub fn get_nrows(&self) -> usize {
        return self.nrows;
    }
}

impl<T: RingElement> DMatrix<T> {
    pub fn new_zeroed(ncols: usize, nrows: usize) -> Self {
        let mut new_data = Vec::<Vec<T>>::new();
        new_data.resize_with(nrows, || Vec::new());
        new_data
            .iter_mut()
            .for_each(|row| row.resize_with(ncols, || T::zero()));
        Self {
            data: new_data,
            ncols,
            nrows,
        }
    }
}

impl<T> DMatrix<T> {
    pub fn new(data: Vec<Vec<T>>) -> Self {
        let ncols = data[0].len();
        let nrows = data.len();
        Self { data, ncols, nrows }
    }
}

impl<T: Default + Copy> DMatrix<T> {
    pub fn new_from_array<const NCOLS: usize, const NROWS: usize>(
        data: [[T; NCOLS]; NROWS],
    ) -> DMatrix<T> {
        let mut new_data = Vec::<Vec<T>>::new();
        new_data.resize_with(NROWS, || Vec::new());
        new_data
            .iter_mut()
            .for_each(|row| row.resize_with(NCOLS, || T::default()));
        for i in 0..NROWS {
            for j in 0..NCOLS {
                new_data[i][j] = data[i][j]
            }
        }
        DMatrix {
            ncols: NCOLS,
            nrows: NROWS,
            data: new_data,
        }
    }
}

impl<T: Copy> DMatrix<T> {
    pub fn get_column(&self, column: usize) -> Vec<T> {
        return self.data.iter().map(|row| row[column]).collect();
    }

    pub fn get(&self, i: usize, j: usize) -> T {
        self.data[i][j]
    }
}

impl<T: RingElement + Copy> Add<DMatrix<T>> for DMatrix<T> {
    type Output = DMatrix<T>;
    fn add(self, rhs: DMatrix<T>) -> Self::Output {
        assert_eq!(self.nrows, rhs.nrows);
        assert_eq!(self.ncols, rhs.ncols);
        let mut new_data = self.data.clone();
        for i in 0..self.nrows {
            for j in 0..self.ncols {
                new_data[i][j] = new_data[i][j] + rhs.data[i][j];
            }
        }
        DMatrix {
            ncols: self.ncols,
            nrows: self.nrows,
            data: new_data,
        }
    }
}

impl<T: RingElement + Copy> Neg for DMatrix<T> {
    type Output = DMatrix<T>;
    fn neg(self) -> Self::Output {
        let mut copy = self.clone();
        for i in 0..copy.get_nrows() {
            for j in 0..copy.get_ncols() {
                let value = copy.get(i, j);
                copy.set(i, j, -value);
            }
        }
        copy
    }
}

impl<T: RingElement + Copy + std::cmp::PartialOrd> DMatrix<T> {
    pub fn abs(&self) -> DMatrix<T> {
        let abs = |x: T| -> T {
            if x > T::zero() {
                x
            } else {
                -x
            }
        };
        let new_data: Vec<Vec<_>> = self
            .data
            .iter()
            .map(|row| row.iter().map(|x| abs(*x)).collect())
            .collect();
        DMatrix {
            nrows: self.nrows,
            ncols: self.ncols,
            data: new_data,
        }
    }
}

impl<T: RingElement + Copy> std::ops::Mul<DMatrix<T>> for DMatrix<T> {
    type Output = DMatrix<T>;
    fn mul(self, rhs: DMatrix<T>) -> Self::Output {
        let m = self.nrows;
        let n = self.ncols;
        let l = rhs.ncols;
        assert_eq!(m, rhs.nrows);
        let mut result: DMatrix<T> = DMatrix::new_zeroed(l, m);
        for i in 0..m {
            for j in 0..l {
                let mut value = T::zero();
                for s in 0..n {
                    value = value + self.get(i, s) * rhs.get(s, j);
                }
                result.set(i, j, value);
            }
        }
        result
    }
}

impl<T: RingElement + PartialOrd + Copy + std::iter::Sum> DMatrix<T> {
    pub fn is_diagonally_dominant(&self) -> bool {
        let abs = |x: T| -> T {
            if x > T::zero() {
                x
            } else {
                -x
            }
        };
        let mut strict_number: usize = 0;
        let weak_dominant = self.data.iter().enumerate().all(|(i, row)| {
            let diagonal_value = abs(row[i]);
            let others_sum: T = row
                .iter()
                .enumerate()
                .filter(|(j, _)| *j != i)
                .map(|(_, x)| abs(*x))
                .sum();
            if diagonal_value > others_sum {
                strict_number += 1;
            }
            diagonal_value >= others_sum
        });
        return weak_dominant && strict_number > 0;
    }

    pub fn max_for_col(&self, col: usize) -> usize {
        let mut max_id = 0;
        for i in col..self.nrows {
            if self.get(i, col) > self.get(max_id, col) {
                max_id = i
            }
        }
        max_id
    }
}

#[cfg(test)]
mod tests {
    use super::DMatrix;

    #[test]
    fn add_matrix() {
        let a = DMatrix::new(vec![vec![1, 2, 3], vec![4, 5, 6], vec![7, 8, 9]]);
        let b = DMatrix::new(vec![vec![8, 7, 6], vec![5, 4, 3], vec![2, 1, 0]]);
        let c = a + b;
        let expected = DMatrix::new(vec![vec![9, 9, 9], vec![9, 9, 9], vec![9, 9, 9]]);
        for i in 0..3 {
            for j in 0..3 {
                print!("{}", c.data[i][j]);
            }
            println!("");
        }
        assert_eq!(expected, c);
    }

    #[test]
    fn is_diagonally_dominant() {
        let dominant = DMatrix::new_from_array([[3, -2, 1], [1, 3, 2], [-1, 2, 4]]);
        assert!(dominant.is_diagonally_dominant());

        let not_dominant = DMatrix::new_from_array([[0, 0, 0], [0, 0, 0], [0, 0, 0]]);
        assert!(!not_dominant.is_diagonally_dominant());
    }
}
