use crate::matrix::DMatrix;
use crate::ring::RingElement;

struct SLE<T: RingElement + PartialOrd> {
    n: usize,
    /// N x N matrix
    a: DMatrix<T>,
    /// Column-vector of size N
    b: DMatrix<T>,
}

impl<T: RingElement + PartialOrd> SLE<T> {
    fn swap_rows(&mut self, i: usize, j: usize) {
        self.a.swap_rows(i, j);
        self.b.swap_rows(i, j);
    }
}
