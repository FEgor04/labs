pub mod gauss_seidel;

use crate::{matrix::DMatrix, ring::RingElement, sle::SLE};

pub struct SLESolution<T: RingElement> {
    pub solution: DMatrix<T>,
    pub iterations_count: usize,
    pub error_vector: DMatrix<T>,
}

pub trait SLESolver<T: RingElement> {
    /// Tries to solve given SLE.
    /// It can mutate `sle` if needed.
    ///
    /// Returns column-vector of `x`
    fn solve_sle(&self, sle: &SLE<T>) -> SLESolution<T>;
}
