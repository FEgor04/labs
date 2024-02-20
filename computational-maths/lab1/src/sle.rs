use crate::matrix::DMatrix;
use crate::ring::RingElement;

pub struct SLE<T: RingElement> {
    pub n: usize,
    /// N x N matrix
    pub a: DMatrix<T>,
    /// Column-vector of size N
    pub b: DMatrix<T>,
}

impl<T: RingElement> SLE<T> {
    pub fn swap_rows(&mut self, i: usize, j: usize) {
        self.a.swap_rows(i, j);
        self.b.swap_rows(i, j);
    }

    pub fn get_n(&self) -> usize {
        self.n
    }
}

impl<T: RingElement + std::cmp::PartialOrd + Copy + std::iter::Sum + std::fmt::Display> SLE<T> {
    pub fn is_diagonally_dominant(&self) -> bool {
        self.a.is_diagonally_dominant()
    }

    /// Tries to transform this system to diagonally dominant format
    pub fn transform_to_diagonally_dominant(&mut self) {
        if self.is_diagonally_dominant() {
            return;
        }
        for col in 0..self.n - 1 {
            let max_for_col = self.a.max_for_col(col);
            if max_for_col != col {
                self.swap_rows(col, max_for_col);
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use crate::{matrix::DMatrix, sle::SLE};

    #[test]
    fn transform_to_diagonally_dominant() {
        let a = DMatrix::new_from_array([[2, 2, 10], [10, 1, 1], [2, 10, 1]]);
        assert!(!a.is_diagonally_dominant());
        let b = DMatrix::new_from_array([[14], [12], [13]]);
        let mut sle = SLE { a, b, n: 3 };
        sle.transform_to_diagonally_dominant();
        assert!(sle.a.is_diagonally_dominant());
        let a_expected = DMatrix::new_from_array([[10, 1, 1], [2, 10, 1], [2, 2, 10]]);
        let b_expected = DMatrix::new_from_array([[12], [13], [14]]);
        assert_eq!(a_expected, sle.a);
        assert_eq!(b_expected, sle.b);
    }
}
