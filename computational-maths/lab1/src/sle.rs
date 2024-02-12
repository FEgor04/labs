use crate::matrix::DMatrix;
use crate::ring::RingElement;

struct SLE<T: RingElement + PartialOrd> {
    n: usize,
    /// N x N matrix
    a: DMatrix<T>,
    /// Column-vector of size N
    b: DMatrix<T>,
}
