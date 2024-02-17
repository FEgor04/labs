pub mod gauss_seidel;

use crate::{matrix::DMatrix, ring::RingElement, sle::SLE};

pub struct SLESolution<T: RingElement> {
    solution: DMatrix<T>,
    iterations_count: usize,
}

trait SLESolver<T: RingElement> {
    /// Tries to solve given SLE.
    /// It can mutate `sle` if needed.
    ///
    /// Returns column-vector of `x`
    fn solve_sle(&self, sle: &mut SLE<T>) -> SLESolution<T>;
}
